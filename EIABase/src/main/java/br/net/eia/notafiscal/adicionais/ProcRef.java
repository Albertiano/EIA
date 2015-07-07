package br.net.eia.notafiscal.adicionais;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import br.net.eia.entities.BaseEntity;

@Entity
public class ProcRef extends BaseEntity{
	private String nProc;
	@Enumerated(EnumType.STRING)
	private IndProc indProc;
	
	public String getnProc() {
		return nProc;
	}
	public void setnProc(String nProc) {
		this.nProc = nProc;
	}
	public IndProc getIndProc() {
		return indProc;
	}
	public void setIndProc(IndProc indProc) {
		this.indProc = indProc;
	}
	
}
