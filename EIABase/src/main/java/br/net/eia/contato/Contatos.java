package br.net.eia.contato;

import br.net.eia.entities.EntityCollection;

import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Contatos extends EntityCollection<Contato> {

	@Override
	@XmlElement(name="contato")
	public Collection<Contato> getEntities() {
		return super.getEntities();
	}
	
	@Override
	public void setEntities(Collection<Contato> entities) {
		// TODO Auto-generated method stub
		super.setEntities(entities);
	}
	

}
