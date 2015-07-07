package br.net.eia.notafiscal.pagamento;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import br.net.eia.entities.BaseEntity;

@Entity
public class Card extends BaseEntity{
	private String cnpj;
	@Enumerated(EnumType.STRING)
	private TpBand tBand;
	private String cAut;
	
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public TpBand gettBand() {
		return tBand;
	}
	public void settBand(TpBand tBand) {
		this.tBand = tBand;
	}
	public String getcAut() {
		return cAut;
	}
	public void setcAut(String cAut) {
		this.cAut = cAut;
	}
}
