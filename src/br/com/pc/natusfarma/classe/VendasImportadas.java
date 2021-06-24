package br.com.pc.natusfarma.classe;


import br.com.pc.natusfarma.bd.VendasSql;
import br.com.pc.natusfarma.form.FormVendasImportadas;
import br.com.pc.natusfarma.tabela.ParametrosTabelaLayout;
import br.com.pc.natusfarma.tabela.Tabela;
import br.com.pc.util.FormatacaoMaskara;
import br.com.pc.util.Mensagem;
import br.com.pc.util.Unicode;
import br.com.pc.util.ValidaCPFeCNPJ;
import br.com.pc.util.jfilechoose.EscolherArquivo;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 * @author Paulo César
 */
public class VendasImportadas extends FormVendasImportadas{
    
    public static final String VERSAO = "VERSÃO 1.0";
    public static final String TITULO = "Vendas Importadas.";
    private Tabela tabela;
    Map<Integer,ParametrosTabelaLayout> mapTabela1;
    private final java.io.File file = new java.io.File("layoutvi.conf");
    private String dataInicial;
    private String dataFinal;
    private List<ModeloVendas> lista;
    
    public VendasImportadas() {
        inicializar();
    }
    
    private void inicializar(){
        montaTabela();      
        calendario.setCurrentView(new datechooser.view.appearance.AppearancesList("Light"));
        calendario.setFormat(2);
        calendario.setWeekStyle(datechooser.view.WeekDaysStyle.NORMAL);
        calendario.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_PERIOD);
        checkLayout.setSelected(false);
        FormatacaoMaskara.formatacao( tfCpf, "###.###.###-##");
        
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
        
        tfCpf.addKeyListener(new java.awt.event.KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    tfCpf();
                }
            }
        });
    }
    
    private boolean validaCpf(){
        String cpf = CadastroClientes.tfCpfFormatado(tfCpf.getText());
        if (!cpf.trim().equals("")) { 
            if (!ValidaCPFeCNPJ.isValidCPF(cpf)) {
                Mensagem.mensagem("CPF é inválido.");
                tfCpf.requestFocus();
                return false;
            }
        }
        return true;
    }
    
    private void tfCpf(){
        if (!validaCpf()) {
            return;
        }
        ModeloVendas mv = new ModeloVendas();
        List<ModeloVendas> lista;
        tabela.limpaTabela();
        mv.setVenCpf(tfCpf.getText());
        lista = VendasSql.selectVendasAutCupomCodigo(mv, ModeloVendas.VEN_CPF);
        
        preencherTabela(lista);
    }
    
    private void btPesquisar(){
        String cpf = CadastroClientes.tfCpfFormatado(tfCpf.getText());
        pegarData();
        ModeloVendas mv = new ModeloVendas();
        List<ModeloVendas> lista = null;
        tabela.limpaTabela();
        try {
            mv.setVenDataVenda(getDataInicial());
            mv.setVenDataVendaF(getDataFinal());
        } catch (Exception e) {
            Mensagem.mensagem("Data Inválida. "+e);
        }
        if (cpf.trim().equals("")) {
            lista = VendasSql.selectVendasAutCupomCodigo(mv,ModeloVendas.VEN_DATA_VENDA);
        }else{
            if (validaCpf()) {
                mv.setVenCpf(tfCpf.getText());
                lista = VendasSql.selectVendasAutCupomCodigo(mv,ModeloVendas.VEN_DATA_VENDA+ModeloVendas.VEN_CPF);
            }
        }
        if (lista == null) {
            Mensagem.mensagem("Não foram encontrados registros.");
            return;
        }
        preencherTabela(lista);
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
        //tabela.colunas(new String[]{"CPF","NOME","TELEFONE1","TELEFONE2","ENDEREÇO","BAIRRO","COMPLEMENTO","CEP","CIDADE","UF","AUTORIZAÇÃO","CUPOM","DATA_VENDA","DATA_PROXIMA_VENDA","COD.SISTEMA"});
        tabela.colunas((String[]) tabela.toArray(mapTabela1, ParametrosTabelaLayout.COLUNA));
        //tabela.tamanho(new Integer[]{100,100,100,100,100,100,100,100,100,100,100,100,100,100,100});
        tabela.tamanho((Integer[]) tabela.toArray(mapTabela1, ParametrosTabelaLayout.TAMANHO));
        
        
    }
    
    
    private void preencherTabela(List<ModeloVendas> lista){
        if (lista.isEmpty()) {
            Mensagem.mensagem("O período informado não tem registros.", "Informação", javax.swing.JOptionPane.WARNING_MESSAGE);
        }
        for (int i = 0; i < lista.size(); i++) {
            tabela.linhas(new Object[]{
                lista.get(i).getVenId(),
                lista.get(i).getVenCpf(),
                lista.get(i).getVenNome(),
                lista.get(i).getVenCupom(),
                lista.get(i).getVenCaixa(),
                lista.get(i).getVenDataVenda("dd/MM/yyyy"),
                lista.get(i).getProId(),
                lista.get(i).getProDescricao(),
                lista.get(i).getProBarras(),
                lista.get(i).getVenQuantidade(),
                lista.get(i).getProDias(),
                lista.get(i).getVenAutorizacao()
            });
        }
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
    
    private void colunasTabela1(){
        mapTabela1 = new HashMap<Integer,ParametrosTabelaLayout>();
        mapTabela1.put(1,new ParametrosTabelaLayout("ID",35));
        mapTabela1.put(2,new ParametrosTabelaLayout("CPF",100));
        mapTabela1.put(3,new ParametrosTabelaLayout("NOME",100));
        mapTabela1.put(4,new ParametrosTabelaLayout("CUPOM",60));
        mapTabela1.put(5,new ParametrosTabelaLayout("CAIXA", 35));
        mapTabela1.put(6,new ParametrosTabelaLayout("DATA_VENDA", 100));
        mapTabela1.put(7, new ParametrosTabelaLayout("CÓDIGO",60));
        mapTabela1.put(8, new ParametrosTabelaLayout("DESCRIÇÃO",350));
        mapTabela1.put(9, new ParametrosTabelaLayout("BARRAS",110));
        mapTabela1.put(10, new ParametrosTabelaLayout("QTDE" ,50));
        mapTabela1.put(11, new ParametrosTabelaLayout("Q DIAS",50)); 
        mapTabela1.put(12,new ParametrosTabelaLayout("AUTORIZAÇÃO",130));
                                            
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

}
