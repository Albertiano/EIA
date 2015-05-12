package br.net.eia.produto;

import br.net.eia.entities.EntityCollection;

import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Produtos extends EntityCollection<Produto> {

	@Override
	@XmlElement(name="produto")
	public Collection<Produto> getEntities() {
		return super.getEntities();
	}
	
	
	/*
	 * Overriden to prevent JAXB's NPE
	 */
	@Override
	public void setEntities(Collection<Produto> entities) {
		// TODO Auto-generated method stub
		super.setEntities(entities);
	}
	

}
