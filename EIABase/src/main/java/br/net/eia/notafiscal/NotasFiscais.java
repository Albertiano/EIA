package br.net.eia.notafiscal;

import br.net.eia.entities.EntityCollection;
import java.util.Collection;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NotasFiscais extends EntityCollection<NotaFiscal> {

	@Override
	@XmlElement(name="notaFiscal")
	public Collection<NotaFiscal> getEntities() {
		return super.getEntities();
	}
	
	
	/*
	 * Overriden to prevent JAXB's NPE
	 */
	@Override
	public void setEntities(Collection<NotaFiscal> entities) {
		// TODO Auto-generated method stub
		super.setEntities(entities);
	}
	

}
