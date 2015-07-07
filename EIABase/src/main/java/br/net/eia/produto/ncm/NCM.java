package br.net.eia.produto.ncm;

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
public class NCM  extends BaseEntity  implements
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
	
    private String codigo;
    private String ex;
    private String tipo;
    private String descricao;
    private double aliqNacionalFederal;
    private double aliqImportadosFederal;
    private double aliqEstadual;
    private double aliqMunicipal;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getEx() {
		return ex;
	}

	public void setEx(String ex) {
		this.ex = ex;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getAliqNacionalFederal() {
		return aliqNacionalFederal;
	}

	public void setAliqNacionalFederal(double aliqNacionalFederal) {
		this.aliqNacionalFederal = aliqNacionalFederal;
	}

	public double getAliqImportadosFederal() {
		return aliqImportadosFederal;
	}

	public void setAliqImportadosFederal(double aliqImportadosFederal) {
		this.aliqImportadosFederal = aliqImportadosFederal;
	}

	public double getAliqEstadual() {
		return aliqEstadual;
	}

	public void setAliqEstadual(double aliqEstadual) {
		this.aliqEstadual = aliqEstadual;
	}

	public double getAliqMunicipal() {
		return aliqMunicipal;
	}

	public void setAliqMunicipal(double aliqMunicipal) {
		this.aliqMunicipal = aliqMunicipal;
	}
    
}
