package br.net.eia.notafiscal.total;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import br.net.eia.entities.BaseEntity;

@Entity
public class Total extends BaseEntity{
	private BigDecimal vbc;
	private BigDecimal vicms;
	private BigDecimal vbcst;
	private BigDecimal vst;
	private BigDecimal vProd;
	private BigDecimal vFrete;
	private BigDecimal vSeg;
	private BigDecimal vDesc;
	private BigDecimal vii;
	private BigDecimal vipi;
	private BigDecimal vpis;
	private BigDecimal vcofins;
	private BigDecimal vOutro;
	private BigDecimal vnf;
	private BigDecimal vTotTrib;
	
	public BigDecimal getVbc() {
		return vbc;
	}
	public void setVbc(BigDecimal vbc) {
		this.vbc = vbc;
	}
	public BigDecimal getVicms() {
		return vicms;
	}
	public void setVicms(BigDecimal vicms) {
		this.vicms = vicms;
	}
	public BigDecimal getVbcst() {
		return vbcst;
	}
	public void setVbcst(BigDecimal vbcst) {
		this.vbcst = vbcst;
	}
	public BigDecimal getVst() {
		return vst;
	}
	public void setVst(BigDecimal vst) {
		this.vst = vst;
	}
	public BigDecimal getvProd() {
		return vProd;
	}
	public void setvProd(BigDecimal vProd) {
		this.vProd = vProd;
	}
	public BigDecimal getvFrete() {
		return vFrete;
	}
	public void setvFrete(BigDecimal vFrete) {
		this.vFrete = vFrete;
	}
	public BigDecimal getvSeg() {
		return vSeg;
	}
	public void setvSeg(BigDecimal vSeg) {
		this.vSeg = vSeg;
	}
	public BigDecimal getvDesc() {
		return vDesc;
	}
	public void setvDesc(BigDecimal vDesc) {
		this.vDesc = vDesc;
	}
	public BigDecimal getVii() {
		return vii;
	}
	public void setVii(BigDecimal vii) {
		this.vii = vii;
	}
	public BigDecimal getVipi() {
		return vipi;
	}
	public void setVipi(BigDecimal vipi) {
		this.vipi = vipi;
	}
	public BigDecimal getVpis() {
		return vpis;
	}
	public void setVpis(BigDecimal vpis) {
		this.vpis = vpis;
	}
	public BigDecimal getVcofins() {
		return vcofins;
	}
	public void setVcofins(BigDecimal vcofins) {
		this.vcofins = vcofins;
	}
	public BigDecimal getvOutro() {
		return vOutro;
	}
	public void setvOutro(BigDecimal vOutro) {
		this.vOutro = vOutro;
	}
	public BigDecimal getVnf() {
		return vnf;
	}
	public void setVnf(BigDecimal vnf) {
		this.vnf = vnf;
	}
	public BigDecimal getvTotTrib() {
		return vTotTrib;
	}
	public void setvTotTrib(BigDecimal vTotTrib) {
		this.vTotTrib = vTotTrib;
	}
}
