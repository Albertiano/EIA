package br.net.eia.notafiscal.transporte;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import br.net.eia.entities.BaseEntity;
import br.net.eia.enums.UF;

@Entity
public class Veiculo extends BaseEntity{
	
	private String placa;
	@Enumerated(EnumType.STRING)
	private UF uf;
	private String rntc;
	
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public UF getUf() {
		return uf;
	}
	public void setUf(UF uf) {
		this.uf = uf;
	}
	public String getRntc() {
		return rntc;
	}
	public void setRntc(String rntc) {
		this.rntc = rntc;
	}
}
