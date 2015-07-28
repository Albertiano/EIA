package br.net.eia.util.recibo;

import java.math.BigDecimal;
import java.util.Date;
import br.net.eia.contato.Contato;
import br.net.eia.emitente.Emitente;

public class Recibo {
	private Emitente emitente;
	private Contato contato;
	private Date data;
	private BigDecimal valor;
	private String referencia;
	
	public Emitente getEmitente() {
		return emitente;
	}
	public void setEmitente(Emitente emitente) {
		this.emitente = emitente;
	}
	public Contato getContato() {
		return contato;
	}
	public void setContato(Contato contato) {
		this.contato = contato;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
}
