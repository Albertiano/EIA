package br.net.eia.notafiscal.adicionais;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import br.net.eia.entities.BaseEntity;

@Entity
public class InfAdicionais extends BaseEntity {
	private String infAdFisco;
    private String infCpl;
    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinColumn
    private List<ProcRef> procRef;
    
	public String getInfAdFisco() {
		return infAdFisco;
	}
	public void setInfAdFisco(String infAdFisco) {
		this.infAdFisco = infAdFisco;
	}
	public String getInfCpl() {
		return infCpl;
	}
	public void setInfCpl(String infCpl) {
		this.infCpl = infCpl;
	}
	public List<ProcRef> getProcRef() {
		return procRef;
	}
	public void setProcRef(List<ProcRef> procRef) {
		this.procRef = procRef;
	}
}
