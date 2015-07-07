package br.net.eia.xml;

import java.awt.Desktop;
import java.io.BufferedReader;    
import java.io.File;
import java.io.FileInputStream;    
import java.io.FilenameFilter;
import java.io.IOException;    
import java.io.InputStreamReader;    
import java.io.StringReader; 
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;    
import javax.xml.bind.JAXBException;    
import javax.xml.bind.Unmarshaller;    
import javax.xml.transform.stream.StreamSource;   
import br.net.eia.nfe.v310.TNfeProc;
    
public class lerXML_JAXB {    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new lerXML_JAXB().lerNota();
    }
    
    private String setLength(String s, int l) {  
    if (s == null)  
        s = new String();  
  
    if (s.length() == l) {  
        return s;  
    } else if (s.length() < l) {  
        char[] esp = new char[l - s.length()];  
        Arrays.fill(esp, ' ');  
        return s.concat(String.valueOf(esp));  
    } else {  
        return s.substring(0, l);  
    }  
}
    
    
    public void lerNota(){
    	double CFOP5401Qtd=0;
    	double CFOP5904Qtd=0;
    	double CFOP5116 = 0;
    	double faturamento = 0;
    	
    	FilenameFilter filtro = new FilenameFilter() {
    	    public boolean accept(File dir, String name) {  
    	        return name.endsWith("-procNfe.xml");   
    	    }  
    	};  
        String local = "/home/albertiano/xml/autorizada";
    	File arquivos = new File(local);  
    	String[] nomeArquivos = arquivos.list(filtro);  
    	StringBuilder sb = new StringBuilder();
        for (String nomeArquivo : nomeArquivos) {
            TNfeProc nfe = getTNfeProc(local + "/" + nomeArquivo);
            String CFOP = nfe.getNFe().getInfNFe().getDet().get(0).getProd().getCFOP();
            StringBuilder sbAcao = new StringBuilder();
            sbAcao
                    .append(" | NF-e: ")
                    .append(nfe.getNFe().getInfNFe().getIde().getNNF())
                    .append(" | " + "CFOP: ")
                    .append(setLength(nfe.getNFe().getInfNFe().getDet().get(0).getProd().getCFOP(), 4))
                    .append(" | ")
                    .append(setLength(nfe.getNFe().getInfNFe().getDet().get(0).getProd().getXProd(), 40))
                    .append(" | ")
                    .append(setLength(nfe.getNFe().getInfNFe().getDet().get(0).getProd().getQTrib(), 6))
                    .append(" | Preço: ")
                    .append(setLength(nfe.getNFe().getInfNFe().getDet().get(0).getProd().getVUnTrib(), 4))
                    .append(" | Valor: ")
                    .append(setLength(nfe.getNFe().getInfNFe().getTotal().getICMSTot().getVNF(), 8));
            
            //System.out.println(sbAcao);
            sb.append("\r\n");
            sb.append(sbAcao);
            switch (CFOP) {
                case "5401":
                    CFOP5401Qtd += Double.parseDouble(nfe.getNFe().getInfNFe().getDet().get(0).getProd().getQTrib());
                    faturamento += Double.parseDouble(nfe.getNFe().getInfNFe().getDet().get(0).getProd().getVProd());
                    break;
                case "5904":
                    CFOP5904Qtd += Double.parseDouble(nfe.getNFe().getInfNFe().getDet().get(0).getProd().getQTrib());
                    faturamento += Double.parseDouble(nfe.getNFe().getInfNFe().getDet().get(0).getProd().getVProd());
                    break;
                case "5116":
                    CFOP5116 +=Double.parseDouble(nfe.getNFe().getInfNFe().getDet().get(0).getProd().getQTrib());
                    faturamento+=Double.parseDouble(nfe.getNFe().getInfNFe().getDet().get(0).getProd().getVProd());
                    break;
                default:
                    faturamento+=Double.parseDouble(nfe.getNFe().getInfNFe().getDet().get(0).getProd().getVProd());
                    break;
            }
        }
              sb.append("\r\n")
                 .append("\r\n")
                .append("CFOP 5401: ")
                .append(CFOP5401Qtd)
                .append(" Aguas")
                .append("\r\n")                
                .append("CFOP 5904: ")
                .append(CFOP5904Qtd)
                .append(" Aguas")
                .append("\r\n")
                .append("CFOP 5116: ")
                .append(CFOP5116)
                .append(" Aguas")
                .append("\r\n")
                .append("\r\n")
                .append("Faturamento: R$ ")
                .append(faturamento);
        try {
            Files.write(Paths.get(local+"/Resultado.txt"), sb.toString().getBytes("UTF-8"));
        } catch (IOException ex) {
            Logger.getLogger(lerXML_JAXB.class.getName()).log(Level.SEVERE, null, ex);
        }    	
    	System.out.println();
    	System.out.println("CFOP 5401: "+CFOP5401Qtd+" Aguas");
    	System.out.println("CFOP 5904: "+CFOP5904Qtd+" Aguas");
    	System.out.println("CFOP 5116: "+CFOP5116+" Aguas");
    	System.out.println();
    	System.out.println("Faturamento: R$ "
    	+faturamento);
        Desktop desktop = Desktop.getDesktop();    
        try {
            desktop.open(new File(local+"/Resultado.txt"));
        } catch (IOException ex) {
            Logger.getLogger(lerXML_JAXB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    public TNfeProc getTNfeProc(String local){

    	TNfeProc wNfe = null;
        try {    
            String xml = lerXML(local);  
              
            wNfe = getNFe(xml);  
              
              
        } catch (Exception e) {    
            error(e.toString());    
        }    
        return wNfe;
    }
       
    public TNfeProc getNFe(String xml) throws Exception{        
        try {        
            JAXBContext context = JAXBContext.newInstance(TNfeProc.class);        
            Unmarshaller unmarshaller = context.createUnmarshaller();        
            TNfeProc nfe = unmarshaller.unmarshal(new StreamSource(new StringReader(xml)), TNfeProc.class).getValue();        
            return nfe;  
        } catch (JAXBException ex) {        
            throw new Exception(ex.toString());        
        }        
    }        
      
    private String lerXML(String fileXML) throws IOException {    
        String linha = "";    
        StringBuilder xml = new StringBuilder();    
    
        BufferedReader in = new BufferedReader(new InputStreamReader(    
                new FileInputStream(fileXML)));    
        while ((linha = in.readLine()) != null) {    
            xml.append(linha);    
        }    
        in.close();    
    
        return xml.toString();    
    }    
    
    private static void error(String log) {    
        System.out.println("ERROR: " + log);    
    }        
} 