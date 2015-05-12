/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.eia.util.nfe;

import br.inf.portalfiscal.www.nfe.wsdl.nfestatusservico2.NfeStatusServico2Stub;
import br.inf.portalfiscal.www.nfe.wsdl.nfestatusservico2.NfeStatusServico2Stub.NfeStatusServicoNF2Result;
import br.net.eia.enums.UF;
import br.net.eia.nfe.v310.consStatServ.ObjectFactory;
import br.net.eia.nfe.v310.consStatServ.TConsStatServ;
import br.net.eia.nfe.v310.retConsStatServ.TRetConsStatServ;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamException;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.impl.llom.util.AXIOMUtil;
import org.apache.axis2.AxisFault;

/**
 *
 * @author EIA
 */
public class ConsultarStatusServicos {
    
    private String createXML(UF uf, String ambiente){
        try {
            TConsStatServ consStatServ = new TConsStatServ();
            consStatServ.setCUF(uf.getCUF());
            consStatServ.setTpAmb(ambiente);
            consStatServ.setVersao("3.10");
            consStatServ.setXServ("STATUS");
            return strValueOf(consStatServ);
        } catch (JAXBException ex) {
            Logger.getLogger(ConsultarStatusServicos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private String strValueOf(TConsStatServ consStatServ) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(TConsStatServ.class);
        Marshaller marshaller = context.createMarshaller();
        JAXBElement<TConsStatServ> element = new ObjectFactory().createConsStatServ(consStatServ);
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

        StringWriter sw = new StringWriter();
        marshaller.marshal(element, sw);
        return sw.toString();
    }
    

    public TRetConsStatServ consultaStatusServico(UF uf, String ambiente,
            String url) {
        StringBuilder xml = new StringBuilder();
            xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
               .append(createXML(uf,ambiente));
        try {
            OMElement ome = AXIOMUtil.stringToOM(xml.toString());

            NfeStatusServico2Stub.NfeDadosMsg dadosMsg = new NfeStatusServico2Stub.NfeDadosMsg();
            dadosMsg.setExtraElement(ome);

            NfeStatusServico2Stub.NfeCabecMsg nfeCabecMsg = new NfeStatusServico2Stub.NfeCabecMsg();
            /**
             * Codigo do Estado.
             */
            nfeCabecMsg.setCUF(uf.getCUF());

            /**
             * Versao do XML
             */
            nfeCabecMsg.setVersaoDados("3.10");
            NfeStatusServico2Stub.NfeCabecMsgE nfeCabecMsgE = new NfeStatusServico2Stub.NfeCabecMsgE();
            nfeCabecMsgE.setNfeCabecMsg(nfeCabecMsg);

            NfeStatusServico2Stub stub = new NfeStatusServico2Stub(url);
            NfeStatusServico2Stub.NfeStatusServicoNF2Result nfeStatusServicoNF2Result = stub.nfeStatusServicoNF2(dadosMsg, nfeCabecMsgE);

            return parseXML(nfeStatusServicoNF2Result);
        } catch (XMLStreamException ex) {
            Logger.getLogger(ConversorNFe.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JAXBException ex) {
            Logger.getLogger(ConversorNFe.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AxisFault ex) {
            Logger.getLogger(ConversorNFe.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ConversorNFe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private TRetConsStatServ parseXML(
            NfeStatusServicoNF2Result nfeStatusServicoNF2Result)
            throws JAXBException {
        try {
            String xml = nfeStatusServicoNF2Result.getExtraElement().toString();

            JAXBContext context = JAXBContext.newInstance("br.net.eia.nfe.v310.retConsStatServ");  
            Unmarshaller unmarshaller = context.createUnmarshaller();  
            JAXBElement<TRetConsStatServ> element = (JAXBElement<TRetConsStatServ>) unmarshaller  
                    .unmarshal(new StringReader(xml));  
            return element.getValue();  
        } catch (Exception ex) {
            Logger.getLogger(ConversorNFe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
