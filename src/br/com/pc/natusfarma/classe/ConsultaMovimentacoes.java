package br.com.pc.natusfarma.classe;


import br.com.pc.formularios.FrameJDialog;
import br.com.pc.natusfarma.bd.ConsultaMovimentacoesSql;
import br.com.pc.natusfarma.bd.DadosFPSql;
import br.com.pc.natusfarma.bd.VendasSql;
import br.com.pc.natusfarma.form.FormConsultaMovimentacoes;
import br.com.pc.natusfarma.tabela.ParametrosTabelaLayout;
import br.com.pc.natusfarma.tabela.Tabela;
import br.com.pc.util.Mensagem;
import br.com.pc.util.Unicode;
import br.com.pc.util.jfilechoose.EscolherArquivo;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;


/**
 *
 * @author Paulo César
 */
public class ConsultaMovimentacoes extends FormConsultaMovimentacoes implements Runnable{
    
    public static final String VERSAO = "VERSÃO 1.0";
    public static final String TITULO = "Consulta Movimentações.";
    private Tabela tabela;
    Map<Integer,ParametrosTabelaLayout> mapTabela1;
    private final java.io.File file = new java.io.File("layoutcm.conf");
    private String dataInicial;
    private String dataFinal;
    
    public ConsultaMovimentacoes() {
        inicializar();
    }
    
    private void inicializar(){
        montaTabela();      
        calendario.setCurrentView(new datechooser.view.appearance.AppearancesList("Light"));
        calendario.setFormat(2);
        calendario.setWeekStyle(datechooser.view.WeekDaysStyle.NORMAL);
        calendario.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_PERIOD);
        checkLayout.setSelected(false);
        checkMovimentacao.setSelected(true);
        barra.setStringPainted(true);
        
