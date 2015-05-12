package br.net.eia.notafiscal.transporte;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import br.net.eia.entities.BaseEntity;

@Entity
public class Volume extends BaseEntity{
	
	private BigDecimal qVol;
	private String esp;
	private String marca;
	private String nVol;
	private BigDecimal pesoL;
    private BigDecimal pesoB;
    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinColumn
    private List<Lacre> lacres;
    
	public BigDecimal getqVol() {
		return qVol;
	}
	public void setqVol(BigDecimal qVol) {
		this.qVol = qVol;
	}
	public String getEsp() {
		return esp;
	}
	public void setEsp(String esp) {
		this.esp = esp;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getnVol() {
		return nVol;
	}
	public void setnVol(String nVol) {
		this.nVol = nVol;
	}
	public BigDecimal getPesoL() {
		return pesoL;
	}
	public void setPesoL(BigDecimal pesoL) {
		this.pesoL = pesoL;
	}
	public BigDecimal getPesoB() {
		return pesoB;
	}
	public void setPesoB(BigDecimal pesoB) {
		this.pesoB = pesoB;
	}
	public List<Lacre> getLacres() {
		return lacres;
	}
	public void setLacres(List<Lacre> lacres) {
		this.lacres = lacres;
	}
}
