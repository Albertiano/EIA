package br.net.eia.util.nfe;

import java.io.StringReader;
import java.io.StringWriter;
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
import br.inf.portalfiscal.www.nfe.wsdl.nfeconsulta2.NfeConsulta2Stub;
import br.inf.portalfiscal.www.nfe.wsdl.nfeconsulta2.NfeConsulta2Stub.NfeConsultaNF2Result;
import br.net.eia.enums.UF;
import br.net.eia.nfe.v310.consSitNFe.ObjectFactory;
import br.net.eia.nfe.v310.consSitNFe.TConsSitNFe;
import br.net.eia.nfe.v310.retConsSitNFe.TRetConsSitNFe;

public class ConsultarNFe {

    public TRetConsSitNFe consultar(UF uf, String ambiente,
            String url, String chave) {
        try {
            OMElement ome = AXIOMUtil.stringToOM(createXML(ambiente, chave).toString());

            NfeConsulta2Stub.NfeDadosMsg dadosMsg = new NfeConsulta2Stub.NfeDadosMsg();
            dadosMsg.setExtraElement(ome);

            NfeConsulta2Stub.NfeCabecMsg nfeCabecMsg = new NfeConsulta2Stub.NfeCabecMsg();
            
            nfeCabecMsg.setCUF(uf.getCUF());

            nfeCabecMsg.setVersaoDados("3.10");
            NfeConsulta2Stub.NfeCabecMsgE nfeCabecMsgE = new NfeConsulta2Stub.NfeCabecMsgE();
            nfeCabecMsgE.setNfeCabecMsg(nfeCabecMsg);

            NfeConsulta2Stub stub = new NfeConsulta2Stub(url);
            NfeConsulta2Stub.NfeConsultaNF2Result nfeStatusServicoNF2Result = stub.nfeConsultaNF2(dadosMsg, nfeCabecMsgE);
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
       
    private String createXML(String ambiente, String chave){        
        	TConsSitNFe consStatServ = new TConsSitNFe();
            consStatServ.setTpAmb(ambiente);
            consStatServ.setVersao("3.10");
            consStatServ.setXServ("CONSULTAR");
            consStatServ.setChNFe(chave);
            
            return strValueOf(consStatServ);        
    }
    
    private String strValueOf(TConsSitNFe consStatServ){
       	try {
			JAXBContext context = JAXBContext.newInstance(TConsSitNFe.class);
			 Marshaller marshaller = context.createMarshaller();
		        JAXBElement<TConsSitNFe> element = new ObjectFactory().createConsSitNFe(consStatServ);
		        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
		        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

		        StringWriter sw = new StringWriter();
		        marshaller.marshal(element, sw);
		        return sw.toString();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       return null;
    }
 
    private TRetConsSitNFe parseXML(
            NfeConsultaNF2Result nfeStatusServicoNF2Result)
            throws JAXBException {
        try {
            String xml = nfeStatusServicoNF2Result.getExtraElement().toString();

            JAXBContext context = JAXBContext.newInstance("br.net.eia.nfe.v310.retConsSitNFe");  
            Unmarshaller unmarshaller = context.createUnmarshaller();  
            JAXBElement<TRetConsSitNFe> element = (JAXBElement<TRetConsSitNFe>) unmarshaller  
                    .unmarshal(new StringReader(xml));  
            return element.getValue();  
        } catch (Exception ex) {
            Logger.getLogger(ConversorNFe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
