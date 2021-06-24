package br.com.pc.natusfarma.classe;

import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
 *
 * @author Paulo César
 */
public class ModeloVendas extends ModeloProduto{
    
    public static final String T_VENDAS        = "VENDAS";   
    public static final String VEN_ID          = "VEN_ID";      	
    //public static final String PRO_ID          = "PRO_ID";      	
    public static final String VEN_AUTORIZACAO = "VEN_AUTORIZACAO"; 
    public static final String VEN_DATA_VENDA  = "VEN_DATA_VENDA";  
    public static final String VEN_CUPOM       = "VEN_CUPOM";      	
    public static final String VEN_CAIXA       = "VEN_CAIXA";      	
    public static final String VEN_QUANTIDADE  = "VEN_QUANTIDADE";  
    public static final String VEN_CPF         = "VEN_CPF";
    public static final String VEN_NOME        = "VEN_NOME";

    private int    venId          ;
    //private int    proId          ;
    private long   venAutorizacao ;
    private java.sql.Date venDataVenda  ;
    private java.sql.Date venDataVendaF  ;
    private int    venCupom       ;
    private int    venCaixa       ;
    private int    venQuantidade  ;
    private String venCpf;
    private String venNome;
    

    /**
     * @return the venId
     */
    public int getVenId() {
        return venId;
    }

    /**
     * @param venId the venId to set
     */
    public void setVenId(int venId) {
        this.venId = venId;
    }

    /**
     * @return the venAutorizacao
     */
    public long getVenAutorizacao() {
        return venAutorizacao;
    }

    /**
     * @param venAutorizacao the venAutorizacao to set
     */
    public void setVenAutorizacao(long venAutorizacao) {
        this.venAutorizacao = venAutorizacao;
    }

    /**
     * @return the venDataVenda
     */
    public java.sql.Date getVenDataVenda() {
        return venDataVenda;
    }
    
    /**
     * @param format
     * @return the venDataVenda
     */
    public String getVenDataVenda(String format) {
        return new SimpleDateFormat(format).format(venDataVenda);
    }
    

    /**
     * @param venData_venda the venDataVenda to set
     */
    public void setVenDataVenda(java.sql.Date venDataVenda) {
        this.venDataVenda = venDataVenda;
    }
    
    /**
     * @param venData_venda the venData_venda to set
     */
    public void setVenDataVenda(String venDataVenda) throws ParseException {
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        this.venDataVenda = new java.sql.Date(fmt.parse(venDataVenda).getTime());
    }
    
    /**
     * @return the venDataVendaF
     */
    public java.sql.Date getVenDataVendaF() {
        return venDataVendaF;
    }
    
    /**
     * @param format
     * @return the venDataVendaF
     */
    public String getVenDataVendaF(String format) {
        return new SimpleDateFormat(format).format(venDataVendaF);
    }
    

    /**
     * @param venDataVendaF the venDataVendaF to set
     */
    public void setVenDataVendaF(java.sql.Date venDataVendaF) {
        this.venDataVendaF = venDataVendaF;
    }
    
    /**
     * @param venDataVendaF the venDataVendaF to set
     * @throws java.text.ParseException
     */
    public void setVenDataVendaF(String venDataVendaF) throws ParseException {
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        this.venDataVendaF = new java.sql.Date(fmt.parse(venDataVendaF).getTime());
    }

    
    
    /**
     * @return the venCupom
     */
    public int getVenCupom() {
        return venCupom;
    }

    /**
     * @param venCupom the venCupom to set
     */
    public void setVenCupom(int venCupom) {
        this.venCupom = venCupom;
    }

    /**
     * @return the venCaixa
     */
    public int getVenCaixa() {
        return venCaixa;
    }

    /**
     * @param venCaixa the venCaixa to set
     */
    public void setVenCaixa(int venCaixa) {
        this.venCaixa = venCaixa;
    }

    /**
     * @return the venQuantidade
     */
    public int getVenQuantidade() {
        return venQuantidade;
    }

    /**
     * @param venQuantidade the venQuantidade to set
     */
    public void setVenQuantidade(int venQuantidade) {
        this.venQuantidade = venQuantidade;
    }

    /**
     * @return the venCpf
     */
    public String getVenCpf() {
        return venCpf;
    }

    /**
     * @param venCpf the venCpf to set
     */
    public void setVenCpf(String venCpf) {
        this.venCpf = venCpf == null ? "" : venCpf.trim().replace(".", "").replace("-", "");
    }

    /**
     * @return the venNome
     */
    public String getVenNome() {
        return venNome;
    }

    /**
     * @param venNome the venNome to set
     */
    public void setVenNome(String venNome) {
        this.venNome = venNome == null ? "" : venNome;
    }


}
