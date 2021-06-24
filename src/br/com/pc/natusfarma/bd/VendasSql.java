package br.com.pc.natusfarma.bd;

import br.com.pc.natusfarma.classe.ModeloProduto;
import br.com.pc.natusfarma.classe.ModeloVendas;
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
public class VendasSql {
    
    private static PreparedStatement pstmt;
    private static Connection conn;
    
    /*public VendasComando(Properties properties) {
        if (ConexaoBD.getConnection() == null) {
            ConexaoBD.conectar(properties);
        }
    }*/
    
    private static String insertVendas(){
        return "INSERT INTO "
                + ModeloVendas.T_VENDAS
                + " ( "
                +           ModeloVendas.VEN_ID
                + " , " +   ModeloVendas.VEN_CUPOM
                + " , " +   ModeloVendas.VEN_CAIXA
                + " , " +   ModeloVendas.VEN_DATA_VENDA
                + " , " +   ModeloVendas.VEN_QUANTIDADE
                + " , " +   ModeloVendas.VEN_AUTORIZACAO
                + " , " +   ModeloVendas.VEN_CPF
                + " , " +   ModeloVendas.VEN_NOME
                + " , " +   ModeloVendas.PRO_ID
                + " ) "
                + " VALUES ( ?, ?, ?, ?, ?, ?, ?, ? ,?)";
    }
    
    
    public static void insertVendas(ModeloVendas mv) {
        try{
            conn = ConexaoBD.autoReConectarXml();
            pstmt = conn.prepareStatement(insertVendas());
            pstmt.setInt   (1, mv.getVenId());
            pstmt.setInt   (2, mv.getVenCupom());
            pstmt.setInt   (3, mv.getVenCaixa());
            pstmt.setDate  (4, mv.getVenDataVenda());
            pstmt.setInt   (5, mv.getVenQuantidade());
            pstmt.setLong  (6, mv.getVenAutorizacao());        
            pstmt.setString(7, mv.getVenCpf());
            pstmt.setString(8, mv.getVenNome());
            pstmt.setInt   (9, mv.getProId());  
            pstmt.execute();
        } catch(SQLException | IOException e){
            Mensagem.mensagem(""+e);
        }finally {
            ConexaoBD.fechaConexao(conn, pstmt);
        }
    }
    
    private static String colunas(){
        return  ModeloVendas.VEN_ID
                + " , " +   ModeloVendas.VEN_CUPOM
                + " , " +   ModeloVendas.VEN_CAIXA
                + " , " +   ModeloVendas.VEN_DATA_VENDA
                + " , " +   ModeloVendas.VEN_QUANTIDADE
                + " , " +   ModeloVendas.VEN_AUTORIZACAO
                + " , " +   ModeloVendas.VEN_CPF
                + " , " +   ModeloVendas.VEN_NOME
                + ",V." +   ModeloVendas.PRO_ID
                + " , " +   ModeloVendas.PRO_DESCRICAO
                + " , " +   ModeloVendas.PRO_BARRAS
                + " , " +   ModeloVendas.PRO_DIAS
                + " , " +   ModeloVendas.PRO_ATIVO
                ;
                
    }
    
     private static String selectConsulta(){
        return "SELECT "
                + colunas()
                + " FROM "
                + ModeloVendas.T_VENDAS
                + " V LEFT JOIN "
                + ModeloProduto.T_PRODUTO
                + " P ON "
                + "V." + ModeloVendas.PRO_ID
                + " = " 
                + "P." + ModeloProduto.PRO_ID;
                
    }
    
    private static String selectConsulta(String field){
        return selectConsulta()
                + " WHERE "
                + "P." +ModeloProduto.PRO_ATIVO
                + " <> 'N' AND "
                + field + " = ?";
    }
        
