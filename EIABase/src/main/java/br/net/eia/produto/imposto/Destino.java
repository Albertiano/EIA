package br.net.eia.produto.imposto;

import java.util.Collection;
import java.util.HashSet;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import br.net.eia.emitente.Emitente;
import br.net.eia.entities.BaseEntity;
import br.net.eia.entities.HATEOASEntity;
import br.net.eia.entities.Link;
import br.net.eia.enums.UF;

@XmlRootElement
@Entity
public class Destino  extends BaseEntity  implements
HATEOASEntity {

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


	@Enumerated(EnumType.STRING)
	UF estado;
	@OneToOne(cascade=CascadeType.ALL)
	Tributo tributos;
	@ManyToOne
	private Emitente emitente;
	
	public UF getEstado() {
		return estado;
	}
	public void setEstado(UF estado) {
		this.estado = estado;
	}

	public Tributo getTributos() {
		return tributos;
	}

	public void setTributos(Tributo tributos) {
		this.tributos = tributos;
	}	
	

	public Emitente getEmitente() {
		return emitente;
	}
	public void setEmitente(Emitente emitente) {
		this.emitente = emitente;
	}
}
