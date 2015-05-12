package br.net.eia.compra;

import java.math.BigDecimal;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import br.net.eia.emitente.Emitente;
import br.net.eia.entities.BaseEntity;
import br.net.eia.produto.Produto;

@XmlRootElement
@Entity
public class ItemCompra extends BaseEntity{
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Produto produto;
	@OneToOne(cascade = CascadeType.ALL)
	private DetalheFiscalCompra detFiscal;
	private String cfop;
	private String ncm;
	private BigDecimal quantidade;
	private BigDecimal precoVenda;
	private BigDecimal pesoBruto;
	private BigDecimal pesoLiquido;
	private BigDecimal subtotal;
	private boolean noValorNota;
	@ManyToOne
	private Emitente emitente;

	@Transient
	private BigDecimal bcIcms;
	@Transient
	private BigDecimal vIcms;
	@Transient
	private BigDecimal vIpi;

	public ItemCompra() {

	}

	public BigDecimal getBcIcms() {
		if (detFiscal != null) {
			return detFiscal.getIcms().getvBCICMS();
		}
		return bcIcms;
	}

	public BigDecimal getVIcms() {
		if (detFiscal != null) {
			return detFiscal.getIcms().getvICMS();
		}
		return vIcms;
	}

	public BigDecimal getVIpi() {
		if (detFiscal != null) {
			return detFiscal.getIpi().getvIPI();
		}
		return vIpi;
	}

	public DetalheFiscalCompra getDetFiscal() {
		return detFiscal;
	}

	public void setDetFiscal(DetalheFiscalCompra detFiscal) {
		this.detFiscal = detFiscal;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public boolean isNoValorNota() {
		return noValorNota;
	}

	public void setNoValorNota(boolean noValorNota) {
		this.noValorNota = noValorNota;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public String getCfop() {
		return cfop;
	}

	public void setCfop(String cfop) {
		this.cfop = cfop;
	}

	public String getNcm() {
		return ncm;
	}

	public void setNcm(String ncm) {
		this.ncm = ncm;
	}

	public BigDecimal getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(BigDecimal precoVenda) {
		this.precoVenda = precoVenda;
	}

	public BigDecimal getPesoBruto() {
		return pesoBruto;
	}

	public void setPesoBruto(BigDecimal pesoBruto) {
		this.pesoBruto = pesoBruto;
	}

	public BigDecimal getPesoLiquido() {
		return pesoLiquido;
	}

	public void setPesoLiquido(BigDecimal pesoLiquido) {
		this.pesoLiquido = pesoLiquido;
	}

	public Emitente getEmitente() {
		return emitente;
	}

	public void setEmitente(Emitente emitente) {
		this.emitente = emitente;
	}

	public BigDecimal getvIcms() {
		return vIcms;
	}

	public void setvIcms(BigDecimal vIcms) {
		this.vIcms = vIcms;
	}

	public BigDecimal getvIpi() {
		return vIpi;
	}

	public void setvIpi(BigDecimal vIpi) {
		this.vIpi = vIpi;
	}

	public void setBcIcms(BigDecimal bcIcms) {
		this.bcIcms = bcIcms;
	}

}
