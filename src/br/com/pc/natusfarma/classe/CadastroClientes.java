package br.com.pc.natusfarma.classe;

import br.com.pc.natusfarma.bd.ClientesSql;
import br.com.pc.natusfarma.form.FormCadastroClientes;
import br.com.pc.util.FormatacaoMaskara;
import br.com.pc.util.LimitCaracter;
import br.com.pc.util.LimitNumero;
import br.com.pc.util.Mensagem;
import br.com.pc.util.ValidaCPFeCNPJ;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 *
 * @author Paulo César
 */
public class CadastroClientes extends FormCadastroClientes{
    
    public static final String TITULO = "Cadastro Cliente"; 
    public static final String VERSAO = "1.3";
    
    public CadastroClientes(){
        //ccm = new CadastroClientesModelo();
        inicializar();
    }
        
    private void inicializar(){
        
        tfCodigo.setEditable(false);
        tfCodigo.setEnabled(true);
        FormatacaoMaskara.formatacao(tfCpf, "###.###.###-##");
        FormatacaoMaskara.formatacao(tfCep, "#####-###");
        tfCodigo.setDocument(new LimitNumero(7));
        tfNumero.setDocument(new LimitNumero(5));
        tfCodSistema.setDocument(new LimitNumero(10));
        tfEndereco.setDocument(new LimitCaracter(50));
        tfBairro.setDocument(new LimitCaracter(50));
        tfNome.setDocument(new LimitCaracter(50));
        lTotal.setText(""+ClientesSql.totalClientes());
        btGravarAtualizar.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                btGravarAtualizar();
            }
        });
        
        
        btGravarAtualizar.addKeyListener(new java.awt.event.KeyAdapter() {
           
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btGravarAtualizar();
                }
            }
        });
        
        tfCodigo.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                tfCpf.requestFocus();
            }
        });
        
        tfCodigo.addFocusListener(new FocusAdapter() {

            @Override
            public void focusLost(FocusEvent e) {
               tfCodigo();
            }
        });
        
        tfCpf.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                tfNome.requestFocus();
            }
        });
        
        tfCpf.addFocusListener(new FocusAdapter() {

            @Override
            public void focusLost(FocusEvent e) {
               tfCpf();
            }
        });
        
        tfNome.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               tfCep.requestFocus();
            }
        });
        
        tfCep.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               tfEndereco.requestFocus();
            }
        });
        
        tfEndereco.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               tfNumero.requestFocus();
            }
        });
        
        tfNumero.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               cbEstado.requestFocus();
            }
        });
        
        cbEstado.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               tfComplemento.requestFocus();
            }
        });
        
        tfComplemento.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               tfBairro.requestFocus();
            }
        });
        
        tfBairro.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               tfCidade.requestFocus();
            }
        });
        
        tfCidade.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               tfTelefone1.requestFocus();
            }
        });
        
        tfTelefone1.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               tfTelefone2.requestFocus();
            }
        });
        
        tfTelefone2.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               tfCodSistema.requestFocus();
            }
        });
        
        tfCodSistema.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               btGravarAtualizar.requestFocus();
            }
        });
    }
    
    
    private void btGravarAtualizar(){
        String cpf = tfCpfFormatado(tfCpf.getText());
        if (cpf.trim().equals("") || 
                tfNome.getText().trim().equals("") || 
                tfEndereco.getText().trim().equals("") || 
                tfNumero.getText().trim().equals("") || 
                tfTelefone1.getText().trim().equals("")) {
            Mensagem.mensagem("CAMPOS COM * SÃO OBRIGATÓRIOS, POR FAVOR PREENCHER.");
            return;
        }
        if (!ValidaCPFeCNPJ.isValidCPF(cpf)) {
            tfCpf.requestFocus();
            return;
        }
        
        try {
            ModeloClientes ccm = new ModeloClientes();
            ccm.setCliNome(tfNome.getText().toUpperCase());
            ccm.setCliCpf(tfCpf.getText());
            ccm.setCliEndereco(tfEndereco.getText().toUpperCase());
            ccm.setCliNumero(Integer.parseInt(tfNumero.getText()));
            ccm.setCliBairro(tfBairro.getText().toUpperCase());
            ccm.setCliComplemento(tfComplemento.getText().toUpperCase());
            ccm.setCliCep(tfCep.getText());
            ccm.setCliCidade(tfCidade.getText().toUpperCase());
            ccm.setCliEstado(cbEstado.getSelectedItem().toString());
            ccm.setCliTelefone1(tfTelefone1.getText());
            ccm.setCliTelefone2(tfTelefone2.getText());
            ccm.setCliCodigosistema(converteStringToInt(tfCodSistema.getText()));
            
            if (verificaCadastroCpf(ccm.getCliCpf()).isEmpty()) {
                ClientesSql.insertClientes(ccm);
                Mensagem.mensagem("Cliente Cadastrado com Sucesso.");
            } else {
                ClientesSql.updateClientes(ccm);
                Mensagem.mensagem("Cliente Atualizado com Sucesso.");
            }
            
        } catch (NumberFormatException e) {
            Mensagem.mensagem("Valor informado não é um número válido.\n"+e);
        } finally{
            limpaTela();
        }
        lTotal.setText(""+ClientesSql.totalClientes());
    }
    
   
    private void tfCodigo(){
        /*ModeloClientes ccm = new ModeloClientes();
        ccm.setCliId(Integer.parseInt(tfNumero.getText()));
        ClientesSql.selectClientesCPFId(ccm, null);
        String cpf = tfCpfFormatado(tfCpf.getText());
        if (cpf.trim().equals("")) {
            return;
        }
        if (!ValidaCPFeCNPJ.isValidCPF(cpf)) {
            Mensagem.mensagem("CPF é inválido.");
            tfCpf.requestFocus();
            return;
        }
        mostrarDadosClientes(cpf);*/
    }
    
    private void tfCpf(){
        String cpf = tfCpfFormatado(tfCpf.getText());
        if (cpf.trim().equals("")) {
            return;
        }
        if (!ValidaCPFeCNPJ.isValidCPF(cpf)) {
            Mensagem.mensagem("CPF é inválido.");
            tfCpf.requestFocus();
            return;
        }
        mostrarDadosClientes(cpf);
    }
    
    public static List<ModeloClientes> verificaCadastroCpf(String cpf){
        ModeloClientes ccm = new ModeloClientes();
        ccm.setCliCpf(cpf);
        return ClientesSql.selectClientesCPFId(ccm, ModeloClientes.CLI_CPF);
    }
       
    private void limpaTela(){
        tfCodigo.setText("");
        tfNome.setText("");
        tfCpf.setText("");
        tfEndereco.setText("");
        tfNumero.setText("");
        tfBairro.setText("");
        tfComplemento.setText("");
        tfCep.setText("");
        tfCidade.setText("");
        //cbEstado.setText("");
        tfTelefone1.setText("");
        tfTelefone2.setText("");
        tfCodSistema.setText("");
    }
    
    public static String tfCpfFormatado(String cpf){
        return cpf.trim().replace(".", "").replace("-", "");
    }
    
    private int converteStringToInt(String valor) throws NumberFormatException{
        if (valor.trim().equals("")) {
            valor = "0";
        }
        return Integer.parseInt(valor);
    }
    
    private void mostrarDadosClientes(String cpf){
        List<ModeloClientes> listaClientes = verificaCadastroCpf(cpf);
        if (!listaClientes.isEmpty()) {
            tfCodigo.setText(""+listaClientes.get(0).getCliId());
            tfCpf.setText(listaClientes.get(0).getCliCpf());
            tfNome.setText(listaClientes.get(0).getCliNome());
            tfEndereco.setText(listaClientes.get(0).getCliEndereco());
            tfNumero.setText(""+listaClientes.get(0).getCliNumero());
            tfBairro.setText(listaClientes.get(0).getCliBairro());
            tfComplemento.setText(listaClientes.get(0).getCliComplemento());
            tfCep.setText(listaClientes.get(0).getCliCep());
            tfCidade.setText(listaClientes.get(0).getCliCidade());
            cbEstado.setSelectedItem(listaClientes.get(0).getCliEstado());
            tfTelefone1.setText(listaClientes.get(0).getCliTelefone1());
            tfTelefone2.setText(listaClientes.get(0).getCliTelefone2());
            tfCodSistema.setText(""+listaClientes.get(0).getCliCodigosistema());
        }else{
            limpaTela();
            tfCpf.setText(cpf);
        }
    }
    
    
        
}
