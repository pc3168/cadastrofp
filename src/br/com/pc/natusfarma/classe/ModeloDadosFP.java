package br.com.pc.natusfarma.classe;

import br.com.pc.util.Mensagem;
import java.text.ParseException;
import java.text.SimpleDateFormat;



/**
 *
 * @author Paulo César
 */
public class ModeloDadosFP extends ModeloClientes{
    
    public static final String T_DADOSFP = "DADOSFP";   
    public static final String FP_AUTORIZACAO     = "FP_AUTORIZACAO"    ;   
    public static final String FP_DATA_VENDA      = "FP_DATA_VENDA"     ;   
    public static final String FP_DATA_PROX_VENDA = "FP_DATA_PROX_VENDA";   
    public static final String FP_CUPOM           = "FP_CUPOM"          ;   

    private long fpAutorizacao    ;
    private java.sql.Date fpData_venda     ;
    private java.sql.Date fpData_vendaF     ;
    private java.sql.Date fpData_prox_venda;
    private java.sql.Date fpData_prox_vendaF;
    private int fpCupom          ;

    
    /**
     * @return the fpAutorizacao
     */
    public long getFpAutorizacao() {
        return fpAutorizacao;
    }

    /**
     * @param fpAutorizacao the fpAutorizacao to set
     */
    public void setFpAutorizacao(long fpAutorizacao) {
        this.fpAutorizacao = fpAutorizacao;
    }

    /**
     * @return the fpData_venda
     */
    public java.sql.Date getFpData_venda() {
        return fpData_venda;
    }
    
    /**
     * @param format
     * @return the fpData_venda
     */
    public String getFpData_venda(String format) {
        return new SimpleDateFormat(format).format(fpData_venda);
    }

    /**
     * @param fpData_venda the fpData_venda to set
     */
    public void setFpData_venda(java.sql.Date fpData_venda) {
        this.fpData_venda = fpData_venda;
    }
    
    /**
     * @param format
     * @param fpData_venda the fpData_venda to set
     */
    public void setFpData_venda(String format, String fpData_venda) {
        this.fpData_venda = converteDataSql(format, fpData_venda);
    }

    /**
     * @return the fpData_prox_venda
     */
    public java.sql.Date getFpData_prox_venda() {
        return fpData_prox_venda;
    }
    
    /**
     * @param format
     * @return the fpData_prox_venda
     */
    public String getFpData_prox_venda(String format) {
        return new SimpleDateFormat(format).format(fpData_prox_venda);
    }

    /**
     * @param fpData_prox_venda the fpData_prox_venda to set
     */
    public void setFpData_prox_venda(java.sql.Date fpData_prox_venda) {
        this.fpData_prox_venda = fpData_prox_venda;
    }
    
    /**
     * @param format
     * @param fpData_prox_venda the fpData_prox_venda to set
     */
    public void setFpData_prox_venda(String format, String fpData_prox_venda) {
        this.fpData_prox_venda = converteDataSql(format, fpData_prox_venda);
    }

    /**
     * @return the fpCupom
     */
    public int getFpCupom() {
        return fpCupom;
    }

    /**
     * @param fpCupom the fpCupom to set
     */
    public void setFpCupom(int fpCupom) {
        this.fpCupom = fpCupom;
    }

    /**
     * @return the fpData_vendaF
     */
    public java.sql.Date getFpData_vendaF() {
        return fpData_vendaF;
    }
    
    /**
     * @param format
     * @return the fpData_vendaF
     */
    public String getFpData_vendaF(String format) {
        return new SimpleDateFormat(format).format(fpData_vendaF);
    }

    /**
     * @param fpData_vendaF the fpData_vendaF to set
     */
    public void setFpData_vendaF(java.sql.Date fpData_vendaF) {
        this.fpData_vendaF = fpData_vendaF;
    }
    
    /**
     * @param format
     * @param fpData_vendaF the fpData_vendaF to set
     */
    public void setFpData_vendaF(String format, String fpData_vendaF) {
        this.fpData_vendaF = converteDataSql(format, fpData_vendaF);
    }

    /**
     * @return the fpData_prox_vendaF
     */
    public java.sql.Date getFpData_prox_vendaF() {
        return fpData_prox_vendaF;
    }
    
    /**
     * @param format
     * @return the fpData_prox_vendaF
     */
    public String getFpData_prox_vendaF(String format) {
        //ex: format dd/MM/yyyy
        return new SimpleDateFormat(format).format(fpData_prox_vendaF);
    }

    /**
     * @param fpData_prox_vendaF the fpData_prox_vendaF to set
     */
    public void setFpData_prox_vendaF(java.sql.Date fpData_prox_vendaF) {
        this.fpData_prox_vendaF = fpData_prox_vendaF;
    }
    
    /**
     * @param format
     * @param fpData_prox_vendaF the fpData_prox_vendaF to set
     */
    public void setFpData_prox_vendaF(String format, String fpData_prox_vendaF) {
        this.fpData_prox_vendaF = converteDataSql(format, fpData_prox_vendaF);
    }
    
    
    private java.sql.Date converteDataSql(String format, String data){
        java.sql.Date date = null;
        try {
            date = new java.sql.Date(new java.text.SimpleDateFormat(format).parse(data).getTime());
        } catch (ParseException ex) {
            Mensagem.mensagem("Erro ao obter a data ..... formato "+format+" data "+data+System.lineSeparator()+ex);
        }
        return date;
    }

}
