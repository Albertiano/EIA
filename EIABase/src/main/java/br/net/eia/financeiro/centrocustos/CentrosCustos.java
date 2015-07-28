package br.net.eia.financeiro.centrocustos;

import java.util.Collection;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import br.net.eia.entities.EntityCollection;

@XmlRootElement
public class CentrosCustos extends EntityCollection<CentroCustos> {

	@Override
	@XmlElement
	public Collection<CentroCustos> getEntities() {
		return super.getEntities();
	}
	
	@Override
	public void setEntities(Collection<CentroCustos> entities) {
		// TODO Auto-generated method stub
		super.setEntities(entities);
	}
}
