package br.com.pc.natusfarma.tabela;

/**
 *
 * @author Paulo César
 */
public interface ModeloTabela {
    
    public void colunas(String[] colunas);
    public void tamanho(Integer[] tamanho);
    public void reajustavel(Boolean[] reajustavel);
    public void editavel(Boolean[] editavel);
    public void linhas(Object[] linhas);
    public Object dadosTabela (int linha, int coluna);
    public int linha();
    public int[] linhas();
    public int coluna();
    public int[] colunas();
}
