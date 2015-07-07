package br.net.eia.produto.imposto;

import br.net.eia.entities.EntityCollection;

import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Tributacoes extends EntityCollection<Tributacao> {

	@Override
	@XmlElement(name="tributacao")
	public Collection<Tributacao> getEntities() {
		return super.getEntities();
	}
	
	
	/*
	 * Overriden to prevent JAXB's NPE
	 */
	@Override
	public void setEntities(Collection<Tributacao> entities) {
		// TODO Auto-generated method stub
		super.setEntities(entities);
	}
	

}
