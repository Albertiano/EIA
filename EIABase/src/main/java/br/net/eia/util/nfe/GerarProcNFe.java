/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.eia.util.nfe;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author EIA
 */
public class GerarProcNFe {    
    
    public String gerarProcNFeEnvi(String envNFe, String retEnvNfe) {
        try {
            Document document = documentFactory(envNFe);
            NodeList nodeListNfe = document.getDocumentElement().getElementsByTagName("NFe");  
            NodeList nodeListInfNfe = document.getElementsByTagName("infNFe");  
  
            for (int i = 0; i < nodeListNfe.getLength(); i++) {  
                Element el = (Element) nodeListInfNfe.item(i);  
                String chaveNFe = el.getAttribute("Id");                  
                
                String xmlNFe = outputXML(nodeListNfe.item(i));  
                String xmlProtNFe = getProtNFe(retEnvNfe, chaveNFe);  
                
                return buildNFeProc(xmlNFe, xmlProtNFe);
            }  
        } catch (SAXException | IOException | ParserConfigurationException | TransformerException ex) {
            Logger.getLogger(GerarProcNFe.class.getName()).log(Level.SEVERE, null, ex);
        }            
        return null;          
    } 
    
    private String buildNFeProc(String xmlNFe, String xmlProtNFe) {  
        StringBuilder nfeProc = new StringBuilder();  
        nfeProc.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")  
            .append("<nfeProc versao=\"3.10\" xmlns=\"http://www.portalfiscal.inf.br/nfe\">")  
            .append(xmlNFe)  
            .append(xmlProtNFe)  
            .append("</nfeProc>");  
        return nfeProc.toString();  
    }  
    
     private String getProtNFe(String xml, String chaveNFe) throws SAXException, IOException, ParserConfigurationException, TransformerException {
        Document document = documentFactory(xml);
        NodeList nodeListProtNFe = document.getDocumentElement().getElementsByTagName("protNFe");        
        NodeList nodeListChNFe = document.getElementsByTagName("chNFe");
        for (int i = 0; i < nodeListProtNFe.getLength(); i++) {
            Element el = (Element) nodeListChNFe.item(i);
            String chaveProtNFe = el.getTextContent();
            if (chaveNFe.contains(chaveProtNFe)) {
                return outputXML(nodeListProtNFe.item(i));
            }
        }
        return "";
    }
    
    private Document documentFactory(String xml) throws SAXException,  
            IOException, ParserConfigurationException {  
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        factory.setNamespaceAware(true);  
        Document document = factory.newDocumentBuilder().parse(  
                new ByteArrayInputStream(xml.getBytes()));  
        return document;  
    }
    
    private static String outputXML(Node node) throws TransformerException {  
        ByteArrayOutputStream os = new ByteArrayOutputStream();  
        TransformerFactory tf = TransformerFactory.newInstance();  
        Transformer trans = tf.newTransformer();  
        trans.transform(new DOMSource(node), new StreamResult(os));  
        String xml = os.toString();  
        if ((xml != null) && (!"".equals(xml))) {  
            xml = xml.replaceAll("<\\?xml version=\"1.0\" encoding=\"UTF-8\"\\?>", "");  
        }  
        return xml;  
    } 
}
