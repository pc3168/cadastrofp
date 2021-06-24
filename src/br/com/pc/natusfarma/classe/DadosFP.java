package br.com.pc.natusfarma.classe;

import br.com.pc.formularios.FrameJDialog;
import br.com.pc.natusfarma.bd.DadosFPSql;
import br.com.pc.natusfarma.form.FormDadosFP;
import br.com.pc.util.FormatacaoMaskara;
import br.com.pc.util.Mensagem;
import br.com.pc.util.ValidaCPFeCNPJ;
import datechooser.beans.DateChooserCombo;
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
public class DadosFP extends FormDadosFP{

    public static final String TITULO = "Cadastro autorização";
    public static final String VERSAO = "1.1";
    //private FrameJDialog frameJDialogCadastroClientes;
    
    public DadosFP() {
        inicializar();
    }
    
    private void inicializar(){
        datechooser(calendarioVenda);
        datechooser(calendarioProxVenda);
        FormatacaoMaskara.formatacao(tfCpf, "###.###.###-##");
        FormatacaoMaskara.formatacao(tfAutorizacao, "###.###.###.###.###");
        
        calendarioVenda.addCommitListener(new datechooser.events.CommitListener() {
            @Override
            public void onCommit(datechooser.events.CommitEvent evt) {
                calendarioVenda();
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
        CadastroClientes cc = new CadastroClientes();
        mostrarDadosClientes(tfCpf.getText());
        System.out.println("fasdfasdf");
        cc.tfCpf.setText(tfCpf.getText());
        cc.tfNome.requestFocus();
        //FrameJDialog frameDialog = new FrameJDialog(null, true, cc, CadastroClientes.TITULO);
        //FrameDialog frameDialog = new FrameDialog(cc, CadastroClientes.TITULO );
        tfCpf.requestFocus();
        
    }
    
   
    private void btGravarAtualizar(){
        String cpf = CadastroClientes.tfCpfFormatado(tfCpf.getText());
        if (cpf.trim().equals("") || 
                autorizacaoSemFormatacao(tfAutorizacao.getText()).equals("") || 
                tfCupom.getText().trim().equals("")) {
            Mensagem.mensagem("Todos os campos são obrigatórios.");
            return;
        }
        if (!ValidaCPFeCNPJ.isValidCPF(cpf)) {
            Mensagem.mensagem("CPF é inválido.");
            tfCpf.requestFocus();
            return;
        }
        
        try {
            long autorizacao = Long.parseLong(autorizacaoSemFormatacao(tfAutorizacao.getText()));
            ModeloDadosFP dfm = new ModeloDadosFP();
            dfm.setFpAutorizacao(autorizacao);
            dfm.setFpCupom(Integer.parseInt(tfCupom.getText()));
            dfm.setCliCpf(tfCpf.getText());
            dfm.setFpData_venda(converteCalendarToDate(calendarioVenda.getSelectedDate()));
            dfm.setFpData_prox_venda(converteCalendarToDate(calendarioProxVenda.getSelectedDate()));
            if (verificaAutorizacao(autorizacao).isEmpty()) {
                DadosFPSql.insertDadosFP(dfm);
                Mensagem.mensagem("Dados inserido com Sucesso.");
            } else {
                DadosFPSql.updateDadosFP(dfm);
                Mensagem.mensagem("Dados atualizado com Sucesso.");
            }
        } catch (NumberFormatException e) {
            Mensagem.mensagem(""+e);
        } finally {
            limpaTela();
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
    
    private void calendarioVenda(){
        try {
            java.sql.Date data = converteStringToDate(calendarioVenda);
            Calendar c = converteDateToCalendar(data);
            c.add(Calendar.DAY_OF_MONTH, 30);
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
        if (valAutorizacao.isEmpty()) {
            return;
        }
        try {
            long autorizacao = Long.parseLong(valAutorizacao);
             //modificar essa linha para aproveitar o codigo da outra classe  par puxar os dados do clientes.
            ArrayList<ModeloDadosFP> listaDFP = verificaAutorizacao(autorizacao);
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
                }
            }
        } catch (NumberFormatException e) {
            Mensagem.mensagem("O valor da autorização não é um número.\n"+e);
        }
            
    }
    
    private ArrayList<ModeloDadosFP> verificaAutorizacao(long autorizacao){
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
    
    private void mostrarDadosClientes(String cpf){
        List<ModeloClientes> listaClientes = CadastroClientes.verificaCadastroCpf(cpf);
        if (!listaClientes.isEmpty()) {
            //tfCpf.setText(listaClientes.get(0).getCliCpf());
            lNome.setText(listaClientes.get(0).getCliNome());
            lEndereco.setText(listaClientes.get(0).getCliEndereco());
            lNumero.setText(""+listaClientes.get(0).getCliNumero());
            lBairro.setText(listaClientes.get(0).getCliBairro());
            //tfComplemento.setText(listaClientes.get(0).getCliComplemento());
            //tfCep.setText(listaClientes.get(0).getCliCep());
            //tfCidade.setText(listaClientes.get(0).getCliCidade());
            //cbEstado.setSelectedItem(listaClientes.get(0).getCliEstado());
            lTelefone.setText(listaClientes.get(0).getCliTelefone1());
            //tfTelefone2.setText(listaClientes.get(0).getCliTelefone2());
            //tfCodSistema.setText(""+listaClientes.get(0).getCliCodigosistema());
        } else {
            cadastroClientesJDialog();
        }
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
}
