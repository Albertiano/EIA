package br.net.eia.notafiscal.ide.ref;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import br.net.eia.entities.BaseEntity;
import br.net.eia.enums.UF;
import br.net.eia.notafiscal.ide.ModNFe;

@Entity
public class RefNFP extends BaseEntity{
	@Enumerated(EnumType.STRING)
	private UF uf;
	private String aamm;
	private String cnpj;
	private String cpf;
	private String ie;
	@Enumerated(EnumType.STRING)
	private ModNFe mod;
	private String serie;
	private String nnf;
	
	public UF getUf() {
		return uf;
	}
	public void setUf(UF uf) {
		this.uf = uf;
	}
	public String getAamm() {
		return aamm;
	}
	public void setAamm(String aamm) {
		this.aamm = aamm;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getIe() {
		return ie;
	}
	public void setIe(String ie) {
		this.ie = ie;
	}
	public ModNFe getMod() {
		return mod;
	}
	public void setMod(ModNFe mod) {
		this.mod = mod;
	}
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	public String getNnf() {
		return nnf;
	}
	public void setNnf(String nnf) {
		this.nnf = nnf;
	}
}
