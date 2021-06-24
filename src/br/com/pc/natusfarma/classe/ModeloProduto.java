package br.com.pc.natusfarma.classe;

/**
 *
 * @author Paulo César
 */
public class ModeloProduto {
    
    public static final String T_PRODUTO = "PRODUTO";
    public static final String PRO_ID        = "PRO_ID";       
    public static final String PRO_DESCRICAO = "PRO_DESCRICAO";
    public static final String PRO_BARRAS    = "PRO_BARRAS";   
    public static final String PRO_DIAS      = "PRO_DIAS";     
    public static final String PRO_ATIVO     = "PRO_ATIVO";
    public static final String PRO_ATIVO_ID  = "PRO_ATIVO_ID";       

    private int    proId        ;
    private String proDescricao ;
    private String proBarras    ;
    private int    proDias      ;
    private String proAtivo     ;

    /**
     * @return the proId
     */
    public int getProId() {
        return proId;
    }

    /**
     * @param proId the proId to set
     */
    public void setProId(int proId) {
        this.proId = proId;
    }

    /**
     * @return the proDescricao
     */
    public String getProDescricao() {
        return proDescricao;
    }

    /**
     * @param proDescricao the proDescricao to set
     */
    public void setProDescricao(String proDescricao) {
        this.proDescricao = proDescricao;
    }

    /**
     * @return the proBarras
     */
    public String getProBarras() {
        return proBarras;
    }

    /**
     * @param proBarras the proBarras to set
     */
    public void setProBarras(String proBarras) {
        this.proBarras = proBarras;
    }

    /**
     * @return the proDias
     */
    public int getProDias() {
        return proDias;
    }

    /**
     * @param proDias the proDias to set
     */
    public void setProDias(int proDias) {
        this.proDias = proDias;
    }

    /**
     * @return the proAtivo
     */
    public String getProAtivo() {
        return proAtivo;
    }

    /**
     * @param proAtivo the proAtivo to set
     */
    public void setProAtivo(String proAtivo) {
        this.proAtivo = proAtivo;
    }

  
}
