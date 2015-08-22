package br.net.eia.model.pais;

import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PaisXML {

	public String obterValorElemento(Element elemento, String nomeElemento) {

		NodeList listaElemento = elemento.getElementsByTagName(nomeElemento);
		if (listaElemento == null) {
			return null;
		}

		Element noElemento = (Element) listaElemento.item(0);
		if (noElemento == null) {
			return null;
		}

		Node no = noElemento.getFirstChild();
		if (no == null) {
			return null;
		}
		return no.getNodeValue();
	}

	public List<Pais> realizaLeituraXML() {
		List<Pais> paises = new ArrayList<Pais>();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(getClass().getResourceAsStream(
					"/paises.xml"));
			Element raiz = document.getDocumentElement();
			NodeList listaContatos = raiz.getElementsByTagName("pais");

			for (int i = 0; i < listaContatos.getLength(); i++) {
				Pais p = new Pais();
				Element contato = (Element) listaContatos.item(i);

				if (obterValorElemento(contato, "codigo") != null) {
					p.setcPais(Integer.parseInt(obterValorElemento(contato,
							"codigo")));
				}
				if (obterValorElemento(contato, "nome") != null) {
					p.setxPais(obterValorElemento(contato, "nome"));
				}
				paises.add(p);
			}
		} catch (Exception e) {

		}
		return paises;
	}

}
