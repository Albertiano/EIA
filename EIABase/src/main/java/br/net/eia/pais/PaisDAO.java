package br.net.eia.pais;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.net.eia.persistence.JpaDAO;

@Repository
public class PaisDAO extends JpaDAO<Pais>{
	public PaisDAO() {
		super(Pais.class);
	}
	
	public Pais getPais(String nome){
		TypedQuery<Pais> query = em.createQuery("select p from Pais p where p.xPais like :name", Pais.class);
        query.setParameter("name", "%"+nome+"%");
        Pais p = query.getSingleResult();
        return p;
	}
}
