package br.net.eia.financeiro.lancamento;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.net.eia.contato.Contato;
import br.net.eia.emitente.Emitente;
import br.net.eia.entities.BaseEntity;
import br.net.eia.entities.HATEOASEntity;
import br.net.eia.entities.Link;
import br.net.eia.financeiro.centrocustos.CentroCustos;
import br.net.eia.financeiro.conta.Conta;
import br.net.eia.financeiro.planocontas.PlanoContas;

@Entity
@XmlRootElement
public class Lancamento extends BaseEntity implements
HATEOASEntity{
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
	
	//*****************  Inicio  *******************************************************************{
	@ManyToOne
	private Emitente emitente;
	@ManyToOne	
	private Contato contato;
	@ManyToOne
	private Conta conta;
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;
	private String doc;
	private String historico;
	@Enumerated(EnumType.STRING)
	private TpLancamento tipo;
	private BigDecimal valor;
	@ManyToOne
	private PlanoContas planoContas;
	@ManyToOne
	private CentroCustos centroCustos;
	
	public Lancamento(){
		
	}

	public Emitente getEmitente() {
		return emitente;
	}

	public void setEmitente(Emitente emitente) {
		this.emitente = emitente;
	}

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		this.contato = contato;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDoc() {
		return doc;
	}

	public void setDoc(String doc) {
		this.doc = doc;
	}

	public String getHistorico() {
		return historico;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}

	public TpLancamento getTipo() {
		return tipo;
	}

	public void setTipo(TpLancamento tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public PlanoContas getPlanoContas() {
		return planoContas;
	}

	public void setPlanoContas(PlanoContas planoContas) {
		this.planoContas = planoContas;
	}

	public CentroCustos getCentroCustos() {
		return centroCustos;
	}

	public void setCentroCustos(CentroCustos centroCustos) {
		this.centroCustos = centroCustos;
	}
	
}
