package br.com.pc.natusfarma.tabela;

import java.util.Map;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Paulo César
 */
public class Tabela extends FramePainel implements ModeloTabela{

    private DefaultTableModel modelo;
    
    public Tabela() {
        inicializar();
    }
    
    
    @Override
    public void colunas(String[] colunas) {
        for (String coluna : colunas) {
            getModelo().addColumn(coluna);
        }
        
    }

    @Override
    public void tamanho(Integer[] tamanho) {
        for (int i = 0; i < tamanho.length; i++) {
            jTable.getColumnModel().getColumn(i).setPreferredWidth(tamanho[i]);
        }
    }

    @Override
    public void reajustavel(Boolean[] reajustavel) {
        for (int i = 0; i < reajustavel.length; i++) {
            jTable.getColumnModel().getColumn(i).setResizable(reajustavel[i]);
        }
    }
    
    @Override
    public void editavel(Boolean[] editavel) {
        for (int i = 0; i < editavel.length; i++) {
            //implementar o codigo aqui.
            //tabela.getColumnModel().getColumn(i).setCellEditor(editavel[i]);
        }
    }
    
    //parametro um para informar se é centralizado , direita e esquerda. 
    //parametro dois para informar qual é a coluna responsável para renderizar.
    public void defaultTableCellRenderer(Object column, int swingConstants){
        javax.swing.table.DefaultTableCellRenderer cellRenderer = new javax.swing.table.DefaultTableCellRenderer();
//        cellRenderer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
//        cellRenderer.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
//        cellRenderer.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cellRenderer.setHorizontalAlignment(swingConstants);
        jTable.getColumn(column).setCellRenderer(cellRenderer);
    }
    
    public void limpaTabela(){
        getModelo().setNumRows(0);
    }
    
    private void inicializar(){
        modelo = new DefaultTableModel();
        jTable.setModel(getModelo());

    }

    @Override
    public void linhas(Object[] linhas) {
        getModelo().addRow(linhas);
    }

    @Override
    public Object dadosTabela(int linha, int coluna){
        return getModelo().getValueAt(linha, coluna);
    }

    /**
     * @return the modelo
     */
    public DefaultTableModel getModelo() {
        return modelo;
    }

    
    public JTable getTabela() {
        return jTable;
    }
 

    @Override
    public int linha() {
        return jTable.getSelectedRow();
    }

    @Override
    public int[] linhas() {
        return jTable.getSelectedRows();
    }

    @Override
    public int coluna() {
        return jTable.getSelectedColumn();
    }

    @Override
    public int[] colunas() {
        return jTable.getSelectedColumns();
    }
    
    public Object[] toArray(Map<Integer, ParametrosTabelaLayout> map1, Integer campo){
        Map<Integer, ParametrosTabelaLayout> map = map1;
        ParametrosTabelaLayout[] array = map.values().toArray(new ParametrosTabelaLayout[map.size()]);
        String[] colunas = new String[array.length];
        Integer[] tamanho = new Integer[array.length];
        Boolean[] reajustavel = new Boolean[array.length];
        Boolean[] editar = new Boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            colunas[i] = array[i].getColuna();
            tamanho[i] = array[i].getTamanho();
            reajustavel[i] = array[i].isReajustavel();
            editar[i] = array[i].isEditavel();
        }
        Object[] obj = null;
        switch(campo){
            case 1:obj = colunas; break;
            case 2:obj = tamanho; break;
            case 3:obj = reajustavel; break;
            case 4:obj = editar; break;
        }
        return obj;
        
    }

 
    
}
