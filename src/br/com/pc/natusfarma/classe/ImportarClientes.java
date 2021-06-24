package br.com.pc.natusfarma.classe;

import br.com.pc.natusfarma.bd.ClientesSql;
import br.com.pc.natusfarma.form.FormImportar;
import br.com.pc.natusfarma.tabela.Tabela;
import br.com.pc.util.Mensagem;
import br.com.pc.util.Unicode;
import br.com.pc.util.jfilechoose.EscolherArquivo;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Paulo César 
 */
public class ImportarClientes extends FormImportar{
    
    public static final String VERSAO = "VERSÃO 1.0";
    public static final String TITULO = "Importar Clientes";
    private java.io.File arquivo;
    private Tabela tabela;
    private String[] layoutColunas = {"CPF","NOME","ENDERECO","NUMERO","BAIRRO","COMPLEMENTO","CEP","CIDADE","ESTADO","TELEFONE1","TELEFONE2","CODIGOSISTEMA"};
    private List<ModeloClientes> lista;

    public ImportarClientes() {
        inicializar();
    }
    
    private void inicializar(){
        tfNome.setEditable(false);
        cbUnicode.addItem(Unicode.ISO_8859_1);
        cbUnicode.addItem(Unicode.UTF_8);
        barra.setStringPainted(true);
        btGravar.setEnabled(false);
        montaTabela();
        
        btNome.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                btNome();
            }
        });
        
        btImportar.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                btImportar();
            }
        });
        
        btGravar.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                btGravar();
            }
        });
        
        btLayout.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                btLayout();
            }
        });
        
        
    }
    
    private void btNome(){  
        EscolherArquivo es = EscolherArquivo.getInstance();
        barra.setValue(0);
        lista = null;
        java.io.File file = es.abrir(this);
        if (file == null) {
            tfNome.setText("");
            arquivo = file;
            return;
        }
        arquivo = file;
        tfNome.setText(file.getAbsolutePath());
    }

    private void btImportar(){
        if (arquivo == null) {return;}
        if (!arquivo.isFile()){return;}
        if (lista != null) {
            lista.clear();
        }
        lista = lerArquivoLayout();
        if (lista.isEmpty()) {
            return;
        }
        tabela.limpaTabela();
        barra.setMaximum(lista.size());
        for (int i = 0; i < lista.size(); i++) {
            tabela.linhas(new Object[]{
                lista.get(i).getCliCpf(),
                lista.get(i).getCliNome(),
                lista.get(i).getCliEndereco(),
                lista.get(i).getCliNumero(),
                lista.get(i).getCliBairro(),
                lista.get(i).getCliComplemento(),
                lista.get(i).getCliCep(),
                lista.get(i).getCliCidade(),
                lista.get(i).getCliEstado(),
                lista.get(i).getCliTelefone1(),
                lista.get(i).getCliTelefone2(),
                lista.get(i).getCliCodigosistema()
            });
            barra.setValue(i+1);
        }
        btGravar.setEnabled(true);
    }
    
    private void btGravar(){
        if (tabela.getModelo().getColumnCount() == 0) {
            return;
        }
        desativar(false);
        barra.setValue(0);
        new Thread(new Runnable(){   
            public void run(){   
                for (int i = 0; i < lista.size(); i++) {
                    if(ClientesSql.selectClientesCPFId(lista.get(i), ModeloClientes.CLI_CPF).isEmpty()){
                        ClientesSql.insertClientes(lista.get(i));
                    }else{
                        ClientesSql.updateClientes(lista.get(i));
                    }
                    barra.setValue(i+1);
                }
                Mensagem.mensagem("Clientes importados com sucesso.");
                tabela.limpaTabela();
                lista.clear();
                desativar(true);
            }   
        }).start(); 
    }
    
    private void btLayout(){
        String mensagem = "";
        for (int i = 0; i < layoutColunas.length; i++) {
            if (i!=0) {
                mensagem += " ; ";
            }
            mensagem += layoutColunas[i];
        }
        Mensagem.mensagem("O Layout deve ser preenchido com os seguintes dados separados por ; "+System.lineSeparator()+mensagem);
    }
    
    private List<ModeloClientes> lerArquivoLayout() {
        java.io.BufferedReader br;
        List<ModeloClientes> lista = new ArrayList();
        ModeloClientes ccm;
        String[] linha ;
        try{
            br = new java.io.BufferedReader(new java.io.InputStreamReader(new java.io.FileInputStream(arquivo), ""+cbUnicode.getSelectedItem()));
            
            while(br.ready()){
                linha = br.readLine().split(";");
                ccm = new ModeloClientes();
                ccm.setCliCpf(linha[0].trim());
                ccm.setCliNome(linha[1].trim());
                ccm.setCliEndereco(linha[2].trim());
                ccm.setCliNumero(Integer.parseInt(linha[3].trim()));
                ccm.setCliBairro(linha[4].trim());
                ccm.setCliComplemento(linha[5].trim());
                ccm.setCliCep(linha[6].trim());
                ccm.setCliCidade(linha[7].trim());
                ccm.setCliEstado(linha[8].trim());
                ccm.setCliTelefone1(linha[9].trim());
                ccm.setCliTelefone2(linha[10].trim());
                ccm.setCliCodigosistema(Integer.parseInt(linha[11].trim()));
                lista.add(ccm);
            }
            br.close();   
        } catch (Exception ex) {
            Mensagem.mensagem(""+ex, "Erro", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        return lista;
    }
    
    private void montaTabela(){
        tabela = new Tabela();
        painelPrincipal.add(tabela);
        painelPrincipal.setPreferredSize(new Dimension(100,300));
        painelPrincipal.setLayout(new java.awt.GridLayout());
        tabela.colunas(layoutColunas);
        tabela.tamanho(new Integer[]{100,100,100,100,100,100,100,100,100,100,100,100});
        //tabela.reajustavel(new Boolean[]{true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true});
    }
    
    private void desativar(boolean ativar){
        btNome.setEnabled(ativar);
        btImportar.setEnabled(ativar);
        btGravar.setEnabled(ativar && btGravar.isEnabled());
    }
}
