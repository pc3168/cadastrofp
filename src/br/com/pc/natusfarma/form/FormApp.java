/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.pc.natusfarma.form;

/**
 *
 * @author Paulo
 */
public class FormApp extends javax.swing.JFrame {

    /**
     * Creates new form App
     */
    public FormApp() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        desktop = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuArquivo = new javax.swing.JMenu();
        menuItemClientes = new javax.swing.JMenuItem();
        menuItemAutorizacao = new javax.swing.JMenuItem();
        menuItemProduto = new javax.swing.JMenuItem();
        menuItemIntegrador = new javax.swing.JMenuItem();
        menuConsulta = new javax.swing.JMenu();
        menuItemProximaCompra = new javax.swing.JMenuItem();
        menuItemVendasImportadas = new javax.swing.JMenuItem();
        menuItemConsultaMovimentacao = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        menuImportar = new javax.swing.JMenu();
        menuItemImportarClientes = new javax.swing.JMenuItem();
        menuItemImportarDadosSistema = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuItemSair = new javax.swing.JMenuItem();
        menuRelatorio = new javax.swing.JMenu();
        menuItemRelatorioClientes = new javax.swing.JMenuItem();
        menuConfig = new javax.swing.JMenu();
        menuCkBoxConexao = new javax.swing.JCheckBoxMenuItem();
        menuItemComandos = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        menuItemSobre = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout desktopLayout = new javax.swing.GroupLayout(desktop);
        desktop.setLayout(desktopLayout);
        desktopLayout.setHorizontalGroup(
            desktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 948, Short.MAX_VALUE)
        );
        desktopLayout.setVerticalGroup(
            desktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 454, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(desktop)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(desktop)
                .addContainerGap())
        );

        menuArquivo.setText("Arquivo");

        menuItemClientes.setText("Clientes");
        menuArquivo.add(menuItemClientes);

        menuItemAutorizacao.setText("Autoriza????o");
        menuArquivo.add(menuItemAutorizacao);

        menuItemProduto.setText("Produto");
        menuArquivo.add(menuItemProduto);

        menuItemIntegrador.setText("Integrador");
        menuArquivo.add(menuItemIntegrador);

        menuConsulta.setText("Consulta");

        menuItemProximaCompra.setText("Pr??xima Compra");
        menuConsulta.add(menuItemProximaCompra);

        menuItemVendasImportadas.setText("Vendas Importadas");
        menuConsulta.add(menuItemVendasImportadas);

        menuItemConsultaMovimentacao.setText("Movimenta????es");
        menuConsulta.add(menuItemConsultaMovimentacao);

        menuArquivo.add(menuConsulta);
        menuArquivo.add(jSeparator2);

        menuImportar.setText("Importar");

        menuItemImportarClientes.setText("Clientes");
        menuImportar.add(menuItemImportarClientes);

        menuItemImportarDadosSistema.setText("Vendas");
        menuImportar.add(menuItemImportarDadosSistema);

        menuArquivo.add(menuImportar);
        menuArquivo.add(jSeparator1);

        menuItemSair.setText("Sair");
        menuArquivo.add(menuItemSair);

        jMenuBar1.add(menuArquivo);

        menuRelatorio.setText("Relat??rios");

        menuItemRelatorioClientes.setText("Clientes");
        menuItemRelatorioClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemRelatorioClientesActionPerformed(evt);
            }
        });
        menuRelatorio.add(menuItemRelatorioClientes);

        jMenuBar1.add(menuRelatorio);

        menuConfig.setText("Ajuda");

        menuCkBoxConexao.setSelected(true);
        menuCkBoxConexao.setText("ConectarBD");
        menuConfig.add(menuCkBoxConexao);

        menuItemComandos.setText("Comandos");
        menuConfig.add(menuItemComandos);
        menuConfig.add(jSeparator3);

        menuItemSobre.setText("Sobre");
        menuConfig.add(menuItemSobre);

        jMenuBar1.add(menuConfig);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuItemRelatorioClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemRelatorioClientesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuItemRelatorioClientesActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormApp().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JDesktopPane desktop;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    public javax.swing.JMenu menuArquivo;
    public javax.swing.JCheckBoxMenuItem menuCkBoxConexao;
    public javax.swing.JMenu menuConfig;
    public javax.swing.JMenu menuConsulta;
    public javax.swing.JMenu menuImportar;
    public javax.swing.JMenuItem menuItemAutorizacao;
    public javax.swing.JMenuItem menuItemClientes;
    public javax.swing.JMenuItem menuItemComandos;
    public javax.swing.JMenuItem menuItemConsultaMovimentacao;
    public javax.swing.JMenuItem menuItemImportarClientes;
    public javax.swing.JMenuItem menuItemImportarDadosSistema;
    public javax.swing.JMenuItem menuItemIntegrador;
    public javax.swing.JMenuItem menuItemProduto;
    public javax.swing.JMenuItem menuItemProximaCompra;
    public javax.swing.JMenuItem menuItemRelatorioClientes;
    public javax.swing.JMenuItem menuItemSair;
    public javax.swing.JMenuItem menuItemSobre;
    public javax.swing.JMenuItem menuItemVendasImportadas;
    private javax.swing.JMenu menuRelatorio;
    // End of variables declaration//GEN-END:variables
}
