package br.com.pc.natusfarma.bd;

import br.com.pc.natusfarma.classe.ModeloClientes;
import br.com.pc.util.Mensagem;
import br.com.pc.util.bd.ConexaoBD;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Paulo César
 */
public class ClientesSql {
    
    private static PreparedStatement pstmt;
    private static Connection conn;
    
    /*public ClientesComando(Properties properties) {
        if (ConexaoBD.getConnection() == null) {
            ConexaoBD.conectar(properties);
        }
    }*/
    
    private static String insertClientes(){
        return "INSERT INTO "
                + ModeloClientes.T_CLIENTES
                + " ( "
                +           ModeloClientes.CLI_NOME
                + " , " +   ModeloClientes.CLI_CPF
                + " , " +   ModeloClientes.CLI_ENDERECO
                + " , " +   ModeloClientes.CLI_NUMERO
                + " , " +   ModeloClientes.CLI_BAIRRO
                + " , " +   ModeloClientes.CLI_COMPLEMENTO
                + " , " +   ModeloClientes.CLI_CEP
                + " , " +   ModeloClientes.CLI_CIDADE
                + " , " +   ModeloClientes.CLI_ESTADO
                + " , " +   ModeloClientes.CLI_TELEFONE1
                + " , " +   ModeloClientes.CLI_TELEFONE2
                + " , " +   ModeloClientes.CLI_CODIGOSISTEMA
                + " ) "
                + " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
    }
    
    
    public static void insertClientes(ModeloClientes ccm) {
        try{
            conn = ConexaoBD.autoReConectarXml();
            pstmt = conn.prepareStatement(insertClientes());
            pstmt.setString( 1, ccm.getCliNome());
            pstmt.setString( 2, ccm.getCliCpf());
            pstmt.setString( 3, ccm.getCliEndereco());
            pstmt.setInt   ( 4, ccm.getCliNumero());
            pstmt.setString( 5, ccm.getCliBairro());
            pstmt.setString( 6, ccm.getCliComplemento());
            pstmt.setString( 7, ccm.getCliCep());
            pstmt.setString( 8, ccm.getCliCidade());
            pstmt.setString( 9, ccm.getCliEstado());
            pstmt.setString(10, ccm.getCliTelefone1());
            pstmt.setString(11, ccm.getCliTelefone2());
            pstmt.setInt   (12, ccm.getCliCodigosistema());         
            pstmt.execute();
        } catch(SQLException | IOException e){
            Mensagem.mensagem(""+e);
        }finally {
            ConexaoBD.fechaConexao(conn, pstmt);
        }
    }
    
    private static String colunas(){
        return  ModeloClientes.CLI_NOME
                + " , " +   ModeloClientes.CLI_ID
                + " , " +   ModeloClientes.CLI_CPF
                + " , " +   ModeloClientes.CLI_ENDERECO
                + " , " +   ModeloClientes.CLI_NUMERO
                + " , " +   ModeloClientes.CLI_BAIRRO
                + " , " +   ModeloClientes.CLI_COMPLEMENTO
                + " , " +   ModeloClientes.CLI_CEP
                + " , " +   ModeloClientes.CLI_CIDADE
                + " , " +   ModeloClientes.CLI_ESTADO
                + " , " +   ModeloClientes.CLI_TELEFONE1
                + " , " +   ModeloClientes.CLI_TELEFONE2
                + " , " +   ModeloClientes.CLI_CODIGOSISTEMA;
    }

    private static String selectClientesCPF(){
        return "SELECT "
                + colunas()
                + " FROM "
                + ModeloClientes.T_CLIENTES
                + " WHERE "
                + ModeloClientes.CLI_CPF
                + " = "
                + " ? ";
    }
    
    /*public static List<ModeloClientes> selectClientesCPF(ModeloClientes ccm){
        List<ModeloClientes> lista = null;
        try {
            conn = ConexaoBD.autoReConectarXml();
            pstmt = conn.prepareStatement(selectClientesCPF());
            pstmt.setString(1, ccm.getCliCpf());
            lista = listaClientes(pstmt.executeQuery());
        } catch (SQLException |IOException e) {
            Mensagem.mensagem(""+e);
        } finally {
            ConexaoBD.fechaConexao(conn, pstmt);
        }
        return lista;
    }*/
    
    public static List<ModeloClientes> selectClientesCPFId(ModeloClientes ccm, String field){
        List<ModeloClientes> lista = null;
        try {
            conn = ConexaoBD.autoReConectarXml();
            if (field == null) {
                field = "";
            }
            switch (field) {
                case ModeloClientes.CLI_CPF:
                    pstmt = conn.prepareStatement(selectClientesCPF());
                    pstmt.setString(1, ccm.getCliCpf()); 
                    break;
                default:
                    pstmt = conn.prepareStatement(
                            "SELECT "+ colunas()+ " FROM "+ ModeloClientes.T_CLIENTES+ " WHERE "+ ModeloClientes.CLI_ID+ " =  ? "
                    );
                    pstmt.setInt(1, ccm.getCliId()); 
                    break;
            }
            lista = listaClientes(pstmt.executeQuery());
        } catch (SQLException |IOException e) {
            Mensagem.mensagem(""+e);
        } finally {
            ConexaoBD.fechaConexao(conn, pstmt);
        }
        return lista;
    }
    
