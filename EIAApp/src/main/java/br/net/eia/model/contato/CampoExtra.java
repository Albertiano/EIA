package br.net.eia.model.contato;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

import br.net.eia.model.BaseEntity;

@Entity
@XmlRootElement
public class CampoExtra  extends BaseEntity{
	
	String name;
	String value;	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
