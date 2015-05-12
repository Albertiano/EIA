package br.net.eia.municipio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import br.net.eia.emitente.Emitente;
import br.net.eia.enums.UF;
import br.net.eia.notafiscal.NotaFiscal;
import br.net.eia.notafiscal.NotaFiscal_;
import br.net.eia.persistence.JpaDAO;

@Repository
public class MunicipioDAO extends JpaDAO<Municipio> {
	public MunicipioDAO() {
		super(Municipio.class);
	}

	@SuppressWarnings("unchecked")
	public Collection<Municipio> retrieveUF(String xUF) {
		UF uf = UF.valueOf(xUF);
		StringBuilder query = new StringBuilder("select entity from ")
				.append(Municipio.class.getSimpleName())
				.append(" entity where entity.active=true")
				.append(" and entity.UF=:uf")
				.append(" ORDER BY entity.xMun");
		Query q = this.em.createQuery(query.toString());
		q.setParameter("uf", uf);
		return q.getResultList();
	}
	
	public Municipio getMunicipio(String nome, String xUF){
		UF uf = UF.valueOf(xUF);
		StringBuilder query = new StringBuilder("select entity from ")
		.append(Municipio.class.getSimpleName())
		.append(" entity where entity.active=true")
		.append(" and entity.UF=:uf")
		.append(" and entity.xMun like :name")
		.append(" ORDER BY entity.xMun");
		TypedQuery<Municipio> q = em.createQuery(query.toString(), Municipio.class);
		q.setParameter("uf", uf);
        q.setParameter("name", nome);
        return q.getSingleResult();
	}
	
public Municipio carregar(Integer cMun){
		
		CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Municipio> query = builder.createQuery(Municipio.class);
        Root<Municipio> from = query.from(Municipio.class);
        query.select(from);
		List<Predicate> predicados = new ArrayList<Predicate>();
		
		Predicate pAtivo = builder.equal(from.get(NotaFiscal_.active), true);
		predicados.add(pAtivo);
		
		Predicate pCMun = builder.equal(from.get(Municipio_.cMun), cMun);
		predicados.add(pCMun);
		
		 // cria o where com as clausulas de filtro da select  
        if (predicados.size() > 0) {  
            query.where(builder.and(predicados.toArray(new Predicate[]{})));  
        } 
		
		TypedQuery<Municipio> q = em.createQuery(query);
        
		Municipio results = q.getSingleResult();
       
        return results;
	}
}
