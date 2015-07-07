package br.net.eia.nfe;

import java.util.Collection;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import br.net.eia.entities.EntityCollection;

@XmlRootElement
public class NFsE extends EntityCollection<NFe> {

	@Override
	@XmlElement
	public Collection<NFe> getEntities() {
		return super.getEntities();
	}
	
	@Override
	public void setEntities(Collection<NFe> entities) {
		// TODO Auto-generated method stub
		super.setEntities(entities);
	}
}
