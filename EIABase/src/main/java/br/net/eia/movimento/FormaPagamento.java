package br.net.eia.movimento;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import br.net.eia.notafiscal.pagamento.TpPag;

@Entity
public class FormaPagamento {
	@Enumerated(EnumType.STRING)
	private TpPag tpPag;
	private BigDecimal vPag;
	
	public TpPag getTpPag() {
		return tpPag;
	}
	public void setTpPag(TpPag tpPag) {
		this.tpPag = tpPag;
	}
	public BigDecimal getvPag() {
		return vPag;
	}
	public void setvPag(BigDecimal vPag) {
		this.vPag = vPag;
	}
}
