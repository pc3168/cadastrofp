package br.com.pc.natusfarma.tabela;

/**
 *
 * @author Paulo César
 */
public class ParametrosTabelaLayout {

    public static final Integer COLUNA  = 1;
    public static final Integer TAMANHO = 2;
    public static final Integer REAJUSTAVEL = 3;
    public static final Integer EDITAVEL = 4;
    private String coluna;
    private int tamanho;
    private boolean reajustavel;
    private boolean editavel;

    public ParametrosTabelaLayout(String coluna, int tamanho, boolean reajustavel, boolean editavel) {
        this.coluna = coluna;
        this.tamanho = tamanho;
        this.reajustavel = reajustavel;
        this.editavel = editavel;
    }

    public ParametrosTabelaLayout(String coluna, int tamanho, boolean reajustavel) {
        this(coluna, tamanho, reajustavel, false);
    }

    public ParametrosTabelaLayout(String coluna, int tamanho) {
        this(coluna, tamanho, false, false);
    }
    
    public ParametrosTabelaLayout(String coluna) {
        this(coluna, 0);
    }
    
    

    @Override
    public String toString(){
        return "["+getColuna()+", "+getTamanho()+", "+isReajustavel()+", "+isEditavel()+"]";
    }
    
    public Object[] toArray(){
        return new Object[]{getColuna() , getTamanho() , isReajustavel() , isEditavel()};
    }

    /**
     * @return the coluna
     */
    public String getColuna() {
        return coluna;
    }

    /**
     * @return the tamanho
     */
    public int getTamanho() {
        return tamanho;
    }

    /**
     * @return the reajustavel
     */
    public boolean isReajustavel() {
        return reajustavel;
    }

    /**
     * @return the editavel
     */
    public boolean isEditavel() {
        return editavel;
    }

    
   
}
