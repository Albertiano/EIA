package br.net.eia.notafiscal.ide.ref;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import br.net.eia.entities.BaseEntity;

@Entity
public class NFref extends BaseEntity{
	private String refNFe;
	@OneToOne
	private RefNF refNF;
	@OneToOne
	private RefNFP refNFP;
	private String refCTe;
	@OneToOne
	private RefECF refECF;
	
	public String getRefNFe() {
		return refNFe;
	}
	public void setRefNFe(String refNFe) {
		this.refNFe = refNFe;
	}
	public RefNF getRefNF() {
		return refNF;
	}
	public void setRefNF(RefNF refNF) {
		this.refNF = refNF;
	}
	public RefNFP getRefNFP() {
		return refNFP;
	}
	public void setRefNFP(RefNFP refNFP) {
		this.refNFP = refNFP;
	}
	public String getRefCTe() {
		return refCTe;
	}
	public void setRefCTe(String refCTe) {
		this.refCTe = refCTe;
	}
	public RefECF getRefECF() {
		return refECF;
	}
	public void setRefECF(RefECF refECF) {
		this.refECF = refECF;
	}
}
