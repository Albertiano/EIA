package br.net.eia.model.produto.imposto;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import br.net.eia.model.BaseEntity;
import br.net.eia.model.emitente.Emitente;

@Entity
@XmlRootElement
public class Tributacao  extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	@NotNull
	String nome;
	String descricao;
	@ManyToOne
	private Emitente emitente;	
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinColumn(name="tributacao_id")
    List<Destino> destino;

	public Emitente getEmitente() {
		return emitente;
	}

	public void setEmitente(Emitente emitente) {
		this.emitente = emitente;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Destino> getDestino() {
		return destino;
	}

	public void setDestino(List<Destino> destino) {
		this.destino = destino;
	}
	
	@Override
	public String toString() {
		
		return nome;
	}
}
