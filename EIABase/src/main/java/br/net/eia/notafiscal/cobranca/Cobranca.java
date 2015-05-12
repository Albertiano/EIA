package br.net.eia.notafiscal.cobranca;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import br.net.eia.entities.BaseEntity;

@Entity
public class Cobranca extends BaseEntity{
	@OneToOne(cascade=CascadeType.ALL)
	private Fatura fat;
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn
	private List<Duplicata> dup;
	
	public Fatura getFat() {
		return fat;
	}
	public void setFat(Fatura fat) {
		this.fat = fat;
	}
	public List<Duplicata> getDup() {
		return dup;
	}
	public void setDup(List<Duplicata> dup) {
		this.dup = dup;
	}
}
