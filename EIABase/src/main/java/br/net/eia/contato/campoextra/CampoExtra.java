package br.net.eia.contato.campoextra;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.net.eia.entities.BaseEntity;
import br.net.eia.entities.HATEOASEntity;
import br.net.eia.entities.Link;

@Entity
@XmlRootElement
public class CampoExtra  extends BaseEntity  implements
HATEOASEntity{
	
	String name;
	String value;	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	
	
	@Transient
	private Collection<Link> links = new HashSet<Link>();
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
