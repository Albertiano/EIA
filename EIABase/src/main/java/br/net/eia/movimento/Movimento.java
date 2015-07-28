package br.net.eia.movimento;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
import br.net.eia.enums.UF;
import br.net.eia.municipio.Municipio;
import br.net.eia.notafiscal.adicionais.AutXML;
import br.net.eia.notafiscal.adicionais.InfAdicionais;
import br.net.eia.notafiscal.adicionais.Local;
import br.net.eia.notafiscal.adicionais.SitNFe;
import br.net.eia.notafiscal.cobranca.Cobranca;
import br.net.eia.notafiscal.ide.FinNFe;
import br.net.eia.notafiscal.ide.IdDest;
import br.net.eia.notafiscal.ide.IndFinal;
import br.net.eia.notafiscal.ide.IndPag;
import br.net.eia.notafiscal.ide.IndPres;
import br.net.eia.notafiscal.ide.ModNFe;
import br.net.eia.notafiscal.ide.ProcEmi;
import br.net.eia.notafiscal.ide.TpAmb;
import br.net.eia.notafiscal.ide.TpEmis;
import br.net.eia.notafiscal.ide.TpImp;
import br.net.eia.notafiscal.ide.TpNF;
import br.net.eia.notafiscal.ide.Versao;
import br.net.eia.notafiscal.ide.ref.NFref;
import br.net.eia.notafiscal.item.ItemNotaFiscal;
import br.net.eia.notafiscal.pagamento.Pagamento;
import br.net.eia.notafiscal.total.Total;
import br.net.eia.notafiscal.transporte.Transporte;

@Entity
@XmlRootElement
public class Movimento extends BaseEntity implements HATEOASEntity {

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
	
	//*****************  Inicio  *******************************************************************
	
	@Enumerated(EnumType.STRING)
	private Situacao situacao;
	@Enumerated(EnumType.STRING)
	private TpMovimento tpMovimento;
	private Integer serie;
	private Long numero;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dhEmi;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dhSaiEnt;
	@ManyToOne
	private Emitente emitente;
	@ManyToOne	
	private Contato dest;
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn
	private List<ItemMovimento> itens;
	@OneToOne(cascade=CascadeType.ALL)
	private Total total;
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private Transporte transp;
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private Cobranca cobr;	
	@Enumerated(EnumType.STRING)
	private IndPag indPag;
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn
	private FormaPagamento pag;	
	private String obs;
	@Enumerated(EnumType.STRING)
	private IndPres indPres;
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn
	private List<NFref> NFRef;
	
	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public TpMovimento getTpMovimento() {
		return tpMovimento;
	}

	public void setTpMovimento(TpMovimento tpMovimento) {
		this.tpMovimento = tpMovimento;
	}

	public Integer getSerie() {
		return serie;
	}

	public void setSerie(Integer serie) {
		this.serie = serie;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public Date getDhEmi() {
		return dhEmi;
	}

	public void setDhEmi(Date dhEmi) {
		this.dhEmi = dhEmi;
	}

	public Date getDhSaiEnt() {
		return dhSaiEnt;
	}

	public void setDhSaiEnt(Date dhSaiEnt) {
		this.dhSaiEnt = dhSaiEnt;
	}

	public Emitente getEmitente() {
		return emitente;
	}

	public void setEmitente(Emitente emitente) {
		this.emitente = emitente;
	}

	public Contato getDest() {
		return dest;
	}

	public void setDest(Contato dest) {
		this.dest = dest;
	}

	public List<ItemMovimento> getItens() {
		return itens;
	}

	public void setItens(List<ItemMovimento> itens) {
		this.itens = itens;
	}

	public Total getTotal() {
		return total;
	}

	public void setTotal(Total total) {
		this.total = total;
	}

	public Transporte getTransp() {
		return transp;
	}

	public void setTransp(Transporte transp) {
		this.transp = transp;
	}

	public Cobranca getCobr() {
		return cobr;
	}

	public void setCobr(Cobranca cobr) {
		this.cobr = cobr;
	}

	public IndPag getIndPag() {
		return indPag;
	}

	public void setIndPag(IndPag indPag) {
		this.indPag = indPag;
	}

	public FormaPagamento getPag() {
		return pag;
	}

	public void setPag(FormaPagamento pag) {
		this.pag = pag;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public IndPres getIndPres() {
		return indPres;
	}

	public void setIndPres(IndPres indPres) {
		this.indPres = indPres;
	}

	public List<NFref> getNFRef() {
		return NFRef;
	}

	public void setNFRef(List<NFref> nFRef) {
		NFRef = nFRef;
	}}
