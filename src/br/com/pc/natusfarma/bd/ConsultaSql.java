package br.com.pc.natusfarma.bd;

import br.com.pc.natusfarma.classe.ModeloClientes;
import br.com.pc.natusfarma.classe.ModeloDadosFP;
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
public class ConsultaSql {
    private static PreparedStatement pstmt;
    private static Connection conn;
    
    public static String colunas(){
        return  ModeloClientes.CLI_NOME
                + " ,C." +   ModeloClientes.CLI_CPF
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
                + " , " +   ModeloDadosFP.FP_AUTORIZACAO
                //+ " , " +   DadosFPModelo.CLI_CPF
                + " , " +   ModeloDadosFP.FP_CUPOM
                + " , " +   ModeloDadosFP.FP_DATA_VENDA
                + " , " +   ModeloDadosFP.FP_DATA_PROX_VENDA;
    }

    private static String selectConsulta(){
        return "SELECT "
                + colunas()
                + " FROM "
                + ModeloClientes.T_CLIENTES 
                + " C LEFT JOIN "
                + ModeloDadosFP.T_DADOSFP 
                + " D ON "
                + "C." + ModeloClientes.CLI_CPF 
                + " = " 
                + "D." + ModeloDadosFP.CLI_CPF;
                
    }
    
    private static String selectConsulta(String field){
        return selectConsulta()
                + " WHERE "+ field+ " between ? and ?";
    }
    
    public static List<ModeloDadosFP> selectConsulta(ModeloDadosFP dfp, String field){
        List<ModeloDadosFP> lista = null;
        try {
            conn = ConexaoBD.autoReConectarXml();
            if (field == null) {
                field = "";
            }
            String orderby = " order by "+ModeloDadosFP.FP_DATA_PROX_VENDA;
            switch (field) {
                case ModeloDadosFP.FP_DATA_PROX_VENDA:
                    pstmt = conn.prepareStatement(selectConsulta(field) +orderby);
                    pstmt.setDate(1, dfp.getFpData_prox_venda());
                    pstmt.setDate(2, dfp.getFpData_prox_vendaF());
                    break;
                case ModeloDadosFP.FP_DATA_VENDA:
                    pstmt = conn.prepareStatement(selectConsulta(field)+orderby);
                    pstmt.setDate(1, dfp.getFpData_venda());
                    pstmt.setDate(2, dfp.getFpData_vendaF());
                    break;
                case ModeloDadosFP.FP_DATA_PROX_VENDA+ModeloDadosFP.CLI_CPF:
                    pstmt = conn.prepareStatement(selectConsulta()+ " WHERE "+ModeloDadosFP.FP_DATA_PROX_VENDA+ " between ? and ? AND C."+ModeloClientes.CLI_CPF +" = ?" +orderby);
                    pstmt.setDate(1, dfp.getFpData_prox_venda());
                    pstmt.setDate(2, dfp.getFpData_prox_vendaF());
                    pstmt.setString(3, dfp.getCliCpf());
                    break;
                case ModeloDadosFP.FP_DATA_VENDA+ModeloDadosFP.CLI_CPF:
                    pstmt = conn.prepareStatement(selectConsulta()+ " WHERE "+ModeloDadosFP.FP_DATA_VENDA+ " between ? and ? AND C."+ModeloClientes.CLI_CPF +" = ?"+orderby);
                    pstmt.setDate(1, dfp.getFpData_venda());
                    pstmt.setDate(2, dfp.getFpData_vendaF());
                    pstmt.setString(3, dfp.getCliCpf());
                    break;
                case ModeloDadosFP.CLI_CPF:
                    pstmt = conn.prepareStatement(selectConsulta() +" WHERE C."+ field+ " = ?"+orderby);
                    pstmt.setString(1, dfp.getCliCpf());
                    break;
                default:
                    pstmt = conn.prepareStatement(selectConsulta()+orderby);
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
    
    private static List<ModeloDadosFP> listaClientes(ResultSet rs) throws SQLException{
        List<ModeloDadosFP> lista = new ArrayList<ModeloDadosFP>();
        ModeloDadosFP dfp;
        while(rs.next()){
            dfp = new ModeloDadosFP();
            dfp.setCliNome(rs.getString(ModeloClientes.CLI_NOME));
            dfp.setCliCpf(rs.getString(ModeloClientes.CLI_CPF));
            dfp.setCliEndereco(rs.getString(ModeloClientes.CLI_ENDERECO));
            dfp.setCliNumero(rs.getInt(ModeloClientes.CLI_NUMERO));
            dfp.setCliBairro(rs.getString(ModeloClientes.CLI_BAIRRO));
            dfp.setCliComplemento(rs.getString(ModeloClientes.CLI_COMPLEMENTO));
            dfp.setCliCep(rs.getString(ModeloClientes.CLI_CEP));
            dfp.setCliCidade(rs.getString(ModeloClientes.CLI_CIDADE));
            dfp.setCliEstado(rs.getString(ModeloClientes.CLI_ESTADO));
            dfp.setCliTelefone1(rs.getString(ModeloClientes.CLI_TELEFONE1));
            dfp.setCliTelefone2(rs.getString(ModeloClientes.CLI_TELEFONE2));
            dfp.setCliCodigosistema(rs.getInt(ModeloClientes.CLI_CODIGOSISTEMA));
            dfp.setFpAutorizacao(rs.getLong(ModeloDadosFP.FP_AUTORIZACAO));
            //dfp.setCliCpf(rs.getString(DadosFPModelo.CLI_CPF));
            dfp.setFpCupom(rs.getInt(ModeloDadosFP.FP_CUPOM));
            dfp.setFpData_venda(rs.getDate(ModeloDadosFP.FP_DATA_VENDA));
            dfp.setFpData_prox_venda(rs.getDate(ModeloDadosFP.FP_DATA_PROX_VENDA));
            lista.add(dfp);
        }
        return lista;
    }

    
}
