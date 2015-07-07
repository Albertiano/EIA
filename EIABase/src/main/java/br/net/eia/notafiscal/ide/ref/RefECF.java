package br.net.eia.notafiscal.ide.ref;

import javax.persistence.Entity;
import br.net.eia.entities.BaseEntity;

@Entity
public class RefECF extends BaseEntity{
     private String mod;
     private String necf;
     private String ncoo;
     
	public String getMod() {
		return mod;
	}
	public void setMod(String mod) {
		this.mod = mod;
	}
	public String getNecf() {
		return necf;
	}
	public void setNecf(String necf) {
		this.necf = necf;
	}
	public String getNcoo() {
		return ncoo;
	}
	public void setNcoo(String ncoo) {
		this.ncoo = ncoo;
	}
}
