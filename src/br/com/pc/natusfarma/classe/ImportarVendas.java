package br.com.pc.natusfarma.classe;


import br.com.pc.natusfarma.bd.ProdutoSql;
import br.com.pc.natusfarma.bd.VendasSql;
import br.com.pc.natusfarma.form.FormImportar;
import br.com.pc.natusfarma.tabela.Tabela;
import br.com.pc.util.Mensagem;
import br.com.pc.util.Unicode;
import br.com.pc.util.jfilechoose.EscolherArquivo;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Paulo César 
 */
public class ImportarVendas extends FormImportar{
    
    public static final String VERSAO = "VERSÃO 1.0";
    public static final String TITULO = "Importar Vendas";
    private java.io.File arquivo;
    private Tabela tabela;
    private String[] layoutColunas = {"Cupom", "Caixa","Data Venda", "Código" , "Produto","EAN","Qtde","Nr.Autorização"};
    private List<ModeloVendas> lista;

    public ImportarVendas() {
        inicializar();
    }
    
    private void inicializar(){
        tfNome.setEditable(false);
        cbUnicode.addItem(Unicode.ISO_8859_1);
        cbUnicode.addItem(Unicode.UTF_8);
        barra.setStringPainted(true);
        btGravar.setEnabled(false);
        montaTabela();
        
        btNome.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                btNome();
            }
        });
        
        btImportar.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                btImportar();
            }
        });
        
        btGravar.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                btGravar();
            }
        });
        
        btLayout.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                btLayout();
            }
        });
        
        
    }
    
    private void btNome(){  
        EscolherArquivo es = EscolherArquivo.getInstance();
        barra.setValue(0);
        lista = null;
        java.io.File file = es.abrir(this);
        if (file == null) {
            tfNome.setText("");
            arquivo = file;
            return;
        }
        arquivo = file;
        tfNome.setText(file.getAbsolutePath());
    }

    private void btImportar(){
        if (arquivo == null) {return;}
        if (!arquivo.isFile()){return;}
        if (lista != null) {
            lista.clear();
        }
        lista = lerArquivoLayout();
        if (lista.isEmpty()) {
            return;
        }
        tabela.limpaTabela();
        barra.setMaximum(lista.size());
        for (int i = 0; i < lista.size(); i++) {
            tabela.linhas(new Object[]{
                //"Cupom", "Caixa","Data Venda", "Código" , "Produto","EAN","Qtde","Nr.Autorização
                lista.get(i).getVenCupom(),
                lista.get(i).getVenCaixa(),
                lista.get(i).getVenDataVenda(),
                lista.get(i).getProId(),
                lista.get(i).getProDescricao(),
                lista.get(i).getProBarras(),
                lista.get(i).getVenQuantidade(),
                lista.get(i).getVenAutorizacao(),
            });
            barra.setValue(i+1);
        }
        btGravar.setEnabled(true);
    }
    
    private void btGravar(){
        if (tabela.getModelo().getColumnCount() == 0) {
            return;
        }
        desativar(false);
        barra.setValue(0);
        new Thread(new Runnable(){   
            public void run(){   
                for (int i = 0; i < lista.size(); i++) {
                    if (ProdutoSql.selectProduto(lista.get(i), ModeloProduto.PRO_ID).isEmpty()) {
                        ProdutoSql.insertProduto(lista.get(i));
                    }
                    if (VendasSql.selectVendasAutCupomCodigo(lista.get(i), null).isEmpty()) {
                        VendasSql.insertVendas(lista.get(i));
                    }else{
                        VendasSql.updateVendas(lista.get(i));
                    }
                    barra.setValue(i+1);
                }
                Mensagem.mensagem("Vendas importadas com sucesso.");
                tabela.limpaTabela();
                lista.clear();
                desativar(true);
            }   
        }).start(); 
    }
    
    private void btLayout(){
        String mensagem = "";
        for (int i = 0; i < layoutColunas.length; i++) {
            if (i!=0) {
                mensagem += " ; ";
            }
            mensagem += layoutColunas[i];
        }
        Mensagem.mensagem("O Layout deve conter na primeira linha a sequencia abaixo separado por ; "+System.lineSeparator()+mensagem);
    }
    
    private List<ModeloVendas> lerArquivoLayout() {
        java.io.BufferedReader br;
        List<ModeloVendas> lista = new ArrayList();
        ModeloVendas mv;
        String[] linha ;
        int valor = 0;
        int[] posicao = null;
        try{
            br = new java.io.BufferedReader(new java.io.InputStreamReader(new java.io.FileInputStream(arquivo), ""+cbUnicode.getSelectedItem()));
            
            while(br.ready()){
                linha = br.readLine().split(";");
                if (valor == 0) {
                    posicao = montaLayout(linha);
                    //"Cupom", "Caixa","Data Venda", "Código" , "Produto","EAN","Qtde","Nr.Autorização
                    valor++;
                }else{
                    mv = new ModeloVendas();
                    mv.setVenCupom(Integer.parseInt(linha[posicao[0]].trim()));
                    mv.setVenCaixa(Integer.parseInt(linha[posicao[1]].trim()));
                    mv.setVenDataVenda(linha[posicao[2]].trim());
                    mv.setVenQuantidade(Integer.parseInt(linha[posicao[6]].trim()));
                    mv.setVenAutorizacao(Long.parseLong(linha[posicao[7]].trim()));

                    mv.setProId(Integer.parseInt(linha[posicao[3]].trim()));
                    mv.setProDescricao(linha[posicao[4]].trim());
                    mv.setProBarras(linha[posicao[5]].trim());
                    //mv.setProAtivo("S");
                    lista.add(mv);
                }
                
            }
            br.close();  
        } catch (Exception ex) {
            Mensagem.mensagem(""+ex, "Erro", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        return lista;
    }
    
    private int[] montaLayout(String[] linha){
        int[] posicao = new int[layoutColunas.length];
        for (int i = 0; i < layoutColunas.length; i++) {
            for (int j = 0; j < linha.length; j++) {
                if (layoutColunas[i].contains(linha[j])) {
                    posicao[i] = j;
                    break;
                }
            }
        }
        return posicao;
    }
    
    private void montaTabela(){
        tabela = new Tabela();
        painelPrincipal.add(tabela);
        painelPrincipal.setPreferredSize(new Dimension(100,300));
        painelPrincipal.setLayout(new java.awt.GridLayout());
        tabela.colunas(layoutColunas);
        tabela.tamanho(new Integer[]{70,50,80,65,270,120,40,120});
        //{"Cupom", "Caixa","Data Venda", "Código" , "Produto","EAN","Qtde","Nr.Autorização"};
        //tabela.reajustavel(new Boolean[]{true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true});
    }
    
    private void desativar(boolean ativar){
        btNome.setEnabled(ativar);
        btImportar.setEnabled(ativar);
        btGravar.setEnabled(ativar && btGravar.isEnabled());
    }
}
