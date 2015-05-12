package br.net.eia.pais;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

import br.net.eia.entities.BaseEntity;
import br.net.eia.entities.HATEOASEntity;
import br.net.eia.entities.Link;


@SuppressWarnings("unused")
@Entity
@XmlRootElement
public class Pais extends BaseEntity implements Serializable,HATEOASEntity {
   
	@Transient
	private Collection<Link> links = new HashSet<Link>();
   
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int cPais;
    private String xPais;

    public Pais(){
    }

    public Pais(int codigo, String nome){
        this.cPais = codigo;
        this.xPais = nome;
    }

    /**
     * @return the cPais
     */
    public int getcPais() {
        return cPais;
    }

    /**
     * @param cPais the cPais to set
     */
    public void setcPais(int cPais) {
        this.cPais = cPais;
    }

    /**
     * @return the xPais
     */
    public String getxPais() {
        return xPais;
    }

    /**
     * @param xPais the xPais to set
     */
    public void setxPais(String xPais) {
        this.xPais = xPais;
    }
 
    
    public void createStandardLinks() {
		addLink(new Link(UriBuilder.fromPath(getId().toString()).build()
				.toASCIIString(), "self"));
		/**
		 * if (getPortrait() != null) { Link portraitLink = new
		 * Link(UriBuilder.fromPath
		 * ("{id}/portrait").build(getId()).toASCIIString(), "portrait");
		 * addLink(portraitLink); setPortrait(null); }
		 **/
	}

	public HATEOASEntity addLink(Link link) {
		this.links.add(link);
		return this;
	}

	@XmlElement(name = "link", namespace = "http://www.w3.org/1999/xlink")
	public Collection<Link> getLinks() {
		return this.links;
	}

	public void setLinks(Collection<Link> links) {
		this.links = links;
	}
	@Override
	public String toString() {
		if(this.getxPais()!=null){
			return getxPais();
		}
		return super.toString();
	}
}
