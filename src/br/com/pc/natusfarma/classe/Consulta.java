package br.com.pc.natusfarma.classe;

import br.com.pc.natusfarma.bd.ConsultaSql;
import br.com.pc.natusfarma.form.FormConsulta;
import br.com.pc.natusfarma.tabela.Tabela;
import br.com.pc.util.Mensagem;
import br.com.pc.util.Unicode;
import br.com.pc.util.jfilechoose.EscolherArquivo;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.util.List;


/**
 *
 * @author Paulo César
 */
public class Consulta extends FormConsulta{
    
    public static final String VERSAO = "VERSÃO 1.0";
    public static final String TITULO = "Consulta Próxima Vendas";
    private Tabela tabela;
    private final java.io.File file = new java.io.File("layout.conf");
    
    public Consulta() {
        inicializar();
    }
    
    private void inicializar(){
        montaTabela();
        calendario.setCurrentView(new datechooser.view.appearance.AppearancesList("Light"));
        calendario.setFormat(2);
        calendario.setWeekStyle(datechooser.view.WeekDaysStyle.NORMAL);
        calendario.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_PERIOD);
        checkLayout.setSelected(false);
        
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
    }
    
    private void btPesquisar(){
        String[] data = calendario.getText().split(" - ");
        String dataInicial;
        String dataFinal;
        if (data.length == 1) {
            dataInicial = data[0];
            dataFinal = data[0];
        }else{
            dataInicial = data[0];
            dataFinal = data[1];
        }
        ModeloDadosFP dfp = new ModeloDadosFP();
        List<ModeloDadosFP> lista;
        tabela.limpaTabela();
        
        if (radioProxVenda.isSelected()) {
            dfp.setFpData_prox_venda(converteDataSql("dd/MM/yyyy", dataInicial));
            dfp.setFpData_prox_vendaF(converteDataSql("dd/MM/yyyy", dataFinal));
            lista = ConsultaSql.selectConsulta(dfp,ModeloDadosFP.FP_DATA_PROX_VENDA);
        }else{
            dfp.setFpData_venda(converteDataSql("dd/MM/yyyy", dataInicial));
            dfp.setFpData_vendaF(converteDataSql("dd/MM/yyyy", dataFinal));
            lista = ConsultaSql.selectConsulta(dfp,ModeloDadosFP.FP_DATA_VENDA);
        }
        if (lista.isEmpty()) {
            Mensagem.mensagem("O período informado não tem registros.", "Informação", javax.swing.JOptionPane.WARNING_MESSAGE);
        }
        for (int i = 0; i < lista.size(); i++) {
            tabela.linhas(new Object[]{
                lista.get(i).getCliNome(),
                lista.get(i).getCliCpf(),
                lista.get(i).getCliEndereco(),
                lista.get(i).getCliNumero(),
                lista.get(i).getCliBairro(),
                lista.get(i).getCliComplemento(),
                lista.get(i).getCliCep(),
                lista.get(i).getCliCidade(),
                lista.get(i).getCliEstado(),
                lista.get(i).getCliTelefone1(),
                lista.get(i).getCliTelefone2(),
                lista.get(i).getCliCodigosistema(),
                lista.get(i).getFpAutorizacao(),
                lista.get(i).getFpCupom(),
                lista.get(i).getFpData_venda(),
                lista.get(i).getFpData_prox_venda()
            });
        }
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
        tabela = new Tabela();
        tabela.limpaTabela();
        painelPrincipal.add(tabela);
        painelPrincipal.setPreferredSize(new Dimension(100,300));
        painelPrincipal.setLayout(new java.awt.GridLayout());
        tabela.colunas(ConsultaSql.colunas().split(","));
        //tabela.colunas(new String[]{"id","nome","Teste","id","nome","Teste"});
        tabela.tamanho(new Integer[]{100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100});
        //tabela.reajustavel(new Boolean[]{true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true});
        /*for (int i = 0; i < 10; i++) {
            tabela.linhas(new Object[]{"casa","Brinquedo","laranja","casa","Brinquedo","laranja"});
        }*/
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
    
    private java.sql.Date converteDataSql(String format, String data){
        java.sql.Date date = null;
        try {
            date = new java.sql.Date(new java.text.SimpleDateFormat(format).parse(data).getTime());
        } catch (ParseException ex) {
            Mensagem.mensagem("Erro ao obter a data ..... formato "+format+" data "+data+System.lineSeparator()+ex);
        }
        return date;
    }
    
}
