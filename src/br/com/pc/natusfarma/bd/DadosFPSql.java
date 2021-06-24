
package br.com.pc.natusfarma.bd;

import br.com.pc.natusfarma.classe.ModeloDadosFP;
import br.com.pc.util.Mensagem;
import br.com.pc.util.bd.ConexaoBD;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Paulo César
 */
public class DadosFPSql {
    
    private static PreparedStatement pstmt;
    private static Connection conn;
    
    private static String insertDadosFP(){
        return "INSERT INTO "
                + ModeloDadosFP.T_DADOSFP
                + " ( "
                + "   " +   ModeloDadosFP.FP_AUTORIZACAO
                + " , " +   ModeloDadosFP.CLI_CPF
                + " , " +   ModeloDadosFP.FP_CUPOM
                + " , " +   ModeloDadosFP.FP_DATA_VENDA
                + " , " +   ModeloDadosFP.FP_DATA_PROX_VENDA
                + " ) "
                + " VALUES ( ?, ?, ?, ?, ? )";
    }
    
    public static void insertDadosFP(ModeloDadosFP dfp){
        try{
            conn = ConexaoBD.autoReConectarXml();
            pstmt = conn.prepareStatement(insertDadosFP());
            pstmt.setLong   (1, dfp.getFpAutorizacao());
            pstmt.setString (2, dfp.getCliCpf());
            pstmt.setInt    (3, dfp.getFpCupom());
            pstmt.setDate   (4, dfp.getFpData_venda());
            pstmt.setDate   (5, dfp.getFpData_prox_venda());
            pstmt.execute();
        } catch(SQLException | IOException e){
            Mensagem.mensagem(""+e);
            e.printStackTrace();
        }finally {
            ConexaoBD.fechaConexao(conn, pstmt);
        }
        
    }
    
     private static String colunas(){
        return  ModeloDadosFP.FP_AUTORIZACAO
                + " , " +   ModeloDadosFP.CLI_CPF
                + " , " +   ModeloDadosFP.FP_CUPOM
                + " , " +   ModeloDadosFP.FP_DATA_VENDA
                + " , " +   ModeloDadosFP.FP_DATA_PROX_VENDA
                ;
    }
        
    private static String selectDadosFP(){
        return "SELECT "
                + colunas()
                + " FROM "
                + ModeloDadosFP.T_DADOSFP
                + " WHERE "
                + ModeloDadosFP.FP_AUTORIZACAO
                + " = ?";
    }
    
    public static ArrayList<ModeloDadosFP> selectDadosFP(ModeloDadosFP dfp){
        ArrayList<ModeloDadosFP> lista = null;
        try {
            conn = ConexaoBD.autoReConectarXml();
            pstmt = conn.prepareStatement(selectDadosFP());
            pstmt.setLong(1, dfp.getFpAutorizacao());
            lista = listaDadosFP(pstmt.executeQuery());
        } catch (SQLException | IOException e) {
            Mensagem.mensagem(""+e);
        } finally {
            ConexaoBD.fechaConexao(conn, pstmt);
        }
        return lista;
    }
    
    private static ArrayList<ModeloDadosFP> listaDadosFP(ResultSet rs) throws SQLException{
        ArrayList<ModeloDadosFP> lista = new ArrayList<ModeloDadosFP>();
        ModeloDadosFP dfp;
        while(rs.next()){
            dfp = new ModeloDadosFP();
            dfp.setFpAutorizacao(rs.getLong(ModeloDadosFP.FP_AUTORIZACAO));
            dfp.setCliCpf(rs.getString(ModeloDadosFP.CLI_CPF));
            dfp.setFpCupom(rs.getInt(ModeloDadosFP.FP_CUPOM));
            dfp.setFpData_venda(rs.getDate(ModeloDadosFP.FP_DATA_VENDA));
            dfp.setFpData_prox_venda(rs.getDate(ModeloDadosFP.FP_DATA_PROX_VENDA));
            
            lista.add(dfp);
        }
        return lista;
    }
    
    private static String updateDadosFP(){
        return "UPDATE " +
                ModeloDadosFP.T_DADOSFP +
                " SET " +
                ModeloDadosFP.CLI_CPF           + " = ? , " +
                ModeloDadosFP.FP_CUPOM          + " = ? , " +
                ModeloDadosFP.FP_DATA_VENDA     + " = ? , " +
                ModeloDadosFP.FP_DATA_PROX_VENDA+ " = ?   " +
                " WHERE " +
                ModeloDadosFP.FP_AUTORIZACAO    + " = ? ";
    }
    
    public static boolean updateDadosFP(ModeloDadosFP dfp){
        try {
            conn = ConexaoBD.autoReConectarXml();
            pstmt = conn.prepareStatement(updateDadosFP());
            pstmt.setString( 1, dfp.getCliCpf());
            pstmt.setInt   ( 2, dfp.getFpCupom());
            pstmt.setDate  ( 3, dfp.getFpData_venda());
            pstmt.setDate  ( 4, dfp.getFpData_prox_venda());
            pstmt.setLong  ( 5, dfp.getFpAutorizacao());
            
            pstmt.execute();
            return true;
        } catch (SQLException | IOException e) {
            Mensagem.mensagem(""+e);
        }finally {
            ConexaoBD.fechaConexao(conn, pstmt);
        }
        return false;
    }
    
   
}
