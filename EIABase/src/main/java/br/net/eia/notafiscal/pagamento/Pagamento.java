package br.net.eia.notafiscal.pagamento;

import java.math.BigDecimal;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import br.net.eia.entities.BaseEntity;

@Entity
public class Pagamento extends BaseEntity{
	@Enumerated(EnumType.STRING)
	private TpPag tpPag;
	private BigDecimal vPag;
	@OneToOne(cascade=CascadeType.ALL)
	private Card card;
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
	public Card getCard() {
		return card;
	}
	public void setCard(Card card) {
		this.card = card;
	}

}
