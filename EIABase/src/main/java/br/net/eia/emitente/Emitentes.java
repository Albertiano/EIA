package br.net.eia.emitente;

import br.net.eia.entities.EntityCollection;

import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Emitentes extends EntityCollection<Emitente> {

	@Override
	@XmlElement(name="emitente")
	public Collection<Emitente> getEntities() {
		return super.getEntities();
	}
	
	
	/*
	 * Overriden to prevent JAXB's NPE
	 */
	@Override
	public void setEntities(Collection<Emitente> entities) {
		// TODO Auto-generated method stub
		super.setEntities(entities);
	}
	

}
