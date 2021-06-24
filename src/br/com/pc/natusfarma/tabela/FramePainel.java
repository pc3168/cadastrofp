package br.com.pc.natusfarma.tabela;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Paulo César
 */
public class FramePainel extends JPanel{

    public JTable jTable;
    
    public FramePainel() {
        inicializar();
    }
    
    private void inicializar(){
        jTable = new JTable();
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.add(jTable);
        jScrollPane.setViewportView(jTable);
        
        //this.setPreferredSize(new Dimension(100,300));
        
        jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //define o tamanho do cabeçalho de forma manual.
        jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //trava o cabeçalho para que não possa deslocar o local.
        jTable.getTableHeader().setReorderingAllowed(false);
        
        this.setLayout(new java.awt.GridLayout());
        
        this.add(jScrollPane);
    }
}
