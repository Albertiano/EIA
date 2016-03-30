package br.net.eia.notafiscal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
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
import br.net.eia.util.nfe.ChaveAcessoNFe;

@Repository
public class NotaFiscalDAO extends JpaDAO<NotaFiscal>{
	
	@Autowired
	EmitenteDAO EDao;
	
	public NotaFiscalDAO() {
		super(NotaFiscal.class);
	}
	
	@Transactional
	public NotaFiscal create(NotaFiscal nf) {
		if (nf.getSerie() == 0) {
			nf.setSerie(getMaxSerie(nf.getEmitente().getToken()));
		}
		if (nf.getNumero() == 0) {
			nf.setNumero(getMaxNumero(nf.getEmitente().getToken())+1);
		}
		nf.setChave(ChaveAcessoNFe.getChave(nf));
		nf.setcDV(Integer.parseInt(nf.getChave().substring(43)));
		this.em.persist(nf);
		this.em.flush();
		return nf;

	}
	
	@Transactional
	public NotaFiscal update(NotaFiscal nf) {
		if (nf.getSerie() == 0) {
			nf.setSerie(getMaxSerie(nf.getEmitente().getToken()));
		}
		if (nf.getNumero() == 0) {
			nf.setNumero(getMaxNumero(nf.getEmitente().getToken())+1);
		}
		nf.setChave(ChaveAcessoNFe.getChave(nf));
		nf.setcDV(Integer.parseInt(nf.getChave().substring(43)));
		
		nf.setUpdateDate(new Date());
		this.em.merge(nf);
		return nf;

	}
	
	private int getMaxSerie(Integer xEmitente){
		CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Integer> query = builder.createQuery(Integer.class);
        Root<NotaFiscal> from = query.from(NotaFiscal.class);
        query.select(builder.max(from.get(NotaFiscal_.serie)));
		List<Predicate> predicados = new ArrayList<Predicate>();
		
		Predicate pAtivo = builder.equal(from.get(NotaFiscal_.active), true);
		predicados.add(pAtivo);
		
		try{			
			Emitente emitente = EDao.carregar(xEmitente);
			Predicate pEmitente = builder.equal(from.<Emitente> get(NotaFiscal_.emitente), emitente);
			predicados.add(pEmitente);
		}catch(Exception e){
			Logger.getLogger(getClass().getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);
		}		

		
		 // cria o where com as clausulas de filtro da select  
        if (predicados.size() > 0) {  
            query.where(builder.and(predicados.toArray(new Predicate[]{})));  
        }
        Integer maxAge = em.createQuery(query).getSingleResult();
        return maxAge;
	}
	private int getMaxNumero(Integer xEmitente){
		CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Integer> query = builder.createQuery(Integer.class);
        Root<NotaFiscal> from = query.from(NotaFiscal.class);
        query.select(builder.max(from.get(NotaFiscal_.numero)));
		List<Predicate> predicados = new ArrayList<Predicate>();
		
		Predicate pAtivo = builder.equal(from.get(NotaFiscal_.active), true);
		predicados.add(pAtivo);
		
		try{			
			Emitente emitente = EDao.carregar(xEmitente);
			Predicate pEmitente = builder.equal(from.<Emitente> get(NotaFiscal_.emitente), emitente);
			predicados.add(pEmitente);
		}catch(Exception e){
			Logger.getLogger(getClass().getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);
		}		

		
		 // cria o where com as clausulas de filtro da select  
        if (predicados.size() > 0) {  
            query.where(builder.and(predicados.toArray(new Predicate[]{})));  
        }
        Integer maxAge = em.createQuery(query).getSingleResult();
        return maxAge;
	}
	
public List<NotaFiscal> filtrar(Integer xEmitente){
		
		CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<NotaFiscal> query = builder.createQuery(NotaFiscal.class);
        Root<NotaFiscal> from = query.from(NotaFiscal.class);
        query.select(from);
        query.orderBy(builder.asc(from.get(NotaFiscal_.numero)));
		List<Predicate> predicados = new ArrayList<Predicate>();
		
		Predicate pAtivo = builder.equal(from.get(NotaFiscal_.active), true);
		predicados.add(pAtivo);
		
		try{			
			Emitente emitente = EDao.carregar(xEmitente);
			Predicate pEmitente = builder.equal(from.<Emitente> get(NotaFiscal_.emitente), emitente);
			predicados.add(pEmitente);
		}catch(Exception e){
			Logger.getLogger(getClass().getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);
		}		

		
		 // cria o where com as clausulas de filtro da select  
        if (predicados.size() > 0) {  
            query.where(builder.and(predicados.toArray(new Predicate[]{})));  
        } 
		
		TypedQuery<NotaFiscal> q = em.createQuery(query);
        
        List<NotaFiscal> results = q.getResultList();
       
        return results;
	}


public List<NotaFiscal> pesquisar(Integer xEmitente, Date dateIni, Date dateFim, String notaFiscal, String dest) {
	CriteriaBuilder builder = em.getCriteriaBuilder();
    CriteriaQuery<NotaFiscal> query = builder.createQuery(NotaFiscal.class);
    Root<NotaFiscal> root = query.from(NotaFiscal.class);        
    query.select(root);
    query.orderBy(builder.asc(root.get(NotaFiscal_.numero)));
    Join<NotaFiscal, Contato> fornecedores = root.join(NotaFiscal_.dest);

	List<Predicate> predicados = new ArrayList<Predicate>();
	
	Predicate pAtivo = builder.equal(root.get(NotaFiscal_.active), true);
	predicados.add(pAtivo);
	
	try{			
		Emitente emitente = EDao.carregar(xEmitente);
		Predicate pEmitente = builder.equal(root.<Emitente> get(NotaFiscal_.emitente), emitente);
		predicados.add(pEmitente);
	}catch(Exception e){
		Logger.getLogger(getClass().getName()).log(
				Level.ERROR, e.getLocalizedMessage(), e);
	}
	
	if(dateIni!=null){
		predicados.add(builder.greaterThanOrEqualTo(root.<Date> get(NotaFiscal_.dhEmi), dateIni));
	}
	if(dateFim!=null){
		predicados.add(builder.lessThanOrEqualTo(root.<Date> get(NotaFiscal_.dhEmi), dateFim));
	}			
	
	if (!notaFiscal.equalsIgnoreCase("todos")) {
		Predicate pNNf = builder.equal(root.<Integer> get(NotaFiscal_.numero), Integer.parseInt(notaFiscal));
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
	
	TypedQuery<NotaFiscal> q = em.createQuery(query);
	List<NotaFiscal> results = null;
    try{
    	results = q.getResultList();
    }catch(Exception e){
    	Logger.getLogger(getClass().getName()).log(
				Level.ERROR, e.getLocalizedMessage(), e);
    }
   
    return results;
}
	
}
