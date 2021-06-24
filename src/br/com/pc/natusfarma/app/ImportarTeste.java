
package br.com.pc.natusfarma.app;

import br.com.pc.natusfarma.bd.ClientesSql;
import br.com.pc.natusfarma.bd.DadosFPSql;
import br.com.pc.natusfarma.classe.ModeloClientes;
import br.com.pc.natusfarma.classe.ModeloDadosFP;
import br.com.pc.util.Unicode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Paulo
 */
public class ImportarTeste {
    
    private final java.io.File file1 = new java.io.File("C:\\Users\\Paulo\\Desktop\\farmaciapopular\\clientes.csv");
    private final java.io.File file2 = new java.io.File("C:\\Users\\Paulo\\Desktop\\farmaciapopular\\dadosfp.csv");
    
    private List<ModeloClientes> lerArquivoLayout() {
        java.io.BufferedReader br;
        List<ModeloClientes> lista = new ArrayList();
        ModeloClientes ccm;
        String[] linha ;
        try{
            br = new java.io.BufferedReader(new java.io.InputStreamReader(new java.io.FileInputStream(file1), Unicode.UTF_8));
            
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
                ClientesSql.insertClientes(ccm);
                //lista.add(ccm);
            }
            br.close();   
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lista;
    }
    
    private List<ModeloDadosFP> lerArquivoLayoutFp() {
        java.io.BufferedReader br = null;
        List<ModeloDadosFP> lista = new ArrayList();
        ModeloDadosFP dfm;
        String[] linha ;
        try{
            br = new java.io.BufferedReader(new java.io.InputStreamReader(new java.io.FileInputStream(file2), Unicode.UTF_8));
            
            while(br.ready()){
                linha = br.readLine().split(";");
                dfm = new ModeloDadosFP();
                dfm.setCliCpf(linha[1].trim());
                dfm.setFpAutorizacao(Long.parseLong(linha[2].trim()));
                dfm.setFpData_venda(converteDataSql("yyyy-MM-dd", linha[3].trim()));
                dfm.setFpData_prox_venda(converteDataSql("yyyy-MM-dd", linha[4].trim()));
                dfm.setFpCupom(Integer.parseInt(linha[5].trim()));
                DadosFPSql.insertDadosFP(dfm);
                //lista.add(dfm);
            }
            br.close();   
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lista;
    }
    
    private java.sql.Date converteDataSql(String format, String data){
        java.sql.Date date = null;
        try {
            date = new java.sql.Date(new java.text.SimpleDateFormat(format).parse(data).getTime());
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date;
    }
    
    public static void main(String[] args) {
        ImportarTeste app = new ImportarTeste();
        //List<CadastroClientesModelo> lista = app.lerArquivoLayout();
        /*for (int i = 0; i < lista.size(); i++) {
            System.out.println(lista.get(i).getCliNome());
        }*/
        
        //List<DadosFPModelo> lista2 = app.lerArquivoLayoutFp();
        /*for (int i = 0; i < lista2.size(); i++) {
            System.out.println(lista2.get(i).getFpData_prox_venda());
        }*/
        
    }
}
