package br.com.pc.natusfarma.bd;

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
public class ProdutoSql {
    
    private static PreparedStatement pstmt;
    private static Connection conn;
    
    
    private static String insertProduto(){
        return "INSERT INTO "
                + ModeloProduto.T_PRODUTO
                + " ( "
                +           ModeloProduto.PRO_ID
                + " , " +   ModeloProduto.PRO_DESCRICAO
                + " , " +   ModeloProduto.PRO_BARRAS
                + " , " +   ModeloProduto.PRO_DIAS
                + " ) "
                + " VALUES ( ?, ?, ?, ? )";
    }
    
    
    public static void insertProduto(ModeloProduto mp) {
        try{
            conn = ConexaoBD.autoReConectarXml();
            pstmt = conn.prepareStatement(insertProduto());
            pstmt.setInt   ( 1, mp.getProId());
            pstmt.setString( 2, mp.getProDescricao());
            pstmt.setString( 3, mp.getProBarras());
            pstmt.setInt   ( 4, mp.getProDias());
            pstmt.execute();
        } catch(SQLException | IOException e){
            Mensagem.mensagem(""+e);
        }finally {
            ConexaoBD.fechaConexao(conn, pstmt);
        }
    }
    
    private static String colunas(){
        return  ModeloProduto.PRO_ID
                + " , " +   ModeloProduto.PRO_DESCRICAO
                + " , " +   ModeloProduto.PRO_BARRAS
                + " , " +   ModeloProduto.PRO_DIAS
                + " , " +   ModeloProduto.PRO_ATIVO;
    }
    
    private static String selectProduto(){
        return "SELECT "
                + colunas()
                + " FROM "
                + ModeloProduto.T_PRODUTO;
    }
    
    private static String selectProduto(String field){
        return selectProduto()
                    + " WHERE " +ModeloProduto.PRO_ATIVO + " = ? "
                    + " AND " + field+ " = ?";
    }
    
    public static ArrayList<ModeloProduto> selectProduto(ModeloProduto mp,String field){
        ArrayList<ModeloProduto> lista = null;
        try {
            conn = ConexaoBD.autoReConectarXml();
            if (field == null) {
                field = "";
            }
            switch (field) {
                case ModeloProduto.PRO_ATIVO_ID:
                    field = ModeloProduto.PRO_ID;
                    pstmt = conn.prepareStatement(selectProduto(field));
                    pstmt.setString(1, mp.getProAtivo());
                    pstmt.setInt   (2, mp.getProId());
                    break;
                case ModeloProduto.PRO_ID:
                    pstmt = conn.prepareStatement(selectProduto()+ " WHERE " +field + " = ? ");
                    pstmt.setInt   (1, mp.getProId());
                    break;
                case ModeloProduto.PRO_DESCRICAO:
                    pstmt = conn.prepareStatement(selectProduto()+ " WHERE " +ModeloProduto.PRO_ATIVO + " = ? AND "+ field+ " like ?");
                    pstmt.setString(1, mp.getProAtivo());
                    pstmt.setString(2, '%'+mp.getProDescricao()+'%');
                    break;
                case ModeloProduto.PRO_BARRAS:
                    pstmt = conn.prepareStatement(selectProduto(field));
                    pstmt.setString(1, mp.getProAtivo());
                    pstmt.setString(2, mp.getProBarras());
                    break;
                default:
                    pstmt = conn.prepareStatement(selectProduto()+ " WHERE " +ModeloProduto.PRO_ATIVO + " = ? ");
                    pstmt.setString(1, mp.getProAtivo());
                    break;
            }
            lista = listaProduto(pstmt.executeQuery());
        } catch (SQLException |IOException e) {
            Mensagem.mensagem(""+e);
        } finally {
            ConexaoBD.fechaConexao(conn, pstmt);
        }
        return lista;
    }

    
    private static ArrayList<ModeloProduto> listaProduto(ResultSet rs) throws SQLException{
        ArrayList<ModeloProduto> lista = new ArrayList<ModeloProduto>();
        ModeloProduto mp;
        while(rs.next()){
            mp = new ModeloProduto();
            mp.setProId(rs.getInt(ModeloProduto.PRO_ID));
            mp.setProDescricao(rs.getString(ModeloProduto.PRO_DESCRICAO));
            mp.setProBarras(rs.getString(ModeloProduto.PRO_BARRAS));
            mp.setProDias(rs.getInt(ModeloProduto.PRO_DIAS));
            mp.setProAtivo(rs.getString(ModeloProduto.PRO_ATIVO));
            
            lista.add(mp);
        }
        return lista;
    }
    
    private static String updateProduto(){
        return "UPDATE "
                + ModeloProduto.T_PRODUTO +
                " SET " +
                ModeloProduto.PRO_DESCRICAO     + " = ? , " +
                ModeloProduto.PRO_BARRAS        + " = ? , " +
                ModeloProduto.PRO_DIAS          + " = ? , " +
                ModeloProduto.PRO_ATIVO         + " = ?   " +
                " WHERE " +
                ModeloProduto.PRO_ID            + " = ? ";
    }
    
    public static void updateProduto(ModeloProduto mp){
        try {
            conn = ConexaoBD.autoReConectarXml();
            pstmt = conn.prepareStatement(updateProduto());
            pstmt.setString( 1, mp.getProDescricao());
            pstmt.setString( 2, mp.getProBarras());
            pstmt.setInt   ( 3, mp.getProDias());
            pstmt.setString( 4, mp.getProAtivo());
            pstmt.setInt   ( 5, mp.getProId());
            pstmt.execute();
        } catch (SQLException | IOException e) {
            Mensagem.mensagem(""+e);
        }finally {
            ConexaoBD.fechaConexao(conn, pstmt);
        }
    }
    
    
}
