package br.net.eia.repository;

import javax.persistence.TypedQuery;
import br.net.eia.model.pais.Pais;
import br.net.eia.repository.persistence.JpaDAO;

public class Paises extends JpaDAO<Pais>{
	public Paises() {
		super(Pais.class);
	}
	
	public Pais getPais(String nome){
		TypedQuery<Pais> query = em.createQuery("select p from Pais p where p.xPais like :name", Pais.class);
        query.setParameter("name", "%"+nome+"%");
        Pais p = query.getSingleResult();        
        return p;
	}
}
