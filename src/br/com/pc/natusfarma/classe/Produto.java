
package br.com.pc.natusfarma.classe;

import br.com.pc.natusfarma.bd.ProdutoSql;
import br.com.pc.natusfarma.form.FormProduto;
import br.com.pc.natusfarma.tabela.Tabela;
import br.com.pc.util.LimitNumero;
import br.com.pc.util.Mensagem;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 *
 * @author Paulo César
 */
public class Produto extends FormProduto{
    
    public static final String TITULO = "Cadastro de Produtos";
    public static final String VERSAO = "1.1";
    private Tabela tabela;
    private final String[] layoutColunas = {"Código", "Descrição","Barras", "Q Dias", "Ativo"};
    private ModeloProduto mp;
    
    public Produto(){
        inicializar();
    }
    
    private void inicializar(){
        mp = new ModeloProduto();
        verificarAtivo();
        
        tfCodigo.setDocument(new LimitNumero(8));
        montaTabela();
        
        tfCodigo.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                tfCodigo();
            }
        });
        
        tfDescricao.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                tfDescricao();
            }
        });
        
        tfBarras.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                tfBarras();
            }
        });
        
        tfDias.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                tfDias();
            }
        });
        
        btSalvarAtualizar.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                btSalvarAtualizar();
            }
        });
        
        
        
        tabela.getTabela().addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                tabela();
            }

        });
        
        tabela.getTabela().addKeyListener(new java.awt.event.KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                tabela();
            }
        });
        
        checkAtivo.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkAtivo.isSelected()) {
                    mp.setProAtivo("S");
                }else{
                    mp.setProAtivo("N");
                }
            }
        });
        
        
    }
    
    private void tfCodigo(){
        mp.setProId(Integer.parseInt(tfCodigo.getText()));
        List<ModeloProduto> lista = ProdutoSql.selectProduto(mp, ModeloProduto.PRO_ATIVO_ID);
        if (lista.isEmpty()) {
            return;
        }
        preecherdados(lista.get(0));
        preencherTabela(lista);
    }
    
    private void tfDescricao(){
        String descricao = tfDescricao.getText();
        if (descricao.length() < 3) {
            Mensagem.mensagem("Digite no mínimo 3 caracteres");
            return;
        }
        mp.setProDescricao(descricao);
        List<ModeloProduto> lista = ProdutoSql.selectProduto(mp, ModeloProduto.PRO_DESCRICAO);
        limpaTela();
        if (lista.isEmpty()) {
            tfDescricao.setText(descricao);
            return;
        }
        preencherTabela(lista);
    }
    
    private void tfBarras(){
        mp.setProBarras(tfBarras.getText());
        List<ModeloProduto> lista = ProdutoSql.selectProduto(mp, ModeloProduto.PRO_BARRAS);
        if (lista.isEmpty()) {
            return;
        }
        preecherdados(lista.get(0));
        preencherTabela(lista);
    }
    
    private void tfDias(){
        
    }
    
    private void btSalvarAtualizar(){
        if (tfCodigo.getText().trim().isEmpty() ||
                tfBarras.getText().isEmpty() ||
                tfDescricao.getText().isEmpty()) {
            return;
        }
        mp.setProId(Integer.parseInt(""+tfCodigo.getText()));
        mp.setProDescricao(tfDescricao.getText());
        mp.setProBarras(tfBarras.getText());
        mp.setProDias(Integer.parseInt(""+tfDias.getText()));
        mp.setProAtivo(""+cBS_N.getSelectedItem());
        ProdutoSql.updateProduto(mp);
        Mensagem.mensagem("Dados atualizados código "+mp.getProId()+" Descrição "+mp.getProDescricao()+" Barras "+mp.getProBarras()+" Dias "+mp.getProDias()+" Ativo "+mp.getProAtivo());
        limpaTela();
    }
    
    private void tabela(){
        mp.setProId(Integer.parseInt(""+tabela.dadosTabela(tabela.linha(), 0)));
        mp.setProDescricao((String) tabela.dadosTabela(tabela.linha(), 1));
        mp.setProBarras((String) tabela.dadosTabela(tabela.linha(), 2));
        mp.setProDias(Integer.parseInt(""+tabela.dadosTabela(tabela.linha(), 3)));
        mp.setProAtivo((String) tabela.dadosTabela(tabela.linha(), 4));
        preecherdados(mp);
    }
    
    private void limpaTela(){
        tfCodigo.setText("");
        tfDescricao.setText("");
        tfBarras.setText("");
        tfDias.setText("");
        cBS_N.setSelectedIndex(0);
        verificarAtivo();
        tabela.limpaTabela();
    }
    
    private void preecherdados(ModeloProduto mp){
        tfCodigo.setText(""+mp.getProId());
        tfDescricao.setText(mp.getProDescricao());
        tfBarras.setText(mp.getProBarras());
        tfDias.setText(""+mp.getProDias());
        cBS_N.setSelectedItem(""+mp.getProAtivo());
    }
    
    private void montaTabela(){
        tabela = new Tabela();
        painelPrincipal.add(tabela);
        painelPrincipal.setPreferredSize(new Dimension(100,200));
        painelPrincipal.setLayout(new java.awt.GridLayout());
        tabela.colunas(layoutColunas);
        tabela.tamanho(new Integer[]{50,220,105,50,35});
        //tabela.reajustavel(new Boolean[]{true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true});
    }
    
    private void preencherTabela(List<ModeloProduto> lista){
        tabela.limpaTabela();
        for (int i = 0; i < lista.size(); i++) {
            tabela.linhas(new Object[]{
                //"Código", "Descrição","Barras", "Q Dias"
                lista.get(i).getProId(),
                lista.get(i).getProDescricao(),
                lista.get(i).getProBarras(),
                lista.get(i).getProDias(),
                lista.get(i).getProAtivo()
            });
        }
     }
    
    private void verificarAtivo(){
        if (checkAtivo.isSelected()) {
            mp.setProAtivo("S");
        }else{
            mp.setProAtivo("N");
        }  
    }
    
    
}
