package br.net.eia.notafiscal;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
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
public class NotaFiscal extends BaseEntity implements HATEOASEntity {

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
	private SitNFe sitNfe;
	@Enumerated(EnumType.STRING)
	private Versao versao;
	private String chave;
	@Enumerated(EnumType.STRING)
	private UF uf;
	private String cNF;
	private String natOp;
	@Enumerated(EnumType.STRING)
	private IndPag indPag;
	@Enumerated(EnumType.STRING)
	private ModNFe mod;
	private Integer serie;
	private Integer numero;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dhEmi;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dhSaiEnt;
	@Enumerated(EnumType.STRING)
	private TpNF tpNF;
	@Enumerated(EnumType.STRING)
	private IdDest  idDest;
	@OneToOne
	private Municipio munFG;
	@Enumerated(EnumType.STRING)
	private TpImp tpImp;
	@Enumerated(EnumType.STRING)
	private TpEmis tpEmis;
	/*
	 * Dígito Verificador da Chave de Acesso da NF-e
	 */
	private int cDV;
	@Enumerated(EnumType.STRING)
	private TpAmb tpAmb;
	@Enumerated(EnumType.STRING)
	private FinNFe finNFe;
	@Enumerated(EnumType.STRING)
	private IndFinal indFinal;
	@Enumerated(EnumType.STRING)
	private IndPres indPres;
	@Enumerated(EnumType.STRING)
	private ProcEmi procEmi;
	@Enumerated(EnumType.STRING)
	private Versao verProc;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dhCont;	
	private String xJust;
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn
	private List<NFref> NFRef;
	@ManyToOne
	private Emitente emitente;
	@ManyToOne	
	private Contato dest;
	@OneToOne(cascade=CascadeType.ALL)
	private Local retirada;
	@OneToOne(cascade=CascadeType.ALL)
	private Local entrega;
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn
	private List<AutXML> autXML;
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn
	private List<ItemNotaFiscal> itens;
	@OneToOne(cascade=CascadeType.ALL)
	private Total total;
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private Transporte transp;
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private Cobranca cobr;	
	@OneToOne(cascade=CascadeType.ALL)
	private Pagamento pag;
	@OneToOne(cascade=CascadeType.ALL)
	private InfAdicionais infAdic;
	
	public SitNFe getSitNfe() {
		return sitNfe;
	}

	public void setSitNfe(SitNFe sitNfe) {
		this.sitNfe = sitNfe;
	}

	public Versao getVersao() {
		return versao;
	}

	public void setVersao(Versao versao) {
		this.versao = versao;
	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public UF getUf() {
		return uf;
	}

	public void setUf(UF uf) {
		this.uf = uf;
	}

	public String getcNF() {
		return cNF;
	}

	public void setcNF(String cNF) {
		this.cNF = cNF;
	}

	public String getNatOp() {
		return natOp;
	}

	public void setNatOp(String natOp) {
		this.natOp = natOp;
	}

	public IndPag getIndPag() {
		return indPag;
	}

	public void setIndPag(IndPag indPag) {
		this.indPag = indPag;
	}

	public ModNFe getMod() {
		return mod;
	}

	public void setMod(ModNFe mod) {
		this.mod = mod;
	}

	public Integer getSerie() {
		return serie;
	}

	public void setSerie(Integer serie) {
		this.serie = serie;
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

	public TpNF getTpNF() {
		return tpNF;
	}

	public void setTpNF(TpNF tpNF) {
		this.tpNF = tpNF;
	}

	public IdDest getIdDest() {
		return idDest;
	}

	public void setIdDest(IdDest idDest) {
		this.idDest = idDest;
	}

	public Municipio getMunFG() {
		return munFG;
	}

	public void setMunFG(Municipio munFG) {
		this.munFG = munFG;
	}

	public TpImp getTpImp() {
		return tpImp;
	}

	public void setTpImp(TpImp tpImp) {
		this.tpImp = tpImp;
	}

	public TpEmis getTpEmis() {
		return tpEmis;
	}

	public void setTpEmis(TpEmis tpEmis) {
		this.tpEmis = tpEmis;
	}

	public int getcDV() {
		return cDV;
	}

	public void setcDV(int cDV) {
		this.cDV = cDV;
	}

	public TpAmb getTpAmb() {
		return tpAmb;
	}

	public void setTpAmb(TpAmb tpAmb) {
		this.tpAmb = tpAmb;
	}

	public FinNFe getFinNFe() {
		return finNFe;
	}

	public void setFinNFe(FinNFe finNFe) {
		this.finNFe = finNFe;
	}

	public IndFinal getIndFinal() {
		return indFinal;
	}

	public void setIndFinal(IndFinal indFinal) {
		this.indFinal = indFinal;
	}

	public IndPres getIndPres() {
		return indPres;
	}

	public void setIndPres(IndPres indPres) {
		this.indPres = indPres;
	}

	public ProcEmi getProcEmi() {
		return procEmi;
	}

	public void setProcEmi(ProcEmi procEmi) {
		this.procEmi = procEmi;
	}

	public Versao getVerProc() {
		return verProc;
	}

	public void setVerProc(Versao verProc) {
		this.verProc = verProc;
	}

	public Date getDhCont() {
		return dhCont;
	}

	public void setDhCont(Date dhCont) {
		this.dhCont = dhCont;
	}

	public String getxJust() {
		return xJust;
	}

	public void setxJust(String xJust) {
		this.xJust = xJust;
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

	public Local getRetirada() {
		return retirada;
	}

	public void setRetirada(Local retirada) {
		this.retirada = retirada;
	}

	public Local getEntrega() {
		return entrega;
	}

	public void setEntrega(Local entrega) {
		this.entrega = entrega;
	}

	public List<AutXML> getAutXML() {
		return autXML;
	}

	public void setAutXML(List<AutXML> autXML) {
		this.autXML = autXML;
	}

	public List<ItemNotaFiscal> getItens() {
		return itens;
	}

	public void setItens(List<ItemNotaFiscal> itens) {
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

	public Pagamento getPag() {
		return pag;
	}

	public void setPag(Pagamento pag) {
		this.pag = pag;
	}

	public InfAdicionais getInfAdic() {
		return infAdic;
	}

	public void setInfAdic(InfAdicionais infAdic) {
		this.infAdic = infAdic;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}	

	public List<NFref> getNFRef() {
		return NFRef;
	}

	public void setNFRef(List<NFref> nFRef) {
		NFRef = nFRef;
	}
	
}
