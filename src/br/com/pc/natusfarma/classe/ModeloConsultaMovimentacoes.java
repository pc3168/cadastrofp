
package br.com.pc.natusfarma.classe;


/**
 *
 * @author Paulo César
 */
public class ModeloConsultaMovimentacoes {
    
    private ModeloClientes clientes;
    private ModeloDadosFP dadosFp;
    private ModeloVendas vendas;

    /**
     * @return the clientes
     */
    public ModeloClientes getClientes() {
        return clientes;
    }

    /**
     * @param clientes the clientes to set
     */
    public void setClientes(ModeloClientes clientes) {
        this.clientes = clientes;
    }

    /**
     * @return the dadosFp
     */
    public ModeloDadosFP getDadosFp() {
        return dadosFp;
    }

    /**
     * @param dadosFp the dadosFp to set
     */
    public void setDadosFp(ModeloDadosFP dadosFp) {
        this.dadosFp = dadosFp;
    }

    /**
     * @return the vendas
     */
    public ModeloVendas getVendas() {
        return vendas;
    }

    /**
     * @param vendas the vendas to set
     */
    public void setVendas(ModeloVendas vendas) {
        this.vendas = vendas;
    }
    
}
