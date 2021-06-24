package br.com.pc.natusfarma.tabela;

import br.com.pc.natusfarma.relatorio.RelatorioUtils;
import br.com.pc.util.bd.ConexaoBD;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;


/**
 *
 * @author Paulo César
 */
public class Teste {
    
    private void teste(){
        URL a = getClass().getResource("/br/com/pc/natusfarma/relatorio/cliente.jasper");
    }
    
    public static void main(String[] args) throws IOException, InterruptedException {
        try {
            /*
            
            java.sql.Date fpData_venda = new java.sql.Date(new Date().getTime());
            System.out.println(fpData_venda);
            
            Calendar cal = Calendar.getInstance();
            cal.setTime(fpData_venda);
            cal.add(cal.DAY_OF_MONTH, 30);
            fpData_venda.setTime(cal.getTimeInMillis());
            System.out.println(fpData_venda);*/
            //Connection conn = ConexaoBD.autoReConectarXml();
            //JasperPrint print = JasperFillManager.fillReport("relatorios/cliente.jasper", null,conn);
            //URL a = getClass().getResource("/br/com/pc/natusfarma/relatorio/cliente.jasper");
            //JasperPrint print = JasperFillManager.fillReport(a.toString(), null,conn);
            //JasperViewer.viewReport(print);
            //RelatorioUtils.abrirRelatorio("Clientes","relatorios/cliente.jasper", null, conn);
            
            java.io.File f = new java.io.File("C:\\cadastrofp\\cadastrofp4\\lib");
            java.io.File[] fa = f.listFiles();
            for (File file : fa) {
                System.out.println(file.getPath()+",");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
}