        btPesquisar.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                btPesquisar();
            }
        });
        
        btExportar.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                btExportar();
            }
        });
        
        btMovimentacao.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                btMovimentacao();
            }
        });
        
        btAuto.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                btAuto();
            }
        });
        
        tabela.getTabela().addKeyListener(new java.awt.event.KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                eventoCliqueTabela1();
            }

        });
        
        tabela.getTabela().addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                eventoCliqueTabela1();
            }

            
        });
        
    }
    
    private void btPesquisar(){
        ModeloVendas mv = consultaVendas();
        List<ModeloConsultaMovimentacoes> lista = null;
        tabela.limpaTabela();
        desAtivar(false);
        if (checkMovimentacao.isSelected()) {
            lista = ConsultaMovimentacoesSql.selectConsultaMovimentacoes(mv,ModeloVendas.VEN_DATA_VENDA+"NULL");
        }else{
            lista = ConsultaMovimentacoesSql.selectConsultaMovimentacoes(mv,ModeloVendas.VEN_DATA_VENDA);
        }
        
        if (lista == null) {
            Mensagem.mensagem("Não foram encontrados registros.");
            return;
        }
        preencherTabela(lista);
        desAtivar(true);
    }
    
    private void btMovimentacao(){
        if (tabela.linha() < 0) {
            return;
        }
        try {
            DadosFP1 dadosFp = new DadosFP1();
            dadosFp.tfCpf.setText(tabela.dadosTabela(tabela.linha(),1).toString());
            dadosFp.tfAutorizacao.setText(tabela.dadosTabela(tabela.linha(),6).toString());
            FrameJDialog frameDialogDadosFP = new FrameJDialog(null, true, dadosFp, DadosFP1.TITULO + " "+ DadosFP1.VERSAO);
            frameDialogDadosFP.setVisible(true);

            dadosFp.btGravarAtualizar.requestFocus();
        } catch (Exception e) {
            Mensagem.mensagem(""+e);
        }
        
    }
    
    private void btAuto(){
        int n  = Mensagem.pergunta("Deseja movimentar todas as vendas relacionadas ao período informado "+calendario.getText(),
                "",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        if (n == javax.swing.JOptionPane.YES_OPTION) {
            String valor = javax.swing.JOptionPane.showInputDialog(null, "DIGITE A SENHA: ", "SENHA",javax.swing.JOptionPane.INFORMATION_MESSAGE);    
            if (valor != null) {
                if (valor.equals("123456")) {
                    new Thread(this).start();
                }else{
                    Mensagem.mensagem("Senha errada.");
                }
            }
            
        }
        
    }
    
    private void executar(){
        barra.setIndeterminate(true);
        desAtivar(false);
        DadosFP1 dadosFp1 = new DadosFP1();
        ModeloVendas mv;
        ModeloClientes mc;
        ModeloDadosFP mdf;
        List<ModeloConsultaMovimentacoes> lista = ConsultaMovimentacoesSql.selectConsultaMovimentacoes(consultaVendas(),ModeloVendas.VEN_DATA_VENDA+"NULL");
        barra.setMaximum(lista.size());
        barra.setIndeterminate(false);
        int count = 0;
        for (int i = 0; i < lista.size(); i++) {
            mc = lista.get(i).getClientes();
            mdf = lista.get(i).getDadosFp();
            mv = lista.get(i).getVendas();
            barra.setValue(i+1);
            if (mv.getVenCpf().equals("") || 
                    mc.getCliCpf().equals("") ||
                    mv.getVenAutorizacao() == 0 || 
                    mv.getVenCupom() == 0 || 
                    mv.getVenCaixa() == 0) {
                continue;
            }
            mdf.setFpAutorizacao(mv.getVenAutorizacao());
            mdf.setFpCupom(mv.getVenCupom());
            mdf.setCliCpf(mv.getVenCpf());
            mdf.setFpData_venda(mv.getVenDataVenda());
            mdf.setFpData_prox_venda(somaData(mv.getVenDataVenda(), mv.getProDias()));
            List<ModeloVendas> listaMv = VendasSql.selectVendasAutCupomCodigo(mv,ModeloVendas.VEN_AUTORIZACAO);
            if (dadosFp1.verificaAutorizacao(mv.getVenAutorizacao()).isEmpty()) {
                DadosFPSql.insertDadosFP(mdf);
                dadosFp1.movimento(listaMv);
                count++;
            } else {
                DadosFPSql.updateDadosFP(mdf);
                dadosFp1.movimento(listaMv);
                count++;
            }
        }
        if (count == 0) {
            Mensagem.mensagem("Nenhuma movimentação foi gravada.");
        }else{
            Mensagem.mensagem("Foram Gravados "+count+" movimentações.");
        }
        
        btPesquisar();
        desAtivar(true);
                
    }
        
    private void btExportar(){
        int linhas = tabela.getModelo().getRowCount();
        if (linhas == 0) {
            Mensagem.mensagem("Não existem dados para exportação.");
            return;
        }
        EscolherArquivo es = EscolherArquivo.getInstance();
        java.io.File f1 = es.salvar(this);
        java.io.File f = f1;
        if (f1 == null) {
            return;
        }
        if (!f1.getName().contains(".")) {
            f = new java.io.File(f1.getAbsolutePath()+".csv");
        }
        String mensagem = "";
        String cabecalho = "";
        if(checkLayout.isSelected()){
            if (!file.exists()) {
                Mensagem.mensagem("O Arquivo "+file+" não foi encontrado nas configurações.");
                return;
            }
            String[] colunas = lerArquivoLayout();
            for (int i = 0; i < linhas; i++) {
                for (int j = 0; j <  colunas.length; j++) {
                    if (j!=0) {
                        mensagem += ";";
                    }
                    if(i ==0){
                        if(j!=0){
                            cabecalho += ";";                            
                        }
                        cabecalho += tabela.getModelo().getColumnName(Integer.parseInt(colunas[j]));
                    }
                    mensagem += tabela.dadosTabela(i, Integer.parseInt(colunas[j]));
                }
                mensagem += System.lineSeparator();
            }
        }else{
            for (int i = 0; i < linhas; i++) {
                for (int j = 0; j < tabela.getModelo().getColumnCount(); j++) {
                    if (j != 0) {
                        mensagem += ";";
                    }
                    if (i==0) {
                        if (j!=0) {
                            cabecalho += ";";
                        }
                        cabecalho += tabela.getModelo().getColumnName(j);
                    }
                    mensagem += tabela.dadosTabela(i, j);
                }
                mensagem += System.lineSeparator();
            }
        }
        gerarArquivoCsv(cabecalho, false, f);
        //System.out.println(mensagem);
        gerarArquivoCsv(mensagem, true,f);
        Mensagem.mensagem("O arquivo "+f+" foi gerado.");
        
    }
    
    private void montaTabela(){
        colunasTabela1();
        tabela = new Tabela();
        tabela.limpaTabela();
        painelPrincipal.add(tabela);
        painelPrincipal.setPreferredSize(new Dimension(200,200));
        painelPrincipal.setLayout(new java.awt.GridLayout());
        //tabela.colunas(ConsultaSql.colunas().split(","));
        tabela.colunas((String[]) tabela.toArray(mapTabela1, ParametrosTabelaLayout.COLUNA));
        //tabela.tamanho(new Integer[]{100,100,100,100,100,100,100,100,100,100,100,100,100,100,100});
        tabela.tamanho((Integer[]) tabela.toArray(mapTabela1, ParametrosTabelaLayout.TAMANHO));
        tabela.defaultTableCellRenderer(mapTabela1.get(1).getColuna(), javax.swing.SwingConstants.CENTER);
        tabela.defaultTableCellRenderer(mapTabela1.get(5).getColuna(), javax.swing.SwingConstants.CENTER);
        tabela.defaultTableCellRenderer(mapTabela1.get(8).getColuna(), javax.swing.SwingConstants.CENTER);
       
    }
    
    
    private void preencherTabela(List<ModeloConsultaMovimentacoes> lista){
        if (lista.isEmpty()) {
            Mensagem.mensagem("O período informado não tem registros.", "Informação", javax.swing.JOptionPane.WARNING_MESSAGE);
        }
        for (int i = 0; i < lista.size(); i++) {
            tabela.linhas(new Object[]{
                //new javax.swing.ImageIcon(getClass().getResource("/br/com/pc/natusfarma/img/confirmar.jpg")),
                simNao(lista.get(i).getVendas().getVenCpf().equals("") ?  false : lista.get(i).getVendas().getVenCpf().equals(lista.get(i).getClientes().getCliCpf())),
                lista.get(i).getVendas().getVenCpf(),
                lista.get(i).getVendas().getVenNome(),
                lista.get(i).getVendas().getVenCupom(),
                lista.get(i).getVendas().getVenCaixa(),
                lista.get(i).getVendas().getVenDataVenda("dd/MM/yyyy"),
                lista.get(i).getVendas().getVenAutorizacao(),
                //new javax.swing.ImageIcon(getClass().getResource("/br/com/pc/natusfarma/img/confirmar.jpg"))
                simNao(lista.get(i).getVendas().getVenAutorizacao() == lista.get(i).getDadosFp().getFpAutorizacao())
            });
        }
    }
    
    private String simNao(boolean simNao){
        String sN = "NÃO";
        if (simNao) {
            sN = "SIM";
        }
        return sN;
    }
    
    
    private void eventoCliqueTabela1(){
        /*lista.
        ModeloMovimento mm = new ModeloMovimento();
        mm.setFpAutorizacao(Long.parseLong(tabela.dadosTabela(tabela.linha(), 6).toString()));
        java.util.List<ModeloMovimento> lista = MovimentoSql.selectMovimentoAutCodigo(mm, ModeloMovimento.FP_AUTORIZACAO);
        preencherTabela2(lista);*/
    }
    
    private String[] lerArquivoLayout() {
        java.io.BufferedReader br = null;
        String linha = "";
        try{
            br = new java.io.BufferedReader(new java.io.InputStreamReader(new java.io.FileInputStream(file), Unicode.ISO_8859_1));
            //while(br.ready()){
            linha = br.readLine();
            br.close();   
            //}
        } catch (Exception ex) {
            Mensagem.mensagem("Erro no arquivo "+file.getAbsolutePath()+" por favor configurar."+System.lineSeparator()+ex);
        }
        return linha.split(",");
    }
    
    private void gerarArquivoCsv(String valor,  boolean escreve, java.io.File f) {
        try {
            java.io.BufferedWriter bw = new java.io.BufferedWriter(new java.io.OutputStreamWriter(new java.io.FileOutputStream(f,escreve),Unicode.ISO_8859_1));
            bw.write(valor);
            bw.newLine();
            bw.close();
        } catch (Exception e) {
            Mensagem.mensagem("Erro ao gerar o arquivo. "+e);
        }
        
    }
    
    private void pegarData(){
        String[] data = calendario.getText().split(" - ");
        if (data.length == 1) {
            dataInicial = data[0];
            dataFinal = data[0];
        }else{
            dataInicial = data[0];
            dataFinal = data[1];
        }
    }
    
    private ModeloVendas consultaVendas(){
        pegarData();
        ModeloVendas mv = new ModeloVendas();
        try {
            mv.setVenDataVenda(getDataInicial());
            mv.setVenDataVendaF(getDataFinal());
        } catch (Exception e) {
            Mensagem.mensagem("Data Inválida. "+e);
            return null;
        }
        return mv;
    }
    
    private void colunasTabela1(){
        mapTabela1 = new HashMap<Integer,ParametrosTabelaLayout>();
        mapTabela1.put(1,new ParametrosTabelaLayout("CPF CADASTRADO",120));
        mapTabela1.put(2,new ParametrosTabelaLayout("CPF",100));
        mapTabela1.put(3,new ParametrosTabelaLayout("NOME",205));
        mapTabela1.put(4,new ParametrosTabelaLayout("CUPOM",60));
        mapTabela1.put(5,new ParametrosTabelaLayout("CAIXA", 60));
        mapTabela1.put(6,new ParametrosTabelaLayout("DATA_VENDA", 100));
        mapTabela1.put(7,new ParametrosTabelaLayout("AUTORIZAÇÃO",130));
        mapTabela1.put(8,new ParametrosTabelaLayout("MOVIMENTADO",100));
                                            
    }
    
    private java.sql.Date somaData(java.sql.Date dataSql, int dias){
        java.sql.Date data = dataSql;
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.add(cal.DAY_OF_MONTH, dias);
        data.setTime(cal.getTimeInMillis());
        return data;
    }
    
    private void desAtivar(boolean ativar){
        btExportar.setEnabled(ativar);
        btMovimentacao.setEnabled(ativar);
        btPesquisar.setEnabled(ativar);
        btAuto.setEnabled(ativar);
        calendario.setEnabled(ativar);
        checkLayout.setEnabled(ativar);
    }
        

    /**
     * @return the dataInicial
     */
    private String getDataInicial() {
        return dataInicial;
    }

   
    /**
     * @return the dataFinal
     */
    private String getDataFinal() {
        return dataFinal;
    }

    @Override
    public void run() {
        executar();
    }
    
}