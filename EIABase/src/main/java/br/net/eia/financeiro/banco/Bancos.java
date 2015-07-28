package br.net.eia.financeiro.banco;

import java.util.Collection;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import br.net.eia.entities.EntityCollection;

@XmlRootElement
public class Bancos extends EntityCollection<Banco> {

	@Override
	@XmlElement
	public Collection<Banco> getEntities() {
		return super.getEntities();
	}
	
	@Override
	public void setEntities(Collection<Banco> entities) {
		// TODO Auto-generated method stub
		super.setEntities(entities);
	}
}
