package br.net.eia.util.nfe;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;  
import javax.xml.parsers.DocumentBuilderFactory;  
import javax.xml.parsers.ParserConfigurationException;  
 





import org.xml.sax.ErrorHandler;  
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;  
import org.xml.sax.SAXParseException;  
 
public class EnviNFeValidacaoXML implements ErrorHandler {  
   private List<String> listaComErrosDeValidacao;  
   private final InputSource ENVI_NFE = new InputSource(getClass().getResourceAsStream("/xsd/enviNFe_v3.10.xsd"));
   
   public EnviNFeValidacaoXML() {  
       this.listaComErrosDeValidacao = new ArrayList<String>();  
       
   }      
     
   public static String normalizeXML(String xml) {
		if ((xml != null) && (!"".equals(xml))) {
			xml = xml.replaceAll("\\r\\n", "");
			xml = xml.replaceAll(" standalone=\"no\"", "");
		}
		return xml;
	}

   public List<String> validateXml(String xml, String xsd) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
       factory.setNamespaceAware(true);
       factory.setValidating(true);
       factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
       factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaSource", ENVI_NFE);
       DocumentBuilder builder = null;
       try {
           builder = factory.newDocumentBuilder();
           builder.setErrorHandler(this);
       } catch (ParserConfigurationException ex) {
   		error("| UtilsBase.validateXml():");
   		throw new Exception(ex.toString());
       }

       org.w3c.dom.Document document;
       try {
           document = builder.parse(new ByteArrayInputStream(xml.getBytes()));
           org.w3c.dom.Node rootNode  = document.getFirstChild();
           info("| Validando Node: " + rootNode.getNodeName());
           return this.getListaComErrosDeValidacao();
       } catch (Exception ex) {
   		error("| UtilsBase.validateXml():");
   		throw new Exception(ex.toString());
       }
	}

	@Override
	public void error(SAXParseException exception) throws SAXException {
		if (isError(exception)) {
			listaComErrosDeValidacao.add(tratamentoRetorno(exception.getMessage()));
		}
	}

	@Override
	public void fatalError(SAXParseException exception) throws SAXException {
		listaComErrosDeValidacao.add(tratamentoRetorno(exception.getMessage()));
	}

	@Override
	public void warning(SAXParseException exception) throws SAXException {
		listaComErrosDeValidacao.add(tratamentoRetorno(exception.getMessage()));
	}

	private String tratamentoRetorno(String message) {
		message = message.replaceAll("cvc-type.3.1.3:", "");
		message = message.replaceAll("cvc-complex-type.2.4.a:", "");
		message = message.replaceAll("cvc-complex-type.2.4.b:", "");
		message = message.replaceAll("The value", "O valor");
		message = message.replaceAll("of element", "do campo");
		message = message.replaceAll("is not valid", "não é valido");
		message = message.replaceAll("Invalid content was found starting with element", "Encontrado o campo");
		message = message.replaceAll("One of", "Campo(s)");
		message = message.replaceAll("is expected", "é obrigatorio");
		message = message.replaceAll("\\{", "");
		message = message.replaceAll("\\}", "");
		message = message.replaceAll("\"", "");
		message = message.replaceAll("http://www.portalfiscal.inf.br/nfe:", "");
		return message.trim();
	}

	public List<String> getListaComErrosDeValidacao() {
		return listaComErrosDeValidacao;
	}

	private boolean isError(SAXParseException exception) {
		if (exception.getMessage().startsWith("cvc-pattern-valid") ||
			exception.getMessage().startsWith("cvc-maxLength-valid") ||
			exception.getMessage().startsWith("cvc-datatype")) {
			return false;
		}
		return true;
	}
	
	private static void error(String error) {
		System.out.println("ERROR: " + error);
	}

	private static void info(String info) {
		System.out.println("INFO: " + info);
	}
}  