package br.net.eia.model.contato;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

import br.net.eia.model.BaseEntity;

@Entity
@XmlRootElement
public class CampoExtra  extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String value;	
	
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
