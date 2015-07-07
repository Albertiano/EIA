package br.net.eia.notafiscal.transporte;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import br.net.eia.entities.BaseEntity;
import br.net.eia.contato.Contato;

@Entity
public class Transporte extends BaseEntity{
	@Enumerated(EnumType.STRING)
	private ModFrete modFrete;
	@OneToOne
	private Contato transporta;
	@OneToOne(cascade=CascadeType.ALL)
	private Veiculo veicTransp;
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn
	List<Veiculo> reboque;
    private String vagao;
    private String balsa;
    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinColumn
    private List<Volume> vol;
    
    public Transporte(){
    	
    }
	public ModFrete getModFrete() {
		return modFrete;
	}
	public void setModFrete(ModFrete modFrete) {
		this.modFrete = modFrete;
	}
	public Contato getTransporta() {
		return transporta;
	}
	public void setTransporta(Contato transporta) {
		this.transporta = transporta;
	}
	public Veiculo getVeicTransp() {
		return veicTransp;
	}
	public void setVeicTransp(Veiculo veicTransp) {
		this.veicTransp = veicTransp;
	}
	public List<Veiculo> getReboque() {
		return reboque;
	}
	public void setReboque(List<Veiculo> reboque) {
		this.reboque = reboque;
	}
	public String getVagao() {
		return vagao;
	}
	public void setVagao(String vagao) {
		this.vagao = vagao;
	}
	public String getBalsa() {
		return balsa;
	}
	public void setBalsa(String balsa) {
		this.balsa = balsa;
	}
	public List<Volume> getVol() {
		return vol;
	}
	public void setVol(List<Volume> vol) {
		this.vol = vol;
	}
	
	
}
