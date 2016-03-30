package br.net.eia.model.produto.imposto;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;
import br.net.eia.model.BaseEntity;
import br.net.eia.model.emitente.Emitente;
import br.net.eia.model.municipio.UF;

@Entity
@XmlRootElement
public class Destino  extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.STRING)
	private UF estado;
	@OneToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="tributos")
	private Tributo tributos;
	@ManyToOne
	@JoinColumn(name="emitente")
	private Emitente emitente;
	
	public UF getEstado() {
		return estado;
	}
	public void setEstado(UF estado) {
		this.estado = estado;
	}

	public Tributo getTributos() {
		return tributos;
	}

	public void setTributos(Tributo tributos) {
		this.tributos = tributos;
	}	
	

	public Emitente getEmitente() {
		return emitente;
	}
	public void setEmitente(Emitente emitente) {
		this.emitente = emitente;
	}
}
