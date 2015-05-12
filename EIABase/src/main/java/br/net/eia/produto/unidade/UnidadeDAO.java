package br.net.eia.produto.unidade;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.net.eia.emitente.Emitente;
import br.net.eia.emitente.EmitenteDAO;
import br.net.eia.persistence.JpaDAO;

@Repository
public class UnidadeDAO extends JpaDAO<Unidade>{
	@Autowired
	EmitenteDAO EDao;
	
	public UnidadeDAO() {
		super(Unidade.class);
	}	
	
public List<Unidade> filtrar(Integer xEmitente){
		
		CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Unidade> query = builder.createQuery(Unidade.class);
        Root<Unidade> from = query.from(Unidade.class);
        query.select(from);

		List<Predicate> predicados = new ArrayList<Predicate>();
		
		Predicate pAtivo = builder.equal(from.get(Unidade_.active), true);
		predicados.add(pAtivo);
		
		try{			
			Emitente emitente = EDao.carregar(xEmitente);
			Predicate pEmitente = builder.equal(from.<Emitente> get(Unidade_.emitente), emitente);
			predicados.add(pEmitente);
		}catch(Exception e){
			Logger.getLogger(getClass().getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);
		}		

		
		 // cria o where com as clausulas de filtro da select  
        if (predicados.size() > 0) {  
            query.where(builder.and(predicados.toArray(new Predicate[]{})));  
        } 
		
		TypedQuery<Unidade> q = em.createQuery(query);
        
        List<Unidade> results = q.getResultList();
       
        return results;
	}
}