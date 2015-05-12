package br.net.eia.movimento;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.net.eia.contato.Contato;
import br.net.eia.contato.Contato_;
import br.net.eia.emitente.Emitente;
import br.net.eia.emitente.EmitenteDAO;
import br.net.eia.persistence.JpaDAO;

@Repository
public class MovimentoDAO extends JpaDAO<Movimento>{
	
	@Autowired
	EmitenteDAO EDao;
	
	public MovimentoDAO() {
		super(Movimento.class);
	}
	
	@Transactional
	public Movimento create(Movimento nf) {
		if (nf.getNumero() == 0) {
			nf.setNumero(getMaxNumero(nf.getEmitente().getToken())+1);
		}
		this.em.persist(nf);
		this.em.flush();
		return nf;

	}
	
	@Transactional
	public Movimento update(Movimento nf) {
		if (nf.getNumero() == 0) {
			nf.setNumero(getMaxNumero(nf.getEmitente().getToken())+1);
		}
		nf.setUpdateDate(new Date());
		this.em.merge(nf);
		return nf;

	}
	
	private long getMaxNumero(Integer xEmitente){
		long max = 0;		
		List<Movimento> nfs = filtrar(xEmitente);		
		for(Movimento nf : nfs){
			if(nf.getNumero()>max){
				max=nf.getNumero();
			}
		}		
		return max;
	}
	
public List<Movimento> filtrar(Integer xEmitente){
		
		CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Movimento> query = builder.createQuery(Movimento.class);
        Root<Movimento> from = query.from(Movimento.class);
        query.select(from);
        query.orderBy(builder.asc(from.get(Movimento_.numero)));
		List<Predicate> predicados = new ArrayList<Predicate>();
		
		Predicate pAtivo = builder.equal(from.get(Movimento_.active), true);
		predicados.add(pAtivo);
		
		try{			
			Emitente emitente = EDao.carregar(xEmitente);
			Predicate pEmitente = builder.equal(from.<Emitente> get(Movimento_.emitente), emitente);
			predicados.add(pEmitente);
		}catch(Exception e){
			Logger.getLogger(getClass().getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);
		}		

		
		 // cria o where com as clausulas de filtro da select  
        if (predicados.size() > 0) {  
            query.where(builder.and(predicados.toArray(new Predicate[]{})));  
        } 
		
		TypedQuery<Movimento> q = em.createQuery(query);
        
        List<Movimento> results = q.getResultList();
       
        return results;
	}


public List<Movimento> pesquisar(Integer xEmitente, Date dateIni, Date dateFim, String notaFiscal, String dest) {
	CriteriaBuilder builder = em.getCriteriaBuilder();
    CriteriaQuery<Movimento> query = builder.createQuery(Movimento.class);
    Root<Movimento> root = query.from(Movimento.class);        
    query.select(root);
    query.orderBy(builder.asc(root.get(Movimento_.numero)));
    Join<Movimento, Contato> fornecedores = root.join(Movimento_.dest);

	List<Predicate> predicados = new ArrayList<Predicate>();
	
	Predicate pAtivo = builder.equal(root.get(Movimento_.active), true);
	predicados.add(pAtivo);
	
	try{			
		Emitente emitente = EDao.carregar(xEmitente);
		Predicate pEmitente = builder.equal(root.<Emitente> get(Movimento_.emitente), emitente);
		predicados.add(pEmitente);
	}catch(Exception e){
		Logger.getLogger(getClass().getName()).log(
				Level.ERROR, e.getLocalizedMessage(), e);
	}
	
	if(dateIni!=null){
		predicados.add(builder.greaterThanOrEqualTo(root.<Date> get(Movimento_.dhEmi), dateIni));
	}
	if(dateFim!=null){
		predicados.add(builder.lessThanOrEqualTo(root.<Date> get(Movimento_.dhSaiEnt), dateFim));
	}			
	
	if (!notaFiscal.equalsIgnoreCase("todos")) {
		Predicate pNNf = builder.equal(root.<Long> get(Movimento_.numero), Long.parseLong(notaFiscal));
		predicados.add(pNNf);
	}
			
	if (!dest.equalsIgnoreCase("todos")) {
		predicados.add(builder.like(
				builder.upper(fornecedores.get(Contato_.nome)), "%"
						+ dest.toUpperCase() + "%"));
	}        	
	
	 // cria o where com as clausulas de filtro da select  
    if (predicados.size() > 0) {  
        query.where(builder.and(predicados.toArray(new Predicate[]{}))); 
        
    } 
	
	TypedQuery<Movimento> q = em.createQuery(query);
	List<Movimento> results = null;
    try{
    	results = q.getResultList();
    }catch(Exception e){
    	Logger.getLogger(getClass().getName()).log(
				Level.ERROR, e.getLocalizedMessage(), e);
    }
   
    return results;
}
	
}
