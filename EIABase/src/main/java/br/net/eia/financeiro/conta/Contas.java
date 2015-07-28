package br.net.eia.financeiro.conta;

import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import br.net.eia.entities.EntityCollection;

@XmlRootElement
public class Contas extends EntityCollection<Conta> {

	@Override
	@XmlElement
	public Collection<Conta> getEntities() {
		return super.getEntities();
	}
	
	@Override
	public void setEntities(Collection<Conta> entities) {
		// TODO Auto-generated method stub
		super.setEntities(entities);
	}
}
