//
// Este arquivo foi gerado pela Arquitetura JavaTM para Implementação de Referência (JAXB) de Bind XML, v2.2.8-b130911.1802 
// Consulte <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas as modificações neste arquivo serão perdidas após a recompilação do esquema de origem. 
// Gerado em: 2015.03.31 às 09:25:53 PM GMT-03:00 
//


package br.net.eia.nfe.v310.retConsStatServ;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the br.net.eia.nfe.v310.retConsStatServ package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _RetConsStatServ_QNAME = new QName("http://www.portalfiscal.inf.br/nfe", "retConsStatServ");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: br.net.eia.nfe.v310.retConsStatServ
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TRetConsStatServ }
     * 
     */
    public TRetConsStatServ createTRetConsStatServ() {
        return new TRetConsStatServ();
    }

    /**
     * Create an instance of {@link TConsStatServ }
     * 
     */
    public TConsStatServ createTConsStatServ() {
        return new TConsStatServ();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TRetConsStatServ }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.portalfiscal.inf.br/nfe", name = "retConsStatServ")
    public JAXBElement<TRetConsStatServ> createRetConsStatServ(TRetConsStatServ value) {
        return new JAXBElement<TRetConsStatServ>(_RetConsStatServ_QNAME, TRetConsStatServ.class, null, value);
    }

}
