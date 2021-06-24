package br.com.pc.natusfarma.bd;

import br.com.pc.natusfarma.classe.ModeloClientes;
import br.com.pc.natusfarma.classe.ModeloConsultaMovimentacoes;
import br.com.pc.natusfarma.classe.ModeloDadosFP;
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
import java.util.List;

/**
 *
 * @author Paulo César
 */
public class ConsultaMovimentacoesSql {
    
    private static PreparedStatement pstmt;
    private static Connection conn;
    
    
    
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
                
                + " , " +   ModeloDadosFP.FP_AUTORIZACAO
                + ",D." +   ModeloDadosFP.CLI_CPF
                + " , " +   ModeloDadosFP.FP_CUPOM
                + " , " +   ModeloDadosFP.FP_DATA_VENDA
                + " , " +   ModeloDadosFP.FP_DATA_PROX_VENDA
                
                + " , " +   ModeloClientes.CLI_NOME
                + ",C." +   ModeloClientes.CLI_CPF
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
                
                ;
                
    }
    
    //SELECT * FROM VENDAS V LEFT JOIN DADOSFP D ON V.VEN_AUTORIZACAO = D.FP_AUTORIZACAO LEFT JOIN CLIENTES C ON V.VEN_CPF = C.CLI_CPF WHERE VEN_DATA_VENDA BETWEEN '2017-12-27' AND '2017-12-27' GROUP BY VEN_AUTORIZACAO
    
    /*
    *   SELECT * FROM VENDAS V LEFT JOIN PRODUTO P ON V.PRO_ID = P.PRO_ID
    *   LEFT JOIN DADOSFP D ON V.VEN_AUTORIZACAO = D.FP_AUTORIZACAO 
    *   LEFT JOIN CLIENTES C ON V.VEN_CPF = C.CLI_CPF 
    *   WHERE VEN_DATA_VENDA BETWEEN '2017-11-01' AND '2017-11-10' GROUP BY VEN_AUTORIZACAO
    */
     private static String selectConsulta(){
        return "SELECT "
                + colunas()
                + " FROM "
                + ModeloVendas.T_VENDAS
                + " V LEFT JOIN "
                + ModeloProduto.T_PRODUTO
                + " P ON "
                + "V." + ModeloVendas.PRO_ID
                + " = P." + ModeloProduto.PRO_ID
                + " LEFT JOIN "
                + ModeloDadosFP.T_DADOSFP
                + " D ON "
                + "V." + ModeloVendas.VEN_AUTORIZACAO
                + " = " 
                + "D." + ModeloDadosFP.FP_AUTORIZACAO
                + " LEFT JOIN "
                + ModeloClientes.T_CLIENTES
                + " C ON " 
                + "V."+ModeloVendas.VEN_CPF 
                + " = C."+ ModeloClientes.CLI_CPF;
                
    }
    
    /*private static String selectConsulta(String field){
        return selectConsulta()
                + " WHERE "
                + "V." +ModeloVendas.VEN_DATA_VENDA
                + " BETWEEN ? AND ? ";
    }*/
        
    public static List<ModeloConsultaMovimentacoes> selectConsultaMovimentacoes(ModeloVendas mv, String field){
        List<ModeloConsultaMovimentacoes> lista = null;
        try {
            conn = ConexaoBD.autoReConectarXml();
            if (field == null) {
                field = "";
            }
            String ordem = " ORDER BY "+ModeloVendas.VEN_DATA_VENDA;
            switch (field) {
                case ModeloVendas.VEN_DATA_VENDA:
                    pstmt = conn.prepareStatement(selectConsulta()
                            + " WHERE "
                            + ModeloVendas.VEN_DATA_VENDA
                            + " BETWEEN ? AND ? GROUP BY VEN_AUTORIZACAO "+ordem
                    );
                    pstmt.setDate(1, mv.getVenDataVenda()); 
                    pstmt.setDate(2, mv.getVenDataVendaF()); 
                    break;
                case ModeloVendas.VEN_DATA_VENDA+"NULL":
                    pstmt = conn.prepareStatement(selectConsulta()
                            + " WHERE "
                            + ModeloVendas.VEN_DATA_VENDA
                            + " BETWEEN ? AND ? AND "
                            + ModeloDadosFP.FP_AUTORIZACAO
                            + " IS NULL GROUP BY VEN_AUTORIZACAO "+ordem
                    );
                    pstmt.setDate(1, mv.getVenDataVenda()); 
                    pstmt.setDate(2, mv.getVenDataVendaF()); 
                    break;
                default:
                    pstmt = conn.prepareStatement(selectConsulta() + " GROUP BY VEN_AUTORIZACAO "+ordem);
                    break;
            }
            lista = listaConsultaMovimentacoes(pstmt.executeQuery());
        } catch (SQLException | IOException e) {
            Mensagem.mensagem(""+e);
        } finally {
            ConexaoBD.fechaConexao(conn, pstmt);
        }
        return lista;
    }

    private static List<ModeloConsultaMovimentacoes> listaConsultaMovimentacoes(ResultSet rs) throws SQLException{
        List<ModeloConsultaMovimentacoes> lista = new ArrayList<>();
        ModeloVendas mv;
        ModeloClientes ccm;
        ModeloDadosFP dfp;
        ModeloConsultaMovimentacoes mcm;
        while(rs.next()){
            mcm = new ModeloConsultaMovimentacoes();
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
            
            ccm = new ModeloClientes();
            ccm.setCliNome(rs.getString(ModeloClientes.CLI_NOME));
            ccm.setCliCpf(rs.getString("C."+ModeloClientes.CLI_CPF));
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
            
            dfp = new ModeloDadosFP();
            dfp.setFpAutorizacao(rs.getLong(ModeloDadosFP.FP_AUTORIZACAO));
            dfp.setCliCpf(rs.getString("D."+ModeloDadosFP.CLI_CPF));
            dfp.setFpCupom(rs.getInt(ModeloDadosFP.FP_CUPOM));
            dfp.setFpData_venda(rs.getDate(ModeloDadosFP.FP_DATA_VENDA));
            dfp.setFpData_prox_venda(rs.getDate(ModeloDadosFP.FP_DATA_PROX_VENDA));
            
            mcm.setVendas(mv);
            mcm.setClientes(ccm);
            mcm.setDadosFp(dfp);
            
            lista.add(mcm);
        }
        return lista;
    }
    
    
}
