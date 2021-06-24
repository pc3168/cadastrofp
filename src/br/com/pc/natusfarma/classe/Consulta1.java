package br.com.pc.natusfarma.classe;

import br.com.pc.natusfarma.bd.ConsultaSql;
import br.com.pc.natusfarma.bd.MovimentoSql;
import br.com.pc.natusfarma.form.FormConsulta1;
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
public class Consulta1 extends FormConsulta1{
    
    public static final String VERSAO = "VERSÃO 2.0";
    public static final String TITULO = "Consulta Próxima Vendas.";
    private Tabela tabela;
    private Tabela tabela2;
    Map<Integer,ParametrosTabelaLayout> mapTabela1;
    Map<Integer,ParametrosTabelaLayout> mapTabela2;
    //private final String[] layoutColunas2 = {"Código", "Descrição","Barras", "Qtde" , "Q Dias"};
    private final java.io.File file = new java.io.File("layout.conf");
    private String dataInicial;
    private String dataFinal;
    
    public Consulta1() {
        inicializar();
    }
    
    private void inicializar(){
        montaTabela2();
        montaTabela();      
        splitPainel();
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
        ModeloDadosFP dfp = new ModeloDadosFP();
        List<ModeloDadosFP> lista;
        tabela.limpaTabela();
        dfp.setCliCpf(tfCpf.getText());
        lista = ConsultaSql.selectConsulta(dfp,ModeloDadosFP.CLI_CPF);
        
        preencherTabela(lista);
    }
    
