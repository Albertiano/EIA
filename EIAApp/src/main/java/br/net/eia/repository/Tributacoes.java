package br.net.eia.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import br.net.eia.model.emitente.Emitente;
import br.net.eia.model.produto.imposto.Tributacao;
import br.net.eia.repository.persistence.JpaDAO;

public class Tributacoes  extends JpaDAO<Tributacao>{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	EmitenteDAO EDao;
	
	public Tributacoes(){
		super(Tributacao.class);
	}

public List<Tributacao> filtrar(Integer xEmitente){
		CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Tributacao> query = builder.createQuery(Tributacao.class);
        Root<Tributacao> root = query.from(Tributacao.class);
        query.select(root);
        
        Metamodel m = em.getMetamodel();
        EntityType<Tributacao> model_ = m.entity(Tributacao.class);
        
		List<Predicate> predicados = new ArrayList<Predicate>();
		
		Predicate pAtivo = builder.equal(root.get(model_.getSingularAttribute("active", Boolean.class)), true);
		predicados.add(pAtivo);
		
		try{			
			Emitente emitente = EDao.carregar(xEmitente);
			
			Predicate pEmitente = builder.equal(
					root.<Emitente> get(model_.getSingularAttribute("emitente",Emitente.class)), emitente);
			
			predicados.add(pEmitente);
		}catch(Exception e){
			Logger.getLogger(getClass().getName()).log(
					Level.SEVERE, e.getLocalizedMessage(), e);
		}		

		
		 // cria o where com as clausulas de filtro da select  
        if (predicados.size() > 0) {  
            query.where(builder.and(predicados.toArray(new Predicate[]{})));  
        } 
		
		TypedQuery<Tributacao> q = em.createQuery(query);
        
        List<Tributacao> results = q.getResultList();
       
        return results;
	}
}
