package br.net.eia.produto.unidade;

import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.net.eia.entities.EntityCollection;

@XmlRootElement
public class Unidades extends EntityCollection<Unidade> {

	@Override
	@XmlElement(name="unidade")
	public Collection<Unidade> getEntities() {
		return super.getEntities();
	}
	
	
	/*
	 * Overriden to prevent JAXB's NPE
	 */
	@Override
	public void setEntities(Collection<Unidade> entities) {
		// TODO Auto-generated method stub
		super.setEntities(entities);
	}
	
}
