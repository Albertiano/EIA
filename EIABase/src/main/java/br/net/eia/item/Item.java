package br.net.eia.item;

import java.math.BigDecimal;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import br.net.eia.produto.Produto;

@Embeddable
public class Item{	
	private int nItem;
	@ManyToOne
	private Produto produto;
	@OneToOne(cascade = CascadeType.ALL)
	private DetalheFiscal detFiscal;
	private BigDecimal quantidade;
	private BigDecimal precoVenda;
	private BigDecimal pesoBruto;
	private BigDecimal pesoLiquido;
	private BigDecimal subtotal;
	private BigDecimal vFrete;
	private BigDecimal vDesc;
	private BigDecimal vSeg;
	private BigDecimal vOutro;
	private boolean noValorNota;
	private String ifAdic;

	public Item() {

	}

	public DetalheFiscal getDetFiscal() {
		return detFiscal;
	}

	public void setDetFiscal(DetalheFiscal detFiscal) {
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

	public int getnItem() {
		return nItem;
	}

	public void setnItem(int nItem) {
		this.nItem = nItem;
	}

	public BigDecimal getvFrete() {
		return vFrete;
	}

	public void setvFrete(BigDecimal vFrete) {
		this.vFrete = vFrete;
	}

	public BigDecimal getvDesc() {
		return vDesc;
	}

	public void setvDesc(BigDecimal vDesc) {
		this.vDesc = vDesc;
	}

	public BigDecimal getvSeg() {
		return vSeg;
	}

	public void setvSeg(BigDecimal vSeg) {
		this.vSeg = vSeg;
	}

	public BigDecimal getvOutro() {
		return vOutro;
	}

	public void setvOutro(BigDecimal vOutro) {
		this.vOutro = vOutro;
	}

	public String getIfAdic() {
		return ifAdic;
	}

	public void setIfAdic(String ifAdic) {
		this.ifAdic = ifAdic;
	}

}
