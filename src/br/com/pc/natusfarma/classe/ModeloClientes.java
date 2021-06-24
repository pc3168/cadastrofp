
package br.com.pc.natusfarma.classe;

/**
 *
 * @author Paulo César
 */
public class ModeloClientes {
    
    public static final String T_CLIENTES       = "CLIENTES";
    public static final String CLI_ID         = "CLI_ID"         ;
    public static final String CLI_NOME         = "CLI_NOME"         ;
    public static final String CLI_CPF          = "CLI_CPF"          ;
    public static final String CLI_ENDERECO     = "CLI_ENDERECO"     ;
    public static final String CLI_NUMERO       = "CLI_NUMERO"       ;
    public static final String CLI_BAIRRO       = "CLI_BAIRRO"       ;
    public static final String CLI_COMPLEMENTO  = "CLI_COMPLEMENTO"  ;
    public static final String CLI_CEP          = "CLI_CEP"          ;
    public static final String CLI_CIDADE       = "CLI_CIDADE"       ;
    public static final String CLI_ESTADO       = "CLI_ESTADO"       ;
    public static final String CLI_TELEFONE1    = "CLI_TELEFONE1"    ;
    public static final String CLI_TELEFONE2    = "CLI_TELEFONE2"    ;
    public static final String CLI_CODIGOSISTEMA= "CLI_CODIGOSISTEMA";
   
    private int cliId         ;
    private String cliNome         ;
    private String cliCpf          ;
    private String cliEndereco     ;
    private int cliNumero       ;
    private String cliBairro       ;
    private String cliComplemento  ;
    private String cliCep          ;
    private String cliCidade       ;
    private String cliEstado       ;
    private String cliTelefone1    ;
    private String cliTelefone2    ;
    private int cliCodigosistema;

    /**
     * @return the cliNome
     */
    public String getCliNome() {
        return cliNome;
    }

    /**
     * @param cliNome the cliNome to set
     */
    public void setCliNome(String cliNome) {
        this.cliNome = cliNome;
    }

    /**
     * @return the cliCpf
     */
    public String getCliCpf() {
        return cliCpf;
    }

    /**
     * @param cliCpf the cliCpf to set
     */
    public void setCliCpf(String cliCpf) {
        this.cliCpf = cliCpf == null ? "" : cliCpf.trim().replace(".", "").replace("-", "");
    }

    /**
     * @return the cliEndereco
     */
    public String getCliEndereco() {
        return cliEndereco;
    }

    /**
     * @param cliEndereco the cliEndereco to set
     */
    public void setCliEndereco(String cliEndereco) {
        this.cliEndereco = cliEndereco;
    }

    /**
     * @return the cliNumero
     */
    public int getCliNumero() {
        return cliNumero;
    }

    /**
     * @param cliNumero the cliNumero to set
     */
    public void setCliNumero(int cliNumero) {
        this.cliNumero = cliNumero;
    }

    /**
     * @return the cliBairro
     */
    public String getCliBairro() {
        return cliBairro;
    }

    /**
     * @param cliBairro the cliBairro to set
     */
    public void setCliBairro(String cliBairro) {
        this.cliBairro = cliBairro;
    }

    /**
     * @return the cliComplemento
     */
    public String getCliComplemento() {
        return cliComplemento;
    }

    /**
     * @param cliComplemento the cliComplemento to set
     */
    public void setCliComplemento(String cliComplemento) {
        this.cliComplemento = cliComplemento;
    }

    /**
     * @return the cliCep
     */
    public String getCliCep() {
        return cliCep;
    }

    /**
     * @param cliCep the cliCep to set
     */
    public void setCliCep(String cliCep) {
        this.cliCep = cliCep;
    }

    /**
     * @return the cliCidade
     */
    public String getCliCidade() {
        return cliCidade;
    }

    /**
     * @param cliCidade the cliCidade to set
     */
    public void setCliCidade(String cliCidade) {
        this.cliCidade = cliCidade;
    }

    /**
     * @return the cliEstado
     */
    public String getCliEstado() {
        return cliEstado;
    }

    /**
     * @param cliEstado the cliEstado to set
     */
    public void setCliEstado(String cliEstado) {
        this.cliEstado = cliEstado;
    }

    /**
     * @return the cliTelefone1
     */
    public String getCliTelefone1() {
        return cliTelefone1;
    }

    /**
     * @param cliTelefone1 the cliTelefone1 to set
     */
    public void setCliTelefone1(String cliTelefone1) {
        this.cliTelefone1 = cliTelefone1;
    }

    /**
     * @return the cliTelefone2
     */
    public String getCliTelefone2() {
        return cliTelefone2;
    }

    /**
     * @param cliTelefone2 the cliTelefone2 to set
     */
    public void setCliTelefone2(String cliTelefone2) {
        this.cliTelefone2 = cliTelefone2;
    }

    /**
     * @return the cliCodigosistema
     */
    public int getCliCodigosistema() {
        return cliCodigosistema;
    }

    /**
     * @param cliCodigosistema the cliCodigosistema to set
     */
    public void setCliCodigosistema(int cliCodigosistema) {
        this.cliCodigosistema = cliCodigosistema;
    }

    /**
     * @return the cliId
     */
    public int getCliId() {
        return cliId;
    }

    /**
     * @param cliId the cliId to set
     */
    public void setCliId(int cliId) {
        this.cliId = cliId;
    }

    
}
