/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.eia.util.nfe;

import java.io.StringReader;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamException;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.impl.llom.util.AXIOMUtil;

import br.inf.portalfiscal.www.nfe.wsdl.nfeautorizacao.NfeAutorizacaoStub;
import br.net.eia.enums.UF;
import br.net.eia.nfe.v310.retEnviNFe.TRetEnviNFe;

/**
 *
 * @author EIA
 */
public class EnviarLote {

	public String enviar(String xmlEnvNFe, UF uf, String url) {
        try {
            OMElement ome = AXIOMUtil.stringToOM(xmlEnvNFe);
            Iterator<?> children = ome.getChildrenWithLocalName("NFe");
            while (children.hasNext()) {
                OMElement omElement = (OMElement) children.next();
                if ((omElement != null) && ("NFe".equals(omElement.getLocalName()))) {
                    omElement.addAttribute("xmlns", "http://www.portalfiscal.inf.br/nfe", null);
                }
            }

            NfeAutorizacaoStub.NfeDadosMsg dadosMsg = new NfeAutorizacaoStub.NfeDadosMsg();
            dadosMsg.setExtraElement(ome);
            NfeAutorizacaoStub.NfeCabecMsg nfeCabecMsg = new NfeAutorizacaoStub.NfeCabecMsg();
            /**
             * Código do Estado.
             */
            nfeCabecMsg.setCUF(UF.PB.getCUF());

            /**
             * Versao do XML
             */
            nfeCabecMsg.setVersaoDados("3.10");

            NfeAutorizacaoStub.NfeCabecMsgE nfeCabecMsgE = new NfeAutorizacaoStub.NfeCabecMsgE();
            nfeCabecMsgE.setNfeCabecMsg(nfeCabecMsg);

            NfeAutorizacaoStub stub = new NfeAutorizacaoStub(url);
            NfeAutorizacaoStub.NfeAutorizacaoLoteResult result = stub.nfeAutorizacaoLote(dadosMsg, nfeCabecMsgE);
           
            return result.getExtraElement().toString();
        } catch (RemoteException | XMLStreamException ex) {
            Logger.getLogger(EnviarLote.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public TRetEnviNFe retEnviNFe(String xml){
		try {
			JAXBContext context = JAXBContext.newInstance("br.net.eia.nfe.v310.retEnviNFe");
			Unmarshaller unmarshaller = context.createUnmarshaller();
	        JAXBElement<TRetEnviNFe> element = (JAXBElement<TRetEnviNFe>) unmarshaller.unmarshal(new StringReader(xml));
	        return element.getValue();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
        
    }
}
