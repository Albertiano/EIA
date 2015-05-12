package br.net.eia.pais;

import br.net.eia.entities.EntityCollection;

import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Paises extends EntityCollection<Pais> {

	@Override
	@XmlElement(name="pais")
	public Collection<Pais> getEntities() {
		return super.getEntities();
	}
	
	
	/*
	 * Overriden to prevent JAXB's NPE
	 */
	@Override
	public void setEntities(Collection<Pais> entities) {
		// TODO Auto-generated method stub
		super.setEntities(entities);
	}

}
