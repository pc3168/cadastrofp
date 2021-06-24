package br.com.pc.natusfarma.classe;

import br.com.pc.formularios.FrameJDialog;
import br.com.pc.natusfarma.bd.DadosFPSql;
import br.com.pc.natusfarma.bd.MovimentoSql;
import br.com.pc.natusfarma.bd.VendasSql;
import br.com.pc.natusfarma.form.FormDadosFP1;
import br.com.pc.natusfarma.tabela.Tabela;
import br.com.pc.util.FormatacaoMaskara;
import br.com.pc.util.Mensagem;
import br.com.pc.util.ValidaCPFeCNPJ;
import datechooser.beans.DateChooserCombo;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Paulo César
 */
public class DadosFP1 extends FormDadosFP1{

    public static final String TITULO = "Cadastro autorização";
    public static final String VERSAO = "2.0";
    private Tabela tabela;
    private final String[] layoutColunas = {"Código", "Descrição","Barras", "Qtde" , "Q Dias"};
    private List<ModeloVendas> listaMv;
    
    //private FrameJDialog frameJDialogCadastroClientes;
    
    public DadosFP1() {
        inicializar();
    }
    
    private void inicializar(){
        datechooser(calendarioVenda);
        datechooser(calendarioProxVenda);
        FormatacaoMaskara.formatacao(tfCpf, "###.###.###-##");
        FormatacaoMaskara.formatacao(tfAutorizacao, "###.###.###.###.###");
        montaTabela();
        
        calendarioVenda.addCommitListener(new datechooser.events.CommitListener() {
            @Override
            public void onCommit(datechooser.events.CommitEvent evt) {
                calendarioVenda(0);
            }
        });
        
        calendarioProxVenda.addCommitListener(new datechooser.events.CommitListener() {
            @Override
            public void onCommit(datechooser.events.CommitEvent evt) {
                calendarioProxVenda();
            }
        });
                
        tfCpf.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               tfAutorizacao.requestFocus();
            }
        });
        
        tfCpf.addFocusListener(new FocusAdapter() {

            @Override
            public void focusLost(FocusEvent e) {
               tfCpf();
            }
        });
        
        tfAutorizacao.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               tfCupom.requestFocus();
            }
        });
        
        tfAutorizacao.addFocusListener(new FocusAdapter() {

            @Override
            public void focusLost(FocusEvent e) {
               tfAutorizacao();
            }
        });
        
        tfCupom.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               tfCaixa.requestFocus();
            }
        });
        
        tfCaixa.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               calendarioVenda.requestFocus();
            }
        });
        
        
        btGravarAtualizar.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               btGravarAtualizar();
            }
        });
        
        btAlterarDadosCliente.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                btAlterarDadosCliente();
            }
        });
        
    }
    
    private void btAlterarDadosCliente(){
        cadastroClientesJDialog();
    }
    
    private void btGravarAtualizar(){
        String cpf = CadastroClientes.tfCpfFormatado(tfCpf.getText());
        String valAutorizcao = autorizacaoSemFormatacao(tfAutorizacao.getText().trim());
        if (cpf.trim().equals("") || 
                valAutorizcao.equals("") || 
                tfCupom.getText().trim().equals("") || 
                tfCaixa.getText().trim().equals("")) {
            Mensagem.mensagem("Todos os campos são obrigatórios.");
            return;
        }
        if (!ValidaCPFeCNPJ.isValidCPF(cpf)) {
            Mensagem.mensagem("CPF é inválido.");
            tfCpf.requestFocus();
            return;
        }
        
        if (valAutorizcao.length() < 15) {
            Mensagem.mensagem("Autorização Inválida");
            tfAutorizacao.requestFocus();
            return;
        }
        
        if (mostrarDadosClientes(cpf)) {
            return;
        }
        
        try {
            long autorizacao = Long.parseLong(valAutorizcao);
            ModeloDadosFP dfm = new ModeloDadosFP();
            dfm.setFpAutorizacao(autorizacao);
            dfm.setFpCupom(Integer.parseInt(tfCupom.getText()));
            dfm.setCliCpf(tfCpf.getText());
            dfm.setFpData_venda(converteCalendarToDate(calendarioVenda.getSelectedDate()));
            dfm.setFpData_prox_venda(converteCalendarToDate(calendarioProxVenda.getSelectedDate()));
            if (verificaAutorizacao(autorizacao).isEmpty()) {
                DadosFPSql.insertDadosFP(dfm);
                movimento(listaMv);
                Mensagem.mensagem("Dados inserido com Sucesso.");
            } else {
                DadosFPSql.updateDadosFP(dfm);
                movimento(listaMv);
                Mensagem.mensagem("Dados atualizado com Sucesso.");
            }
        } catch (NumberFormatException e) {
            Mensagem.mensagem(""+e);
        } finally {
            limpaTela();
        }
    }
    
    public void movimento(List<ModeloVendas> listaMv){
        ModeloMovimento mv;
        for (int i = 0; i < listaMv.size(); i++) {
            mv = new ModeloMovimento();
            mv.setFpAutorizacao(listaMv.get(i).getVenAutorizacao());
            mv.setMovEcf(listaMv.get(i).getVenCaixa());
            mv.setProId(listaMv.get(i).getProId());
            mv.setMovQuantidade(listaMv.get(i).getVenQuantidade());
            if (MovimentoSql.selectMovimentoAutCodigo(mv,null).isEmpty()) {
                MovimentoSql.insertMovimento(mv);
            }else{
                MovimentoSql.updateMovimento(mv);
            }
            
        }
    }
    
    private void limpaTela(){
        tfCpf.setText("");
        tfAutorizacao.setText("");
        tfCupom.setText("");
        Calendar c = Calendar.getInstance();
        calendarioVenda.setSelectedDate(c);
        calendarioProxVenda.setSelectedDate(c);
        lNome.setText("NOME:");
        lEndereco.setText("END:");
        lBairro.setText("Bairro:");
        lTelefone.setText("Telefone:");
        lNumero.setText("Número:");
        desabilitar(true);
        
    }
    
    private void tfCpf(){
        String cpf = CadastroClientes.tfCpfFormatado(tfCpf.getText());
        if (cpf.equals("") || cpf.length() != 11) {
            return;
        }
        if (!ValidaCPFeCNPJ.isValidCPF(cpf)) {
            Mensagem.mensagem("CPF é inválido.");
            tfCpf.requestFocus();
            return;
        }
        mostrarDadosClientes(cpf);
        
    }
    
    private void calendarioVenda(int dias){
        try {
            java.sql.Date data = converteStringToDate(calendarioVenda);
            Calendar c = converteDateToCalendar(data);
            c.add(Calendar.DAY_OF_MONTH, dias);
            calendarioProxVenda.setSelectedDate(c);
        } catch (ParseException ex) {
            Mensagem.mensagem(""+ex);
        }
    }
    
    private void calendarioProxVenda(){
        //digite o codigo do calendario aqui.
    }
    
    private void tfAutorizacao(){
        String valAutorizacao = autorizacaoSemFormatacao(tfAutorizacao.getText());
        if (valAutorizacao.isEmpty() || valAutorizacao.length() < 15) {
            return;
        }
        try {
            long autorizacao = Long.parseLong(valAutorizacao);
             //modificar essa linha para aproveitar o codigo da outra classe  par puxar os dados do clientes.
            List<ModeloDadosFP> listaDFP = verificaAutorizacao(autorizacao);
            if (!listaDFP.isEmpty()) {
                int n = Mensagem.pergunta("Essa autorização já está cadastrada."
                        + "\nDeseja Visualizar os dados?");
                if (n == javax.swing.JOptionPane.YES_OPTION) {
                    tfAutorizacao.setText(""+listaDFP.get(0).getFpAutorizacao());
                    tfCpf.setText(listaDFP.get(0).getCliCpf());
                    tfCupom.setText(""+listaDFP.get(0).getFpCupom());
                    calendarioVenda.setSelectedDate(converteDateToCalendar(listaDFP.get(0).getFpData_venda()));
                    calendarioProxVenda.setSelectedDate(converteDateToCalendar(listaDFP.get(0).getFpData_prox_venda()));
                    mostrarDadosClientes(tfCpf.getText());
                    mostrarDadosMovimento(listaDFP.get(0).getFpAutorizacao());
                }
                return;
            }
            ModeloVendas mv = new ModeloVendas();
            mv.setVenAutorizacao(autorizacao);
            listaMv = VendasSql.selectVendasAutCupomCodigo(mv,ModeloVendas.VEN_AUTORIZACAO);
            if (listaMv.isEmpty()) {
                Mensagem.mensagem("Autorização não encontrada na tabela de vendas, verifique se a autorização foi digitada corretamente ou realize a importação das vendas para o sistema.", "Atenção!!", javax.swing.JOptionPane.WARNING_MESSAGE);
                desabilitar(true);
            }else{
                //tfAutorizacao.setText(""+listaMv.get(0).getVenAutorizacao());
                tfCupom.setText(""+listaMv.get(0).getVenCupom());
                tfCaixa.setText(""+listaMv.get(0).getVenCaixa());
                calendarioVenda.setSelectedDate(converteDateToCalendar(listaMv.get(0).getVenDataVenda()));
                calendarioVenda(listaMv.get(0).getProDias());
                btGravarAtualizar.requestFocus();
                //calendarioProxVenda.setSelectedDate(converteDateToCalendar(listaMv.get(0).getProDias()));
                preencherTabela(listaMv);
                desabilitar(false);
            }
        } catch (NumberFormatException e) {
            Mensagem.mensagem("O valor da autorização não é um número.\n"+e);
        }
            
    }
    
    private void mostrarDadosMovimento(long autorizacao){
        ModeloMovimento mm = new ModeloMovimento();
        mm.setFpAutorizacao(autorizacao);
        List<ModeloMovimento> listaMm = MovimentoSql.selectMovimentoAutCodigo(mm, ModeloMovimento.FP_AUTORIZACAO);
        if (listaMm.isEmpty()) {
            return;
        }
        tfCaixa.setText(""+listaMm.get(0).getMovEcf());
        preencherTabelaMovimento(listaMm);
        desabilitar(false);
    }
    
    private void desabilitar(boolean ativo){
        tfCupom.setEditable(ativo);
        tfCaixa.setEditable(ativo);
        calendarioVenda.setEnabled(ativo);
        if (ativo) {
            tfCupom.setText("");
            tfCaixa.setText("");
            calendarioVenda.setSelectedDate(java.util.Calendar.getInstance());
            tabela.limpaTabela();
        }
    }
    
    public ArrayList<ModeloDadosFP> verificaAutorizacao(long autorizacao){
        ModeloDadosFP dfm = new ModeloDadosFP();
        dfm.setFpAutorizacao(autorizacao);
        return DadosFPSql.selectDadosFP(dfm);
    }
    
    private Calendar converteDateToCalendar(java.sql.Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }
    
    private DateChooserCombo datechooser(DateChooserCombo chooser) {
        chooser.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);
        chooser.setWeekStyle(datechooser.view.WeekDaysStyle.NORMAL);
        chooser.setFormat(2);
        chooser.setCurrentView(new datechooser.view.appearance.AppearancesList("Light"));
        return chooser;
        
    }
      
    private java.sql.Date converteCalendarToDate(Calendar calendario){
        java.sql.Date date = new java.sql.Date(calendario.getTimeInMillis());
        return date;
    }
    
    private java.sql.Date converteStringToDate(DateChooserCombo chooser) throws ParseException{
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        java.sql.Date data = new java.sql.Date(fmt.parse(chooser.getText()).getTime());
        return data;
    }
    
    private boolean mostrarDadosClientes(String cpf){
        List<ModeloClientes> listaClientes = CadastroClientes.verificaCadastroCpf(cpf);
        if (!listaClientes.isEmpty()) {
            lNome.setText(listaClientes.get(0).getCliNome());
            lEndereco.setText(listaClientes.get(0).getCliEndereco());
            lNumero.setText(""+listaClientes.get(0).getCliNumero());
            lBairro.setText(listaClientes.get(0).getCliBairro());
            lTelefone.setText(listaClientes.get(0).getCliTelefone1());
            
        } else {
            cadastroClientesJDialog();
            return true;
        }
        return false;
    }
    
    private String autorizacaoSemFormatacao(String autorizacao){
        return autorizacao.replace(".", "").trim();
    }
    
    private void cadastroClientesJDialog(){
        CadastroClientes cc = new CadastroClientes();
        cc.tfCpf.setText(tfCpf.getText());
        cc.tfNome.requestFocus();
        //if (frameJDialogCadastroClientes == null) {
        FrameJDialog frameJDialogCadastroClientes = new FrameJDialog(null, true, cc, CadastroClientes.TITULO);
        //}
        frameJDialogCadastroClientes.setVisible(true);
        tfCpf.requestFocus();
    }
    
     private void montaTabela(){
        tabela = new Tabela();
        painelPrincipal.add(tabela);
        painelPrincipal.setPreferredSize(new Dimension(100,100));
        painelPrincipal.setLayout(new java.awt.GridLayout());
        tabela.colunas(layoutColunas);
        tabela.tamanho(new Integer[]{50,200,105,40,50});
        
        //tabela.reajustavel(new Boolean[]{true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true});
    }
     
     private void preencherTabela(List<ModeloVendas> lista){
        tabela.limpaTabela();
        for (int i = 0; i < lista.size(); i++) {
            tabela.linhas(new Object[]{
                //"Código", "Descrição","Barras", "Qtde" , "Qtde Dias"
                lista.get(i).getProId(),
                lista.get(i).getProDescricao(),
                lista.get(i).getProBarras(),
                lista.get(i).getVenQuantidade(),
                lista.get(i).getProDias()
            });
        }
     }
     
     private void preencherTabelaMovimento(List<ModeloMovimento> lista){
        tabela.limpaTabela();
        for (int i = 0; i < lista.size(); i++) {
            tabela.linhas(new Object[]{
                //"Código", "Descrição","Barras", "Qtde" , "Qtde Dias"
                lista.get(i).getProId(),
                lista.get(i).getProDescricao(),
                lista.get(i).getProBarras(),
                lista.get(i).getMovQuantidade(),
                lista.get(i).getProDias()
            });
        }
     }
}