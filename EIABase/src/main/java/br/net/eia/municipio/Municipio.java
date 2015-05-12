package br.net.eia.municipio;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.net.eia.entities.BaseEntity;
import br.net.eia.entities.HATEOASEntity;
import br.net.eia.entities.Link;
import br.net.eia.enums.UF;


@Entity
@XmlRootElement
public class Municipio extends BaseEntity implements Serializable,HATEOASEntity{
   
	private static final long serialVersionUID = 229805288694101112L;

	@Transient
	private Collection<Link> links = new HashSet<Link>();
    
    private int cMun;
    private String xMun;
    @Enumerated(EnumType.STRING)
    private UF UF;

    public Municipio(){
    }

    public int getcMun() {
        return cMun;
    }

    public void setcMun(int cMun) {
        this.cMun = cMun;
    }

    public String getxMun() {
        return xMun;
    }

    public void setxMun(String xMun) {
        this.xMun = xMun;
    }

    public UF getUF() {
        return UF;
    }

    public void setUF(UF uf) {
        this.UF = uf;
    }

    @Override
    public String toString(){
        return this.xMun;
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
}
