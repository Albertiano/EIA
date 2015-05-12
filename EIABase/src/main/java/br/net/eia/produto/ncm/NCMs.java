package br.net.eia.produto.ncm;

import java.util.Collection;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import br.net.eia.entities.EntityCollection;

@XmlRootElement
public class NCMs extends EntityCollection<NCM> {

	@Override
	@XmlElement(name="ncm")
	public Collection<NCM> getEntities() {
		return super.getEntities();
	}
	
	
	/*
	 * Overriden to prevent JAXB's NPE
	 */
	@Override
	public void setEntities(Collection<NCM> entities) {
		// TODO Auto-generated method stub
		super.setEntities(entities);
	}
	
}
