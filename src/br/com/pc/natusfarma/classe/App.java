package br.com.pc.natusfarma.classe;

import br.com.pc.formularios.FrameInterno;
import br.com.pc.natusfarma.bd.ComandosSql;
import br.com.pc.natusfarma.form.FormApp;
import br.com.pc.natusfarma.relatorio.RelatorioUtils;
import br.com.pc.util.Mensagem;
import br.com.pc.util.bd.ConexaoBD;
import br.pc.natusfarma.integrador.classe.IntegradorVisual;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
/**
 *
 * @author Paulo César
 */
public class App extends FormApp{
    
    private static final String VERSAO_SISTEMA = "VERSÃO 18.04.25";
    private static final String VERSAO_BANCO = "VERSÃO 18.04.25";
    private final String DATA_CRIACAO = "24/11/2016";
    public static final String TITULO = "Cadastro Farmacia Popular \t .:"+VERSAO_SISTEMA+":. BD .:"+VERSAO_BANCO+":.";
    
    public static FrameInterno dadosFP;
    private FrameInterno cadastroClientes;
    private FrameInterno comandosSql;
    private FrameInterno consulta;
    private FrameInterno importarClientes;
    private FrameInterno importarVendas;
    private FrameInterno produto;
    private FrameInterno vendasImportadas;
    private FrameInterno integrador;
    private FrameInterno consultaMovimetacoes;

    public App() {
        inicializacao();
    }
    