    private static List<ModeloClientes> listaClientes(ResultSet rs) throws SQLException{
        List<ModeloClientes> lista = new ArrayList<>();
        ModeloClientes ccm;
        while(rs.next()){
            ccm = new ModeloClientes();
            ccm.setCliId(rs.getInt(ModeloClientes.CLI_ID));
            ccm.setCliNome(rs.getString(ModeloClientes.CLI_NOME));
            ccm.setCliCpf(rs.getString(ModeloClientes.CLI_CPF));
            ccm.setCliEndereco(rs.getString(ModeloClientes.CLI_ENDERECO));
            ccm.setCliNumero(rs.getInt(ModeloClientes.CLI_NUMERO));
            ccm.setCliBairro(rs.getString(ModeloClientes.CLI_BAIRRO));
            ccm.setCliComplemento(rs.getString(ModeloClientes.CLI_COMPLEMENTO));
            ccm.setCliCep(rs.getString(ModeloClientes.CLI_CEP));
            ccm.setCliCidade(rs.getString(ModeloClientes.CLI_CIDADE));
            ccm.setCliEstado(rs.getString(ModeloClientes.CLI_ESTADO));
            ccm.setCliTelefone1(rs.getString(ModeloClientes.CLI_TELEFONE1));
            ccm.setCliTelefone2(rs.getString(ModeloClientes.CLI_TELEFONE2));
            ccm.setCliCodigosistema(rs.getInt(ModeloClientes.CLI_CODIGOSISTEMA));

            lista.add(ccm);
        }
        return lista;
    }
    
    private static String updateClientes(){
        return "UPDATE "
                + ModeloClientes.T_CLIENTES +
                " SET " +
                ModeloClientes.CLI_NOME          + " = ? , " +
                //CadastroClientesModelo.CLI_CPF           + " = ? , " +
                ModeloClientes.CLI_ENDERECO      + " = ? , " +
                ModeloClientes.CLI_NUMERO        + " = ? , " +
                ModeloClientes.CLI_BAIRRO        + " = ? , " +
                ModeloClientes.CLI_COMPLEMENTO   + " = ? , " +
                ModeloClientes.CLI_CEP           + " = ? , " +
                ModeloClientes.CLI_CIDADE        + " = ? , " +
                ModeloClientes.CLI_ESTADO        + " = ? , " +
                ModeloClientes.CLI_TELEFONE1     + " = ? , " +
                ModeloClientes.CLI_TELEFONE2     + " = ? , " +
                ModeloClientes.CLI_CODIGOSISTEMA + " = ?   " +
                " WHERE " +
                ModeloClientes.CLI_CPF           + " = ? ";
    }
    
    public static void updateClientes(ModeloClientes ccm){
        try {
            conn = ConexaoBD.autoReConectarXml();
            pstmt = conn.prepareStatement(updateClientes());
            pstmt.setString( 1, ccm.getCliNome());
            pstmt.setString( 2, ccm.getCliEndereco());
            pstmt.setInt   ( 3, ccm.getCliNumero());
            pstmt.setString( 4, ccm.getCliBairro());
            pstmt.setString( 5, ccm.getCliComplemento());
            pstmt.setString( 6, ccm.getCliCep());
            pstmt.setString( 7, ccm.getCliCidade());
            pstmt.setString( 8, ccm.getCliEstado());
            pstmt.setString( 9, ccm.getCliTelefone1());
            pstmt.setString(10, ccm.getCliTelefone2());
            pstmt.setInt   (11, ccm.getCliCodigosistema()); 
            pstmt.setString(12, ccm.getCliCpf());
            pstmt.execute();
        } catch (SQLException | IOException e) {
            Mensagem.mensagem(""+e);
        }finally {
            ConexaoBD.fechaConexao(conn, pstmt);
        }
    }
    
    public static int totalClientes(){
        try {
            conn = ConexaoBD.autoReConectarXml();
            pstmt = conn.prepareStatement("SELECT COUNT(*) FROM "+ ModeloClientes.T_CLIENTES);
            ResultSet rs = pstmt.executeQuery();
            if (rs.first()) {
                return rs.getInt(1);
            }
        } catch (SQLException | IOException e) {
            Mensagem.mensagem(""+e);
        }finally {
            ConexaoBD.fechaConexao(conn, pstmt);
        }
        return 0;
    }
    
    
}
