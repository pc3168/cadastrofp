package br.com.pc.natusfarma.app;

import br.com.pc.natusfarma.classe.App;
import br.com.pc.natusfarma.classe.DadosFP1;
import br.com.pc.util.Mensagem;


/**
 *
 * @author Paulo César
 */
public class Aplicacao {
    
    //private static FrameInterno frameDadosFP;
    
    public static void main(String[] args) {
        
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    App app = new App();
                    if (app.dadosFP == null) {
                        app.dadosFP = new br.com.pc.formularios.FrameInterno(new DadosFP1(), DadosFP1.TITULO + " "+ DadosFP1.VERSAO ,false, false, true);
                        //frameDadosFP.verificaSeVisivel();
                        app.desktop.add(app.dadosFP);
                    }/*else{
                        frameDadosFP.verificaSeContemNoDesktopPane(app.desktop);
                    }*/
                    app.dadosFP.verificaSeVisivel();
                    app.dadosFP.centralizar(app.desktop);
                    
                    app.setVisible(true);
                    
                    app.dadosFP.setVisible(true);
                    
                } catch (Exception e) {
                    Mensagem.mensagem("O sistema apresentou um erro e será fechado."+System.lineSeparator()+e);
                    e.printStackTrace();
                    System.exit(0);
                }
            }
        });
    }
}
