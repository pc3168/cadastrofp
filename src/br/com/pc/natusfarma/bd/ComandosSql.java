package br.com.pc.natusfarma.bd;

import br.com.pc.natusfarma.form.FormComandos;
import br.com.pc.util.Mensagem;
import br.com.pc.util.bd.ConexaoBD;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 *
 * @author Paulo César
 */
public class ComandosSql extends FormComandos{
    
    public static final String TITULO = "Executar Consultas SQL";
    public static final String VERSAO = "1.0";
    
    private static PreparedStatement pstmt;
    private static Connection conn;

    public ComandosSql() {
        inicializar();
    }
    
    private void inicializar(){
        btConsulta.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                btConsulta();
            }
        });
        
        btLimpar.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                btLimpar();
            }
        });
        
        btAtualiza.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                btAtualiza();
            }
        });
        
    taCampo2.setEditable(false);
    }
    
    private void btConsulta(){
        try{
            conn = ConexaoBD.autoReConectarXml();
            pstmt = conn.prepareStatement(taCampo1.getText());
            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            for (int i = 1; i <= meta.getColumnCount(); i++) {
                taCampo2.append(meta.getColumnName(i)+";\t");
            }
            taCampo2.append(System.lineSeparator());
            while(rs.next()){
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    taCampo2.append(rs.getString(i)+";\t");
                }
                taCampo2.append(System.lineSeparator());
            }
        } catch(SQLException | IOException e){
            Mensagem.mensagem(""+e);
        }finally {
            ConexaoBD.fechaConexao(conn, pstmt);
        }
    }
    
    private void btLimpar(){
        taCampo2.setText("");
    }
    
    private void btAtualiza(){
        try{
            conn = ConexaoBD.autoReConectarXml();
            pstmt = conn.prepareStatement(taCampo1.getText());
            int i = pstmt.executeUpdate();
            taCampo2.append(""+i);
        } catch(SQLException | IOException e){
            Mensagem.mensagem(""+e);
        }finally {
            ConexaoBD.fechaConexao(conn, pstmt);
        }
    }
   
        
}
