
package br.com.pc.natusfarma.bd;

import br.com.pc.util.bd.ConexaoBD;
import java.sql.SQLException;

/**
 *
 * @author Paulo
 */
public class MontaDBSql {
    
    public MontaDBSql() {
        
    }
    
    public String tClientes(){
        return "create table CLIENTES\n" +
            "(\n" +
            "   CLI_ID               INTEGER IDENTITY not null ,\n" +
            "   CLI_NOME             varchar(50) not null,\n" +
            "   CLI_CPF              varchar(11) not null,\n" +
            "   CLI_ENDERECO         varchar(50) not null,\n" +
            "   CLI_NUMERO           numeric(5,0) not null,\n" +
            "   CLI_BAIRRO           varchar(50),\n" +
            "   CLI_COMPLEMENTO      varchar(50),\n" +
            "   CLI_CEP              varchar(9),\n" +
            "   CLI_CIDADE           varchar(50),\n" +
            "   CLI_ESTADO           varchar(2),\n" +
            "   CLI_TELEFONE1        varchar(19) not null,\n" +
            "   CLI_TELEFONE2        varchar(19),\n" +
            "   CLI_CODIGOSISTEMA    numeric(10,0),\n" +
            "   primary key (CLI_ID)\n" +
            ");";
    }
    
    public String tDadosFP(){
        return "create table DADOSFP\n" +
            "(\n" +
            "   FP_ID                INTEGER IDENTITY not null,\n" +
            "   CLI_ID               int not null,\n" +
            "   FP_AUTORIZACAO       varchar(15) not null,\n" +
            "   FP_DATA_VENDA        date not null,\n" +
            "   FP_DATA_PROX_VENDA   date not null,\n" +
            "   FP_CUPOM             numeric(9,0) not null,\n" +
            "   primary key (FP_ID)\n" +
            ");";
    }
    
    public String tProdutos(){
        return "create table PRODUTO\n" +
            "(\n" +
            "   PRO_ID               int not null,\n" +
            "   PRO_DESCRICAO        varchar(80) not null,\n" +
            "   PRO_BARRAS           varchar(14),\n" +
            "   primary key (PRO_ID)\n" +
            ");";
    }
    
    public String tVendas(){
        return "create table VENDAS\n" +
            "(\n" +
            "   VEN_ID               INTEGER IDENTITY not null,\n" +
            "   PRO_ID               int not null,\n" +
            "   CLI_ID               int not null,\n" +
            "   VEN_AUTORIZACAO      varchar(19) not null,\n" +
            "   VEN_DATA_VENDA       datetime not null,\n" +
            "   VEN_CUPOM            numeric(9,0) not null,\n" +
            "   VEN_CAIXA            varchar(3) not null,\n" +
            "   VEN_CPF              varchar(11),\n" +
            "   primary key (VEN_ID)\n" +
            ");";
    }
    
    public String[] constraint(){
        String[] c = {
            "alter table DADOSFP add constraint FK_DADOSFP_CLIENTES foreign key (CLI_ID)" +
                " references CLIENTES (CLI_ID) on delete restrict on update restrict;",
            "alter table VENDAS add constraint FK_VENDAS_CLIENTES foreign key (CLI_ID)" +
                " references CLIENTES (CLI_ID) on delete restrict on update restrict;" ,
            "alter table VENDAS add constraint FK_VENDAS_PRODUTOS foreign key (PRO_ID)" +
                " references PRODUTO (PRO_ID) on delete restrict on update restrict;"
                
        };
        return c;
    }
    
    public String[] deletes(){
        String[] d = {
            "drop table if exists VENDAS;"  ,
            "drop table if exists PRODUTO;" ,
            "drop table if exists DADOSFP;" ,
            "drop table if exists CLIENTES;"
        };
        return d;
    }
    
    public void criaBancoDeDados() throws SQLException{
        StringBuilder sb = new StringBuilder();
        for(String s : deletes()){
            sb.append(s);
        }
        ConexaoBD.executeUpdate(sb.toString());
        //ConexaoBD.executeUpdate(tClientes());
        /*ConexaoBD.executeUpdate(tDadosFP());
        ConexaoBD.executeUpdate(tProdutos());
        ConexaoBD.executeUpdate(tVendas());
        sb = new StringBuilder();
        for (String s : constraint()){
            sb.append(s);
        }
        ConexaoBD.executeUpdate(sb.toString());*/
    }
    
    
    
}
