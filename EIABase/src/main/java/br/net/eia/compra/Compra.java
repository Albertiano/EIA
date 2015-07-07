package br.net.eia.compra;

import java.math.BigDecimal;
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
import br.net.eia.enums.StatusOperacao;
import br.net.eia.enums.UF;
import br.net.eia.notafiscal.transporte.ModFrete;

@Entity
@XmlRootElement
public class Compra  extends BaseEntity  implements
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
	@OneToOne
	private Contato fornecedor;
	private String pedido;
	private String notaFiscal;
	private String modeloNF;
	private String serieNF;
	@Temporal(TemporalType.DATE)
	private Date emissao;
	@Temporal(TemporalType.DATE)
	private Date entrada;
	private String chaveNFe;
	private BigDecimal bcICMS;
	private BigDecimal icms;
	private BigDecimal bcIcmsST;
	private BigDecimal icmsST;
	private BigDecimal ipi;
	private BigDecimal frete;
	private BigDecimal seguro;
	private BigDecimal outros;
	private BigDecimal pis;
	private BigDecimal confins;
	private BigDecimal produtos;
	private BigDecimal desconto;
	private BigDecimal compra;
	@Enumerated(EnumType.STRING)
	private ModFrete modFrete;
	@OneToOne
	private Contato transportador;
	private String placa;
	@Enumerated(EnumType.STRING)
	private UF uf;
	private String rntc;
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinColumn
	private List<ItemCompra> itemCompra;
	@Enumerated(EnumType.STRING)
	private StatusOperacao status;
	@ManyToOne
	private Emitente emitente;
	
	public Compra(){
		
	}

	public Contato getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Contato fornecedor) {
		this.fornecedor = fornecedor;
	}

	public String getPedido() {
		return pedido;
	}

	public void setPedido(String pedido) {
		this.pedido = pedido;
	}

	public String getNotaFiscal() {
		return notaFiscal;
	}

	public void setNotaFiscal(String notaFiscal) {
		this.notaFiscal = notaFiscal;
	}

	public String getModeloNF() {
		return modeloNF;
	}

	public void setModeloNF(String modeloNF) {
		this.modeloNF = modeloNF;
	}

	public String getSerieNF() {
		return serieNF;
	}

	public void setSerieNF(String serieNF) {
		this.serieNF = serieNF;
	}

	public Date getEmissao() {
		return emissao;
	}

	public void setEmissao(Date emissao) {
		this.emissao = emissao;
	}

	public Date getEntrada() {
		return entrada;
	}

	public void setEntrada(Date entrada) {
		this.entrada = entrada;
	}

	public String getChaveNFe() {
		return chaveNFe;
	}

	public void setChaveNFe(String chaveNFe) {
		this.chaveNFe = chaveNFe;
	}

	public BigDecimal getBcICMS() {
		return bcICMS;
	}

	public void setBcICMS(BigDecimal bcICMS) {
		this.bcICMS = bcICMS;
	}

	public BigDecimal getIcms() {
		return icms;
	}

	public void setIcms(BigDecimal icms) {
		this.icms = icms;
	}

	public BigDecimal getBcIcmsST() {
		return bcIcmsST;
	}

	public void setBcIcmsST(BigDecimal bcIcmsST) {
		this.bcIcmsST = bcIcmsST;
	}

	public BigDecimal getIcmsST() {
		return icmsST;
	}

	public void setIcmsST(BigDecimal icmsST) {
		this.icmsST = icmsST;
	}

	public BigDecimal getIpi() {
		return ipi;
	}

	public void setIpi(BigDecimal ipi) {
		this.ipi = ipi;
	}

	public BigDecimal getFrete() {
		return frete;
	}

	public void setFrete(BigDecimal frete) {
		this.frete = frete;
	}

	public BigDecimal getSeguro() {
		return seguro;
	}

	public void setSeguro(BigDecimal seguro) {
		this.seguro = seguro;
	}

	public BigDecimal getOutros() {
		return outros;
	}

	public void setOutros(BigDecimal outros) {
		this.outros = outros;
	}

	public BigDecimal getPis() {
		return pis;
	}

	public void setPis(BigDecimal pis) {
		this.pis = pis;
	}

	public BigDecimal getConfins() {
		return confins;
	}

	public void setConfins(BigDecimal confins) {
		this.confins = confins;
	}

	public BigDecimal getProdutos() {
		return produtos;
	}

	public void setProdutos(BigDecimal produtos) {
		this.produtos = produtos;
	}

	public BigDecimal getDesconto() {
		return desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}

	public BigDecimal getCompra() {
		return compra;
	}

	public void setCompra(BigDecimal compra) {
		this.compra = compra;
	}

	public ModFrete getModFrete() {
		return modFrete;
	}

	public void setModFrete(ModFrete modFrete) {
		this.modFrete = modFrete;
	}

	public Contato getTransportador() {
		return transportador;
	}

	public void setTransportador(Contato transportador) {
		this.transportador = transportador;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public UF getUf() {
		return uf;
	}

	public void setUf(UF uf) {
		this.uf = uf;
	}

	public String getRntc() {
		return rntc;
	}

	public void setRntc(String rntc) {
		this.rntc = rntc;
	}

	public List<ItemCompra> getItemCompra() {
		return itemCompra;
	}

	public void setItemCompra(List<ItemCompra> itemCompra) {
		this.itemCompra = itemCompra;
	}

	public StatusOperacao getStatus() {
		return status;
	}

	public void setStatus(StatusOperacao status) {
		this.status = status;
	}

	public Emitente getEmitente() {
		return emitente;
	}

	public void setEmitente(Emitente emitente) {
		this.emitente = emitente;
	}
}