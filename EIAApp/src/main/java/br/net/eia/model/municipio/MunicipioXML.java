package br.net.eia.model.municipio;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MunicipioXML {
	public List<Municipio> realizaLeituraXML(){
		List<Municipio> paises = new ArrayList<Municipio>();
		try{
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(getClass().getResourceAsStream("/municipios.xml"));
        Element raiz = document.getDocumentElement();
        NodeList listaContatos = raiz.getElementsByTagName("municipio");
        
        for (int i = 0; i < listaContatos.getLength(); i++) {
            Municipio p = new Municipio();
                Element contato = (Element) listaContatos.item(i);

                if(obterValorElemento(contato, "UF")!=null){
                    p.setUF(UF.valueOf(obterValorElemento(contato, "UF")));
                }
                if(obterValorElemento(contato, "cMun")!=null){
                    p.setcMun(Integer.parseInt(obterValorElemento(contato, "cMun")));
                }
                if(obterValorElemento(contato, "xMun")!=null){
                    p.setxMun(obterValorElemento(contato, "xMun"));
                }
                paises.add(p);      
        }
		}catch(Exception e){
			
		}
        return paises;
    }
    public String obterValorElemento(Element elemento, String nomeElemento){
		
		NodeList listaElemento = elemento.getElementsByTagName(nomeElemento);
		if (listaElemento == null){
			return null;
		}
		
		Element noElemento = (Element) listaElemento.item(0);
		if (noElemento == null){
			return null;
		}
		
		Node no = noElemento.getFirstChild();
                if (no == null){
			return null;
		}
		return no.getNodeValue();
	}
}
