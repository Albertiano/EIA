package br.net.eia.notafiscal.transporte;

import javax.persistence.Entity;
import br.net.eia.entities.BaseEntity;

@Entity
public class Lacre extends BaseEntity{
	
	private String nLacre;

	public String getnLacre() {
		return nLacre;
	}

	public void setnLacre(String nLacre) {
		this.nLacre = nLacre;
	}
}
