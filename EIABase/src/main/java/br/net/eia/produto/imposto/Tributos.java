package br.net.eia.produto.imposto;

import br.net.eia.entities.EntityCollection;

import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Tributos extends EntityCollection<Tributo> {

	@Override
	@XmlElement(name="tributo")
	public Collection<Tributo> getEntities() {
		return super.getEntities();
	}
	
	
	/*
	 * Overriden to prevent JAXB's NPE
	 */
	@Override
	public void setEntities(Collection<Tributo> entities) {
		// TODO Auto-generated method stub
		super.setEntities(entities);
	}
	

}
