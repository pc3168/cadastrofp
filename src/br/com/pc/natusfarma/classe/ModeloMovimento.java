package br.com.pc.natusfarma.classe;

/**
 *
 * @author Paulo César
 */
public class ModeloMovimento extends ModeloProduto{
    
   public static final String T_MOVIMENTO    = "MOVIMENTO"     ;
   public static final String MOV_ID         = "MOV_ID";
   public static final String FP_AUTORIZACAO = "FP_AUTORIZACAO";
   //public static final String PRO_ID         = "PRO_ID";
   public static final String MOV_ECF        = "MOV_ECF";
   public static final String MOV_QUANTIDADE = "MOV_QUANTIDADE";
   
   private int    movId           ;
   private long   fpAutorizacao   ;
   //private String pro_id           ;
   private int    movEcf          ;
   private int    movQuantidade   ;

    /**
     * @return the movId
     */
    public int getMovId() {
        return movId;
    }

    /**
     * @param movId the movId to set
     */
    public void setMovId(int movId) {
        this.movId = movId;
    }

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
     * @return the movEcf
     */
    public int getMovEcf() {
        return movEcf;
    }

    /**
     * @param movEcf the movEcf to set
     */
    public void setMovEcf(int movEcf) {
        this.movEcf = movEcf;
    }

    /**
     * @return the movQuantidade
     */
    public int getMovQuantidade() {
        return movQuantidade;
    }

    /**
     * @param movQuantidade the movQuantidade to set
     */
    public void setMovQuantidade(int movQuantidade) {
        this.movQuantidade = movQuantidade;
    }
    
}
