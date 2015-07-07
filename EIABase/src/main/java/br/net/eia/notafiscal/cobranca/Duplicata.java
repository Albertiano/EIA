package br.net.eia.notafiscal.cobranca;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.net.eia.entities.BaseEntity;

@Entity
public class Duplicata extends BaseEntity{
	private String numero;
	@Temporal(TemporalType.DATE)
    private Date vencimento;
    private BigDecimal valor;
    
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public Date getVencimento() {
		return vencimento;
	}
	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
    
}
