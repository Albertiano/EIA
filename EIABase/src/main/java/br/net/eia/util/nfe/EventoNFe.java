/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.eia.util.nfe;
import br.inf.portalfiscal.www.nfe.wsdl.recepcaoevento.RecepcaoEventoStub;
import br.inf.portalfiscal.www.nfe.wsdl.recepcaoevento.RecepcaoEventoStub.NfeRecepcaoEventoResult;
import br.net.eia.enums.UF;
import br.net.eia.nfe.evento.ObjectFactory;
import br.net.eia.nfe.evento.TEvento;
import br.net.eia.nfe.evento.retEnvEvento.TRetEnvEvento;
import br.net.eia.nfe.v310.consStatServ.TConsStatServ;
import br.net.eia.nfe.v310.retConsStatServ.TRetConsStatServ;
import br.net.eia.notafiscal.NotaFiscal;
import br.net.eia.notafiscal.ide.Versao;
import br.net.eia.util.ConversorDate;
import br.net.eia.util.nfe.evento.TpEvento;

import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Date;
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
public class EventoNFe {
	
	private String envio;
	public String getEnvio() {
		return envio;
	}

	public void setEnvio(String envio) {
		this.envio = envio;
	}

	public String getRetorno() {
		return retorno;
	}

	public void setRetorno(String retorno) {
		this.retorno = retorno;
	}

	private String retorno;
    
    
    private String strValueOf(TEvento evento){
    	try {JAXBContext context = JAXBContext.newInstance(TEvento.class);
			Marshaller marshaller = context.createMarshaller();
	        JAXBElement<TEvento> element = new ObjectFactory().createEvento(evento);
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
	        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

	        StringWriter sw = new StringWriter();
	        marshaller.marshal(element, sw);
	        return sw.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
        return null;
    }
    

    public TRetEnvEvento enviarEvento(TpEvento tipo,String ambiente,String nSeqEvento, String motivo,String nProt, NotaFiscal nf,
            String url) {
    	final String VERSAO = "1.00";
		TEvento evento = new TEvento();  
		evento.setVersao(VERSAO);  
		  
		String codUF = nf.getEmitente().getUf().getCUF();
		String dhEvento = ConversorDate.retornaDataHoraNFe(new Date());  
		String tpEvento = tipo.getValor();  
		if(nSeqEvento == null){
			nSeqEvento = "1";
		}
		String idEvento = "ID" + tpEvento + nf.getChave() + String.format("%02d", Integer.parseInt(nSeqEvento));
		  
		TEvento.InfEvento infEvento = new TEvento.InfEvento();  
		infEvento.setChNFe(nf.getChave());  
		infEvento.setCNPJ(nf.getEmitente().getNumDoc().replaceAll("[^0-9]", "")); 
		infEvento.setCOrgao(codUF);  
		infEvento.setTpAmb(ambiente);  
		infEvento.setDhEvento(dhEvento);  
		infEvento.setTpEvento(tpEvento);  
		infEvento.setNSeqEvento(nSeqEvento);  
		infEvento.setId(idEvento);  
		infEvento.setVerEvento(VERSAO);  
		  
		TEvento.InfEvento.DetEvento detEvento = new TEvento.InfEvento.DetEvento();  
		detEvento.setVersao(VERSAO);
		if(tipo.equals(TpEvento.CANCELAMENTO)){
			detEvento.setDescEvento("Cancelamento");
		}			
		detEvento.setNProt(nProt);
		detEvento.setXJust(motivo);
		
		infEvento.setDetEvento(detEvento);  
		evento.setInfEvento(infEvento); 
		
        StringBuilder xml = new StringBuilder();
            xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
            .append("<envEvento versao=\"1.00\" xmlns=\"http://www.portalfiscal.inf.br/nfe\">")
            .append("<idLote>")
            .append(nf.getChave().substring(29))
            .append("</idLote>")
            .append(strValueOf(evento).replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", ""))
            .append("</envEvento>");
            String xmlAssinado = null;
			try {
				xmlAssinado = new AssinaturaDigital().assinarDocumento(xml.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.envio = xmlAssinado;
           System.out.println("Envi");
           System.out.println(envio);
        try {
            OMElement ome = AXIOMUtil.stringToOM(xmlAssinado);

            RecepcaoEventoStub.NfeDadosMsg dadosMsg = new RecepcaoEventoStub.NfeDadosMsg();
            dadosMsg.setExtraElement(ome);

            RecepcaoEventoStub.NfeCabecMsg nfeCabecMsg = new RecepcaoEventoStub.NfeCabecMsg();
            /**
             * Codigo do Estado.
             */
            nfeCabecMsg.setCUF(nf.getEmitente().getUf().getCUF());

            /**
             * Versao do XML
             */
            nfeCabecMsg.setVersaoDados("1.00");
            RecepcaoEventoStub.NfeCabecMsgE nfeCabecMsgE = new RecepcaoEventoStub.NfeCabecMsgE();
            nfeCabecMsgE.setNfeCabecMsg(nfeCabecMsg);

            RecepcaoEventoStub stub = new RecepcaoEventoStub(url);
            RecepcaoEventoStub.NfeRecepcaoEventoResult nfeStatusServicoNF2Result = stub.nfeRecepcaoEvento(dadosMsg, nfeCabecMsgE);
          
            return parseXML(nfeStatusServicoNF2Result);
        } catch (RemoteException|XMLStreamException |JAXBException ex ) {
            Logger.getLogger(ConversorNFe.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
    }

    private TRetEnvEvento parseXML(
    		NfeRecepcaoEventoResult nfeStatusServicoNF2Result)
            throws JAXBException {
        try {
            String xml = nfeStatusServicoNF2Result.getExtraElement().toString();
            this.retorno = xml;
System.out.println(xml);
            JAXBContext context = JAXBContext.newInstance("br.net.eia.nfe.evento.retEnvEvento");  
            Unmarshaller unmarshaller = context.createUnmarshaller();  
            JAXBElement<TRetEnvEvento> element = (JAXBElement<TRetEnvEvento>) unmarshaller.unmarshal(new StringReader(xml));  
            return element.getValue();  
        } catch (Exception ex) {
            Logger.getLogger(ConversorNFe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