    public static ArrayList<ModeloVendas> selectVendasAutCupomCodigo(ModeloVendas mv, String field){
        ArrayList<ModeloVendas> lista = null;
        try {
            conn = ConexaoBD.autoReConectarXml();
            if (field == null) {
                field = "";
            }
            switch (field) {
                case ModeloVendas.VEN_AUTORIZACAO:
                    pstmt = conn.prepareStatement(selectConsulta(field));
                    pstmt.setLong(1, mv.getVenAutorizacao()); 
                    break;
                case ModeloVendas.VEN_DATA_VENDA:
                    pstmt = conn.prepareStatement(selectConsulta()
                            + " WHERE "
                            + ModeloVendas.VEN_DATA_VENDA
                            + " BETWEEN ? AND ?"
                    );
                    pstmt.setDate(1, mv.getVenDataVenda()); 
                    pstmt.setDate(2, mv.getVenDataVendaF()); 
                    break;
                case ModeloVendas.VEN_DATA_VENDA+ModeloVendas.VEN_CPF:
                    pstmt = conn.prepareStatement(selectConsulta()
                            + " WHERE "
                            + ModeloVendas.VEN_DATA_VENDA
                            + " BETWEEN ? AND ? AND "
                            + ModeloVendas.VEN_CPF
                            + " = ?"
                    );
                    pstmt.setDate(1, mv.getVenDataVenda()); 
                    pstmt.setDate(2, mv.getVenDataVendaF()); 
                    pstmt.setString(3, mv.getVenCpf());
                    break;
                case ModeloVendas.VEN_CPF:
                    pstmt = conn.prepareStatement(selectConsulta()
                            + " WHERE "
                            + ModeloVendas.VEN_CPF
                            + " = ?"
                    );
                    pstmt.setString(1, mv.getVenCpf());
                    break;
                default:
                    pstmt = conn.prepareStatement(selectConsulta()
                            + " WHERE "
                            + ModeloVendas.VEN_AUTORIZACAO 
                            + " = ? AND "
                            + ModeloVendas.VEN_CUPOM
                            + " = ? AND "
                            + "V."+ModeloVendas.PRO_ID
                            + " = ?"
                    );
                    pstmt.setLong   (1, mv.getVenAutorizacao());
                    pstmt.setInt    (2, mv.getVenCupom());
                    pstmt.setInt    (3, mv.getProId());
                    break;
            }
            lista = listaVendas(pstmt.executeQuery());
        } catch (SQLException |IOException e) {
            Mensagem.mensagem(""+e);
        } finally {
            ConexaoBD.fechaConexao(conn, pstmt);
        }
        return lista;
    }

    private static ArrayList<ModeloVendas> listaVendas(ResultSet rs) throws SQLException{
        ArrayList<ModeloVendas> lista = new ArrayList<ModeloVendas>();
        ModeloVendas mv;
        while(rs.next()){
            mv = new ModeloVendas();
            mv.setVenId(rs.getInt(ModeloVendas.VEN_ID));
            mv.setVenCupom(rs.getInt(ModeloVendas.VEN_CUPOM));
            mv.setVenCaixa(rs.getInt(ModeloVendas.VEN_CAIXA));
            mv.setVenDataVenda(rs.getDate(ModeloVendas.VEN_DATA_VENDA));
            mv.setVenQuantidade(rs.getInt(ModeloVendas.VEN_QUANTIDADE));
            mv.setVenAutorizacao(rs.getLong(ModeloVendas.VEN_AUTORIZACAO));
            mv.setVenCpf(rs.getString(ModeloVendas.VEN_CPF));
            mv.setVenNome(rs.getString(ModeloVendas.VEN_NOME));
            mv.setProId(rs.getInt(ModeloVendas.PRO_ID));
            mv.setProDescricao(rs.getString(ModeloVendas.PRO_DESCRICAO));
            mv.setProBarras(rs.getString(ModeloVendas.PRO_BARRAS));
            mv.setProDias(rs.getInt(ModeloVendas.PRO_DIAS));
            mv.setProAtivo(rs.getString(ModeloVendas.PRO_ATIVO));
            
            lista.add(mv);
        }
        return lista;
    }
    
    private static String updateVendas(){
        return "UPDATE "
                + ModeloVendas.T_VENDAS +
                " SET " +
                ModeloVendas.VEN_CAIXA         + " = ? , " +
                ModeloVendas.VEN_DATA_VENDA    + " = ? , " +
                ModeloVendas.VEN_QUANTIDADE    + " = ? , " +
                //ModeloVendas.VEN_AUTORIZACAO   + " = ? , " +
                ModeloVendas.VEN_CUPOM         + " = ? , " +
                ModeloVendas.VEN_CPF           + " = ? , " +
                ModeloVendas.VEN_NOME          + " = ?   " +
                //ModeloVendas.PRO_ID            + " = ?   " +
                " WHERE " +
                ModeloVendas.VEN_AUTORIZACAO   + " = ? AND " +
                ModeloVendas.PRO_ID            + " = ?     ";
                
    }
    
    public static void updateVendas(ModeloVendas mv){
        try {
            conn = ConexaoBD.autoReConectarXml();
            pstmt = conn.prepareStatement(updateVendas());
            pstmt.setInt   (1, mv.getVenCaixa());
            pstmt.setDate  (2, mv.getVenDataVenda());
            pstmt.setInt   (3, mv.getVenQuantidade());
//            pstmt.setLong  (4, mv.getVenAutorizacao());
            pstmt.setInt   (4, mv.getVenCupom());
            pstmt.setString(5, mv.getVenCpf());
            pstmt.setString(6, mv.getVenNome());
//            pstmt.setInt   (8, mv.getProId());
            pstmt.setLong  (7, mv.getVenAutorizacao());
            pstmt.setInt   (8,mv.getProId());
            pstmt.execute();
        } catch (SQLException | IOException e) {
            Mensagem.mensagem(""+e);
            e.printStackTrace();
        }finally {
            ConexaoBD.fechaConexao(conn, pstmt);
        }
    }
    
    
}