    private void inicializacao(){
        this.setLocationRelativeTo(this);
        this.setTitle(TITULO);
        
        menuArquivo.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                menuArquivo();
            }
        });
        
        menuConfig.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                menuConfig();
            }
        });
        
        menuImportar.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                menuImportar();
            }
        });
        
        menuItemClientes.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                menuItemClientes();
            }
        });
        
        menuItemAutorizacao.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                menuItemAutorizacao();
            }
        });
        
        menuItemProduto.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                menuItemProduto();
            }
        });
        
        menuItemIntegrador.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                menuItemIntegrador();
            }
        });
        
        menuConsulta.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                menuConsulta();
            }
        });
        
       menuItemProximaCompra.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                menuItemProximaCompra();
            }
        });
       
       menuItemVendasImportadas.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                menuItemVendasImportadas();
            }
       });
       
       menuItemConsultaMovimentacao.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                menuItemConsultaMovimentacao();
            }
       }); 
       
        menuItemImportarClientes.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                menuItemImportarClientes();
            }
        });
        
        menuItemImportarDadosSistema.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                menuItemImportarVendas();
            }
        });
        
        menuItemSair.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                menuItemSair();
            }
        });
        
        menuItemRelatorioClientes.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                menuItemRelatorioClientes();
            }
        });
        
        menuItemSobre.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                menuItemSobre();
            }
        });
        
        menuItemComandos.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                menuItemComandos();
            }
        });
        
        menuCkBoxConexao.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                menuCkBoxConexao();
            }
        });
    }
    
    private void menuArquivo(){
        //Aqui escreve o código
    }
    
    private void menuConfig(){
        //Aqui escreve o código
    }
    
    private void menuImportar(){
        //Aqui escreve o código
    }
    
    private void menuItemClientes(){
        if (cadastroClientes == null) {
            cadastroClientes = new br.com.pc.formularios.FrameInterno(new CadastroClientes(), CadastroClientes.TITULO + " "+ CadastroClientes.VERSAO ,false, false, true);
            desktop.add(cadastroClientes);
        }else{
            cadastroClientes = new br.com.pc.formularios.FrameInterno(new CadastroClientes(), CadastroClientes.TITULO + " "+ CadastroClientes.VERSAO ,false, false, true);
            cadastroClientes.verificaSeContemNoDesktopPane(desktop);
        }
        cadastroClientes.verificaSeVisivel();
        cadastroClientes.centralizar(desktop);
        cadastroClientes.setVisible(true);
    }
    
    private void menuItemAutorizacao(){
        if (dadosFP == null) {
            dadosFP = new br.com.pc.formularios.FrameInterno(new DadosFP1(), DadosFP1.TITULO + " "+ DadosFP1.VERSAO ,false, false, true);
            desktop.add(dadosFP);
        }else{
            dadosFP.verificaSeContemNoDesktopPane(desktop);
        }
        dadosFP.verificaSeVisivel();
        dadosFP.centralizar(desktop);
        dadosFP.setVisible(true);
    }
    
    private void menuItemProduto(){
        if (produto == null) {
            produto = new br.com.pc.formularios.FrameInterno(new Produto(), Produto.TITULO + " "+ Produto.VERSAO ,false, false, true);
            desktop.add(produto);
        }else{
            produto.verificaSeContemNoDesktopPane(desktop);
        }
        produto.verificaSeVisivel();
        produto.centralizar(desktop);
        produto.setVisible(true);
    }
    
    private void menuItemIntegrador(){
        integrador = null;
        if (integrador == null) {
            integrador = new br.com.pc.formularios.FrameInterno(new IntegradorVisual(), IntegradorVisual.TITULO + " "+ IntegradorVisual.VERSAO ,false, false, true);
            desktop.add(integrador);
        }else{
            integrador.verificaSeContemNoDesktopPane(desktop);
        }
        integrador.verificaSeVisivel();
        integrador.centralizar(desktop);
        integrador.setVisible(true);
    }
    
    private void menuConsulta(){
        //Aqui escreve o código
    }
    
    private void menuItemProximaCompra(){
        if (consulta == null) {
            consulta = new br.com.pc.formularios.FrameInterno(new Consulta1(), Consulta1.TITULO + " "+ Consulta1.VERSAO ,false, true, true);
            desktop.add(consulta);
        }else{
            consulta.verificaSeContemNoDesktopPane(desktop);
        }
        consulta.verificaSeVisivel();
        consulta.centralizar(desktop);
        consulta.setVisible(true);
    }
    
    private void menuItemVendasImportadas(){
        if (vendasImportadas == null) {
            vendasImportadas = new br.com.pc.formularios.FrameInterno(new VendasImportadas(), VendasImportadas.TITULO + " "+ VendasImportadas.VERSAO ,false, true, true);
            desktop.add(vendasImportadas);
        }else{
            vendasImportadas.verificaSeContemNoDesktopPane(desktop);
        }
        vendasImportadas.verificaSeVisivel();
        vendasImportadas.centralizar(desktop);
        vendasImportadas.setVisible(true);
    }
    
    private void menuItemConsultaMovimentacao(){
        if (consultaMovimetacoes == null) {
            consultaMovimetacoes = new br.com.pc.formularios.FrameInterno(new ConsultaMovimentacoes(), ConsultaMovimentacoes.TITULO + " "+ ConsultaMovimentacoes.VERSAO ,false, true, true);
            desktop.add(consultaMovimetacoes);
        }else{
            consultaMovimetacoes.verificaSeContemNoDesktopPane(desktop);
        }
        consultaMovimetacoes.verificaSeVisivel();
        consultaMovimetacoes.centralizar(desktop);
        consultaMovimetacoes.setVisible(true);
    }
    
    private void menuItemImportarClientes(){
        if (importarClientes == null) {
            importarClientes = new br.com.pc.formularios.FrameInterno(new ImportarClientes(), ImportarClientes.TITULO + " "+ ImportarClientes.VERSAO ,false, true, true);
            desktop.add(importarClientes);
        }else{
            importarClientes.verificaSeContemNoDesktopPane(desktop);
        }
        importarClientes.verificaSeVisivel();
        importarClientes.centralizar(desktop);
        importarClientes.setVisible(true);
    }
    
    private void menuItemImportarVendas(){
        if (importarVendas == null) {
            importarVendas = new br.com.pc.formularios.FrameInterno(new ImportarVendas(), ImportarVendas.TITULO + " "+ ImportarVendas.VERSAO ,false, true, true);
            desktop.add(importarVendas);
        }else{
            importarVendas.verificaSeContemNoDesktopPane(desktop);
        }
        importarVendas.verificaSeVisivel();
        importarVendas.centralizar(desktop);
        importarVendas.setVisible(true);
    }
    
    private void menuItemSair(){
        System.exit(0);
    }
    
    private void menuItemRelatorioClientes(){
        menuItemRelatorioClientes.setEnabled(false);
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run(){
                try {
                    RelatorioUtils.abrirRelatorio("Clientes","relatorios/cliente.jasper", null, ConexaoBD.autoReConectarXml());
                     
                } catch (Exception ex) {
                    Mensagem.mensagem("Erro ao Gerar o relatório"+System.lineSeparator()+ex);
                }
            }
        });
        
        
        menuItemRelatorioClientes.setEnabled(true);    
    }
    
    private void menuItemSobre(){
        Mensagem.mensagem("...:SISTEMA "+VERSAO_SISTEMA+" :. BD .: "+VERSAO_BANCO+" :. .: "+DATA_CRIACAO+" :..."+System.lineSeparator()+"Programa ainda em desenvolvimento por Paulo César. O programa vem sendo desenvolvido para ajudar a organizar os dados dos clientes e quem sabe no futuro organizar as receitas do programa Farmácia Popular. Com todas as receitas digitalizadas no sistema ai fica fácil o controle quando ocorrer uma auditória do programa Farmácia Popular.");
    }
    
    private void menuCkBoxConexao(){
        if (menuCkBoxConexao.isSelected()) {
            try {
                ConexaoBD.autoReConectarXml();
                menuCkBoxConexao.setSelected(true);
            } catch (SQLException | IOException ex) {
                Mensagem.mensagem("Erro ao conectar no banco de dados."+System.lineSeparator()+ex);
                menuCkBoxConexao.setSelected(false);
            }
        }else{
            try {
                ConexaoBD.desconetar();
            } catch (SQLException ex) {
                Mensagem.mensagem(""+ex);
            }
        }
    }
    
    private void menuItemComandos(){
        if (comandosSql == null) {
            comandosSql = new br.com.pc.formularios.FrameInterno(new ComandosSql(), ComandosSql.TITULO + " "+ ComandosSql.VERSAO ,false, true, true);
            desktop.add(comandosSql);
        }else{
            comandosSql.verificaSeContemNoDesktopPane(desktop);
        }
        comandosSql.verificaSeVisivel();
        comandosSql.centralizar(desktop);
        comandosSql.setVisible(true);
    }
   
    
    
    
}
