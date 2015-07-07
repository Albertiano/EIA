package br.net.eia.notafiscal.cobranca;

import java.math.BigDecimal;

import javax.persistence.Entity;

import br.net.eia.entities.BaseEntity;

@Entity
public class Fatura extends BaseEntity{
	private String nFat;
	private BigDecimal vOrig;
	private BigDecimal vDesc;
	private BigDecimal vLiq;
	
	public String getnFat() {
		return nFat;
	}
	public void setnFat(String nFat) {
		this.nFat = nFat;
	}
	public BigDecimal getvOrig() {
		return vOrig;
	}
	public void setvOrig(BigDecimal vOrig) {
		this.vOrig = vOrig;
	}
	public BigDecimal getvDesc() {
		return vDesc;
	}
	public void setvDesc(BigDecimal vDesc) {
		this.vDesc = vDesc;
	}
	public BigDecimal getvLiq() {
		return vLiq;
	}
	public void setvLiq(BigDecimal vLiq) {
		this.vLiq = vLiq;
	}
	
}
