package br.net.eia.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import br.net.eia.util.certificado.NFeCacertsUtil;

public class Config {
	public Config(){
		
	}
	
	

	public static Properties getCofiguracoes(){
		Properties props = new Properties();
		String baseDir = System.getProperty("user.home");
		String OS = System.getProperty("os.name").toLowerCase();
		if (OS.indexOf("win") >= 0) {
			baseDir = "C:";
		}
		
		File f = new File(baseDir+"/EIA/Config.properties");
				
		if(!f.exists()){
			try {
				StringBuilder sb = new StringBuilder();
				sb.append("#*** Servidor ****************************************************");
				sb.append("\r\n");
				sb.append("host=http://localhost/");
				sb.append("\r\n");
				sb.append("porta=8080");
				sb.append("\r\n");
				sb.append("#*** Logomarca ****************************************************");
				sb.append("\r\n");
				sb.append("logo=C:/EIA/logo.jpg");
				sb.append("\r\n");
				sb.append("#*** UF ****************************************************");
				sb.append("\r\n");
				sb.append("UF=PB");
                                sb.append("\r\n");
				sb.append("#*** Tipo de Ambiente ****************************************************");
				sb.append("\r\n");
				sb.append("#  1 - Produção\n");
				sb.append("\r\n");
				sb.append("#  2 - Produção\n");
				sb.append("\r\n");
				sb.append("ambiente=2");
				sb.append("\r\n");
				sb.append("\r\n");
				sb.append("#*** Certificado Digital *************************************************");	
				sb.append("\r\n");
				sb.append("#  A1 - Definir A1Local\n");
				sb.append("\r\n");
				sb.append("#  A3 - Definir A3Config\n");
				sb.append("\r\n");
				sb.append("#  WIN - Repositorio do Windows");
				sb.append("\r\n");
				sb.append("tipoCert=WIN");
				sb.append("\r\n");
				sb.append("senhaCert=12345678");
				sb.append("\r\n");
				sb.append("A1Local=C:/EIA/certificado.pfx");
				sb.append("\r\n");
				sb.append("A3Config=C:/EIA/A3Config.cfg");
				sb.append("\r\n");
				sb.append("\r\n");
				sb.append("nfe-cacerts=nfe-cacerts");
				sb.append("\r\n");
				sb.append("\r\n");
				sb.append("#  Relação de Serviços Web");
				sb.append("\r\n");
				sb.append("NfeStatusServico=https://homologacao.nfe.sefazvirtual.rs.gov.br/ws/NfeStatusServico/NfeStatusServico2.asmx");
				sb.append("\r\n");
				sb.append("NFeAutorizacao=https://homologacao.nfe.sefazvirtual.rs.gov.br/ws/NfeAutorizacao/NFeAutorizacao.asmx");
				sb.append("\r\n");
				sb.append("NfeConsultaProtocolo=https://homologacao.nfe.sefazvirtual.rs.gov.br/ws/NfeConsulta/NfeConsulta2.asmx");
				sb.append("\r\n");
				sb.append("\r\n");
				sb.append("RecepcaoEvento=https://nfe-homologacao.svrs.rs.gov.br/ws/recepcaoevento/recepcaoevento.asmx");
				sb.append("\r\n");
				sb.append("obs=.");
				sb.append("\r\n");
                                try{
				File nfeCacerts = new File("nfe-cacerts");
				if(!nfeCacerts.exists()){
					nfeCacerts = NFeCacertsUtil.gerarCacerts("nfe.sefaz.rs.gov.br", 443);  
		            if (nfeCacerts != null) {  
		                System.out.println("| Cacerts gerado em: " + nfeCacerts.getAbsolutePath());  
		            }  
				}
				}catch(Exception e ){
					e.printStackTrace();
				}
                
                File eia = new File(baseDir+"/EIA");
                if(!eia.exists()){
                	Files.createDirectories(Paths.get(baseDir+"/EIA"));
                }
                                
				Files.write(Paths.get(baseDir+"/EIA/Config.properties"), sb.toString().getBytes());
				
				File A3Config = (new File(baseDir+"/EIA/A3Config.cfg"));
				if(!A3Config.exists()){
					StringBuilder sbA3 = new StringBuilder();
					sbA3.append("name=Nome do Token");
					sbA3.append("\r\n");
					sbA3.append("library=C:/WINDOWS/system32/NOME_DLL_DO_TOKEN.dll");
					Files.write(Paths.get(baseDir+"/EIA/A3Config.cfg"), sbA3.toString().getBytes());
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		try {
		    InputStream in = new FileInputStream(f.getAbsoluteFile());                   
		    props.load(in);    
		    in.close();  
		} catch (FileNotFoundException ex) {    
	        ex.printStackTrace();    
	    } catch (IOException ex) {    
	        ex.printStackTrace();    
	    } 
		return props;
	}
}
