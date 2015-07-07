package br.net.eia.compra;

import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.net.eia.entities.EntityCollection;

@XmlRootElement
public class Compras extends EntityCollection<Compra> {

	@Override
	@XmlElement(name="compra")
	public Collection<Compra> getEntities() {
		return super.getEntities();
	}
	
	
	/*
	 * Overriden to prevent JAXB's NPE
	 */
	@Override
	public void setEntities(Collection<Compra> entities) {
		// TODO Auto-generated method stub
		super.setEntities(entities);
	}
	
}
