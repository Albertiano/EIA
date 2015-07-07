package br.net.eia.municipio;

import br.net.eia.entities.EntityCollection;

import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Municipios extends EntityCollection<Municipio> {

	@Override
	@XmlElement(name="municipios")
	public Collection<Municipio> getEntities() {
		return super.getEntities();
	}
	
	
	/*
	 * Overriden to prevent JAXB's NPE
	 */
	@Override
	public void setEntities(Collection<Municipio> entities) {
		// TODO Auto-generated method stub
		super.setEntities(entities);
	}

}
