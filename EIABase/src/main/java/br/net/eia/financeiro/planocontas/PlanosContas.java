package br.net.eia.financeiro.planocontas;

import java.util.Collection;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import br.net.eia.entities.EntityCollection;

@XmlRootElement
public class PlanosContas extends EntityCollection<PlanoContas> {

	@Override
	@XmlElement
	public Collection<PlanoContas> getEntities() {
		return super.getEntities();
	}
	
	@Override
	public void setEntities(Collection<PlanoContas> entities) {
		// TODO Auto-generated method stub
		super.setEntities(entities);
	}
}
