package br.net.eia.movimento;

import br.net.eia.entities.EntityCollection;
import java.util.Collection;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Movimentos extends EntityCollection<Movimento> {

	@Override
	@XmlElement(name="movimento")
	public Collection<Movimento> getEntities() {
		return super.getEntities();
	}
	
	
	/*
	 * Overriden to prevent JAXB's NPE
	 */
	@Override
	public void setEntities(Collection<Movimento> entities) {
		// TODO Auto-generated method stub
		super.setEntities(entities);
	}
	

}