    private void btPesquisar(){
        String cpf = CadastroClientes.tfCpfFormatado(tfCpf.getText());
        pegarData();
        ModeloDadosFP dfp = new ModeloDadosFP();
        List<ModeloDadosFP> lista = null;
        tabela.limpaTabela();
        dfp.setCliCpf(tfCpf.getText());
        if (radioProxVenda.isSelected()) {
            dfp.setFpData_prox_venda("dd/MM/yyyy", getDataInicial());
            dfp.setFpData_prox_vendaF("dd/MM/yyyy", getDataFinal());
            if (cpf.trim().equals("")) {
                lista = ConsultaSql.selectConsulta(dfp,ModeloDadosFP.FP_DATA_PROX_VENDA);
            }else{
                if (validaCpf()) {
                    lista = ConsultaSql.selectConsulta(dfp,ModeloDadosFP.FP_DATA_PROX_VENDA+ModeloDadosFP.CLI_CPF);
                }
            }
        }else{
            dfp.setFpData_venda("dd/MM/yyyy", getDataInicial());
            dfp.setFpData_vendaF("dd/MM/yyyy", getDataFinal());
            if (cpf.trim().equals("")) {
                lista = ConsultaSql.selectConsulta(dfp,ModeloDadosFP.FP_DATA_VENDA);
            }else{
                if (validaCpf()) {
                    lista = ConsultaSql.selectConsulta(dfp,ModeloDadosFP.FP_DATA_VENDA+ModeloDadosFP.CLI_CPF);
                }
            }
        }
        if (lista == null) {
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
    
    private void montaTabela2(){
        colunasTabela2();
        tabela2 = new Tabela();
        painelItens.add(tabela2);
        painelItens.setPreferredSize(new Dimension(100,100));
        painelItens.setLayout(new java.awt.GridLayout());
        tabela2.colunas((String[]) tabela2.toArray(mapTabela2, ParametrosTabelaLayout.COLUNA));
//        tabela2.tamanho(new Integer[]{50,200,105,40,50});
        tabela2.tamanho((Integer[]) tabela2.toArray(mapTabela2, ParametrosTabelaLayout.TAMANHO)); 
        
    }
    
    /*private Object[] toArray(Map<Integer, ParametrosTabelaLayout> map1, Integer campo){
        Map<Integer, ParametrosTabelaLayout> map = map1;
        ParametrosTabelaLayout[] array = map.values().toArray(new ParametrosTabelaLayout[map.size()]);
        String[] colunas = new String[array.length];
        Integer[] tamanho = new Integer[array.length];
        Boolean[] reajustavel = new Boolean[array.length];
        Boolean[] editar = new Boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            colunas[i] = array[i].getColuna();
            tamanho[i] = array[i].getTamanho();
            reajustavel[i] = array[i].isReajustavel();
            editar[i] = array[i].isEditavel();
        }
        Object[] obj = null;
        switch(campo){
            case 1:obj = colunas; break;
            case 2:obj = tamanho; break;
            case 3:obj = reajustavel; break;
            case 4:obj = editar; break;
        }
        return obj;
        
    }*/
    
    
    private void preencherTabela(List<ModeloDadosFP> lista){
        if (lista.isEmpty()) {
            Mensagem.mensagem("O período informado não tem registros.", "Informação", javax.swing.JOptionPane.WARNING_MESSAGE);
            tabela2.limpaTabela();
        }
        for (int i = 0; i < lista.size(); i++) {
            tabela.linhas(new Object[]{
                lista.get(i).getCliCpf(),
                lista.get(i).getCliNome(),
                lista.get(i).getCliTelefone1(),
                lista.get(i).getCliTelefone2(),
                lista.get(i).getCliEndereco() +" , "+lista.get(i).getCliNumero()+" , "+lista.get(i).getCliComplemento(),
                lista.get(i).getCliBairro(),
                lista.get(i).getCliCep(),
                lista.get(i).getCliCidade() +" , "+lista.get(i).getCliEstado(),
                lista.get(i).getFpAutorizacao(),
                lista.get(i).getFpCupom(),
                lista.get(i).getFpData_venda("dd/MM/yyyy"),
                lista.get(i).getFpData_prox_venda("dd/MM/yyyy"),
                lista.get(i).getCliCodigosistema()
            });
        }
    }
    
    private void preencherTabela2(List<ModeloMovimento> lista){
        tabela2.limpaTabela();
        for (int i = 0; i < lista.size(); i++) {
            tabela2.linhas(new Object[]{
                //"Código", "Descrição","Barras", "Qtde" , "Qtde Dias"
                lista.get(i).getProId(),
                lista.get(i).getProDescricao(),
                lista.get(i).getProBarras(),
                lista.get(i).getMovQuantidade(),
                lista.get(i).getProDias()
            });
        }
     }
    
    
    private void eventoCliqueTabela1(){
        ModeloMovimento mm = new ModeloMovimento();
        mm.setFpAutorizacao(Long.parseLong(tabela.dadosTabela(tabela.linha(), 8).toString()));
        java.util.List<ModeloMovimento> lista = MovimentoSql.selectMovimentoAutCodigo(mm, ModeloMovimento.FP_AUTORIZACAO);
        preencherTabela2(lista);
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
        mapTabela1.put( 1,new ParametrosTabelaLayout("CPF",100));
        mapTabela1.put( 2,new ParametrosTabelaLayout("NOME",100));
        mapTabela1.put( 3,new ParametrosTabelaLayout("TELEFONE1",100));
        mapTabela1.put( 4,new ParametrosTabelaLayout("TELEFONE2",100));
        mapTabela1.put( 5,new ParametrosTabelaLayout("ENDEREÇO",100));
        //mapTabela1.put( 7,new ParametrosTabelaLayout("COMPLEMENTO",100));
        mapTabela1.put( 6,new ParametrosTabelaLayout("BAIRRO",100));
        mapTabela1.put( 7,new ParametrosTabelaLayout("CEP",100));
        mapTabela1.put( 8,new ParametrosTabelaLayout("CIDADE",100));
        mapTabela1.put( 9,new ParametrosTabelaLayout("AUTORIZAÇÃO",130));
        mapTabela1.put(10,new ParametrosTabelaLayout("CUPOM",60));
        mapTabela1.put(11,new ParametrosTabelaLayout("DATA_VENDA",100));
        mapTabela1.put(12,new ParametrosTabelaLayout("DATA_PROXIMA_VENDA",100));
        mapTabela1.put(13,new ParametrosTabelaLayout("COD.SISTEMA",100));
                                            
    }
    
    private void colunasTabela2(){
        mapTabela2 = new HashMap<Integer,ParametrosTabelaLayout>();
        mapTabela2.put(1, new ParametrosTabelaLayout("Código",60));
        mapTabela2.put(2, new ParametrosTabelaLayout("Descrição",350));
        mapTabela2.put(3, new ParametrosTabelaLayout("Barras",110));
        mapTabela2.put(4, new ParametrosTabelaLayout("Qtde" ,50));
        mapTabela2.put(5, new ParametrosTabelaLayout("Q Dias",50)); 
    }
    
    private void splitPainel(){
        splitPane.setOneTouchExpandable(true);
        splitPane.setContinuousLayout(true);
        splitPane.setResizeWeight(0.5);
        painelPrincipal.setMinimumSize(new Dimension(100, 200));
        //painelPrincipal.setMaximumSize(new Dimension(500, 100));
        painelItens.setMinimumSize(new Dimension(75, 100));
                
        splitPane.setDividerLocation(300); 
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
