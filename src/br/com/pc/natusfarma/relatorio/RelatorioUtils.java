
package br.com.pc.natusfarma.relatorio;

import java.awt.BorderLayout;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Map;
import javax.swing.JFrame;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JRViewer;

/**
 *
 * @author Paulo César
 */
public class RelatorioUtils {
    
    public static void abrirRelatorio(String titulo, InputStream inputStream, Map parametros, Connection conexao) throws Exception{
        JasperPrint print = JasperFillManager.fillReport(inputStream, parametros, conexao);
        visualizarRelatorio(titulo, print);
    }
    
     public static void abrirRelatorio(String titulo, InputStream inputStream, Map parametros, JRDataSource dataSource) throws Exception{
        JasperPrint print = JasperFillManager.fillReport(inputStream, parametros, dataSource);
        visualizarRelatorio(titulo, print);
    }
     
     public static void abrirRelatorio(String titulo, String arquivoJasper, Map parametros, Connection conexao) throws Exception{
        JasperPrint print = JasperFillManager.fillReport(arquivoJasper, parametros, conexao);
        visualizarRelatorio(titulo, print);
    }
    
     public static void abrirRelatorio(String titulo, String arquivoJasper, Map parametros, JRDataSource dataSource) throws Exception{
        JasperPrint print = JasperFillManager.fillReport(arquivoJasper, parametros, dataSource);
        visualizarRelatorio(titulo, print);
    }
    
    private static void visualizarRelatorio(String titulo, JasperPrint print){
        JRViewer viewer = new JRViewer(print);
        JFrame frameRelatorio = new JFrame(titulo);
        frameRelatorio.add(viewer, BorderLayout.CENTER);
        frameRelatorio.setSize(800, 500);
        frameRelatorio.setLocationRelativeTo(viewer);
        //frameRelatorio.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frameRelatorio.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameRelatorio.setVisible(true);
    }
}
