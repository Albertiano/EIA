package br.net.eia.financeiro.lancamento;

import java.util.Collection;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import br.net.eia.entities.EntityCollection;

@XmlRootElement
public class Lancamentos extends EntityCollection<Lancamento> {

	@Override
	@XmlElement
	public Collection<Lancamento> getEntities() {
		return super.getEntities();
	}
	
	@Override
	public void setEntities(Collection<Lancamento> entities) {
		// TODO Auto-generated method stub
		super.setEntities(entities);
	}
}
