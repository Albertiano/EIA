package br.net.eia.notafiscal.adicionais;

import javax.persistence.Entity;
import br.net.eia.entities.BaseEntity;

@Entity
public class AutXML extends BaseEntity{
	
	private String cnpj;
    private String cpf;
    
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
}
