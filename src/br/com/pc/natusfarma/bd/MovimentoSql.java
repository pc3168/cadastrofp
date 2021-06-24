package br.com.pc.natusfarma.bd;

import br.com.pc.natusfarma.classe.ModeloMovimento;
import br.com.pc.natusfarma.classe.ModeloProduto;
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
public class MovimentoSql {
    
    private static PreparedStatement pstmt;
    private static Connection conn;
    
   
    private static String insertMovimento(){
        return "INSERT INTO "
                + ModeloMovimento.T_MOVIMENTO
                + " ( "
                +           ModeloMovimento.MOV_ID
                + " , " +   ModeloMovimento.FP_AUTORIZACAO
                + " , " +   ModeloMovimento.PRO_ID
                + " , " +   ModeloMovimento.MOV_ECF
                + " , " +   ModeloMovimento.MOV_QUANTIDADE
                + " ) "
                + " VALUES ( ?, ?, ?, ?, ?)";
    }
    
    
    public static void insertMovimento(ModeloMovimento mm) {
        try{
            conn = ConexaoBD.autoReConectarXml();
            pstmt = conn.prepareStatement(insertMovimento());
            pstmt.setInt   (1, mm.getMovId());
            pstmt.setLong  (2, mm.getFpAutorizacao());
            pstmt.setInt   (3, mm.getProId());
            pstmt.setInt   (4, mm.getMovEcf());
            pstmt.setInt   (5, mm.getMovQuantidade());
            pstmt.execute();
        } catch(SQLException | IOException e){
            Mensagem.mensagem(""+e);
        }finally {
            ConexaoBD.fechaConexao(conn, pstmt);
        }
    }
    
    private static String colunas(){
        return  ModeloMovimento.MOV_ID
                + " , " +   ModeloMovimento.FP_AUTORIZACAO
                + ",M." +   ModeloMovimento.PRO_ID
                + " , " +   ModeloMovimento.MOV_ECF
                + " , " +   ModeloMovimento.MOV_QUANTIDADE
                + " , " +   ModeloMovimento.PRO_DESCRICAO
                + " , " +   ModeloMovimento.PRO_BARRAS
                + " , " +   ModeloMovimento.PRO_DIAS
                + " , " +   ModeloMovimento.PRO_ATIVO
                ;
                
    }
    
    private static String selectMovimentoAutCodigo(){
        return "SELECT "
                + colunas()
                + " FROM "
                + ModeloMovimento.T_MOVIMENTO
                + " M INNER JOIN "
                + ModeloProduto.T_PRODUTO
                + " P ON "
                + "M." + ModeloMovimento.PRO_ID
                + " = " 
                + "P." + ModeloProduto.PRO_ID
                + " AND " 
                + "P." +ModeloProduto.PRO_ATIVO
                + " <> 'N' ";
                
    }
    
    
    private static String selectMovimento(String field){
        return selectMovimentoAutCodigo()
                + " WHERE "+ field+ " = ?";
    }
    
    public static ArrayList<ModeloMovimento> selectMovimentoAutCodigo(ModeloMovimento mm, String field){
        ArrayList<ModeloMovimento> lista = null;
        try {
            conn = ConexaoBD.autoReConectarXml();
            if (field == null) {
                field = "";
            }
            switch (field) {
                case ModeloMovimento.FP_AUTORIZACAO:
                    pstmt = conn.prepareStatement(selectMovimento(field));
                    pstmt.setLong(1, mm.getFpAutorizacao()); 
                    break;
                default:
                    pstmt = conn.prepareStatement(selectMovimentoAutCodigo()
                            + " WHERE "
                            + ModeloMovimento.FP_AUTORIZACAO
                            + " = ? AND "
                            + "M."+ModeloMovimento.PRO_ID
                            + " = ?"
                    );
                    pstmt.setLong   (1, mm.getFpAutorizacao());
                    pstmt.setInt    (2, mm.getProId());
                    break;
            }
            lista = listaMovimento(pstmt.executeQuery());
        } catch (SQLException |IOException e) {
            Mensagem.mensagem(""+e);
        } finally {
            ConexaoBD.fechaConexao(conn, pstmt);
        }
        return lista;
    }

    
    private static ArrayList<ModeloMovimento> listaMovimento(ResultSet rs) throws SQLException{
        ArrayList<ModeloMovimento> lista = new ArrayList<ModeloMovimento>();
        ModeloMovimento mm;
        while(rs.next()){
            mm = new ModeloMovimento();
            mm.setMovId(rs.getInt(ModeloMovimento.MOV_ID));
            mm.setFpAutorizacao(rs.getLong(ModeloMovimento.FP_AUTORIZACAO));
            mm.setMovEcf(rs.getInt(ModeloMovimento.MOV_ECF));
            mm.setMovQuantidade(rs.getInt(ModeloMovimento.MOV_QUANTIDADE));
            mm.setProId(rs.getInt(ModeloMovimento.PRO_ID));
            mm.setProDescricao(rs.getString(ModeloMovimento.PRO_DESCRICAO));
            mm.setProBarras(rs.getString(ModeloMovimento.PRO_BARRAS));
            mm.setProDias(rs.getInt(ModeloMovimento.PRO_DIAS));
            
            lista.add(mm);
        }
        return lista;
    }
    
    private static String updateMovimento(){
        return "UPDATE "
                + ModeloMovimento.T_MOVIMENTO +
                " SET " +
                ModeloMovimento.MOV_ECF         + " = ? , " +
                ModeloMovimento.MOV_QUANTIDADE  + " = ? , " +
                ModeloMovimento.FP_AUTORIZACAO  + " = ? , " +
                ModeloMovimento.PRO_ID          + " = ?   " +
                " WHERE " +
                ModeloMovimento.MOV_ID          + " = ? ";
                
    }
    
    public static void updateMovimento(ModeloMovimento mm){
        try {
            conn = ConexaoBD.autoReConectarXml();
            pstmt = conn.prepareStatement(updateMovimento());
            pstmt.setInt   (1, mm.getMovEcf());
            pstmt.setInt   (2, mm.getMovQuantidade());
            pstmt.setLong  (3, mm.getFpAutorizacao());
            pstmt.setInt   (4, mm.getProId());
            pstmt.setInt   (5, mm.getMovId());
            pstmt.execute();
        } catch (SQLException | IOException e) {
            Mensagem.mensagem(""+e);
            e.printStackTrace();
        }finally {
            ConexaoBD.fechaConexao(conn, pstmt);
        }
    }
    
    
}
