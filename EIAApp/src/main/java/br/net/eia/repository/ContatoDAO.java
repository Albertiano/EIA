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
import br.net.eia.app.util.Transactional;
import br.net.eia.model.contato.Contato;
import br.net.eia.model.contato.Contato_;
import br.net.eia.model.contato.TpContato;
import br.net.eia.model.emitente.Emitente;
import br.net.eia.model.municipio.Municipio;
import br.net.eia.model.municipio.UF;
import br.net.eia.model.pais.Pais;
import br.net.eia.repository.persistence.JpaDAO;

public class ContatoDAO extends JpaDAO<Contato>{
	
	@Inject
	Paises pDao;
	@Inject
	MunicipioDAO mDao;
	@Inject
	EmitenteDAO EDao;

	public ContatoDAO() {
		super(Contato.class);
	}
	
	public List<Contato> filtrar(String tpContato, Integer xEmitente, String nome, String xPais, String xUf, String xMunicipio){
		
		CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Contato> query = builder.createQuery(Contato.class);
        Root<Contato> from = query.from(Contato.class);
        query.select(from);
        query.orderBy(builder.asc(from.get(Contato_.nome)));
        
		List<Predicate> predicados = new ArrayList<Predicate>();
		
		Predicate pAtivo = builder.equal(from.get(Contato_.active), true);
		predicados.add(pAtivo);
		
		try{			
			Emitente emitente = EDao.carregar(xEmitente);
			Predicate pEmitente = builder.equal(from.<Emitente> get(Contato_.emitente), emitente);
			predicados.add(pEmitente);
		}catch(Exception e){
			Logger.getLogger(getClass().getName()).log(
					Level.SEVERE, e.getLocalizedMessage(), e);
		}

		// varios predicados construidos dinamicamente............
		

		if ((nome != null) && (!(nome.isEmpty()))
				&& (!nome.equalsIgnoreCase("todos"))) {
			predicados.add(builder.like(
					builder.upper(from.<String> get(Contato_.nome)),
					"%" + nome.toUpperCase() + "%"));
		}
				
		if ((tpContato != null) && (!(tpContato.isEmpty()))
				&& (!tpContato.equalsIgnoreCase("todos")) && (!tpContato.equalsIgnoreCase("null"))) {
			try{
				Predicate pTpContato = builder.equal(from.<TpContato> get(Contato_.tpContato), TpContato.valueOf(tpContato));
				predicados.add(pTpContato);
			}catch(Exception e){
				Logger.getLogger(getClass().getName()).log(
					Level.SEVERE, e.getLocalizedMessage(), e);
			}
		}
		
		if ((xPais != null) && (!(xPais.isEmpty()))
				&& (!xPais.equalsIgnoreCase("todos")) && (!xPais.equalsIgnoreCase("null"))) {
			
			Pais pais = pDao.getPais(xPais);
			try{
				Predicate pPais = builder.equal(from.<Pais> get(Contato_.pais), pais);
				System.out.println(pPais);
				predicados.add(pPais);
			}catch(Exception e){
				Logger.getLogger(getClass().getName()).log(
					Level.SEVERE, e.getLocalizedMessage(), e);
			}			
			
		}  
		
		if ((xUf != null) && (!(xUf.isEmpty()))
				&& (!xUf.equalsIgnoreCase("todos")) && (!xUf.equalsIgnoreCase("null"))) {	
			predicados.add(builder.equal(from.<UF> get(Contato_.uf), UF.valueOf(xUf)));
		}
		
		if ((xMunicipio != null) && (!(xMunicipio.isEmpty()))
				&& (!xMunicipio.equalsIgnoreCase("todos")) && (!xMunicipio.equalsIgnoreCase("null")) 
				&& (xUf != null) && (!(xUf.isEmpty()))
				&& (!xUf.equalsIgnoreCase("todos")) && (!xUf.equalsIgnoreCase("null"))) {	
			
			Municipio municipio = mDao.getMunicipio(xMunicipio, xUf);
			predicados.add(builder.equal(from.<Municipio> get(Contato_.municipio),
					municipio));
		}
		
		 // cria o where com as clausulas de filtro da select  
        if (predicados.size() > 0) {  
            query.where(builder.and(predicados.toArray(new Predicate[]{})));  
        } 
		
		TypedQuery<Contato> q = em.createQuery(query);
        
        List<Contato> results = q.getResultList();
       
        return results;
	}
	
	public Contato pesquisaNumDoc(Integer xEmitente, String numDoc){ 
		CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Contato> query = builder.createQuery(Contato.class);
        Root<Contato> from = query.from(Contato.class);
        query.select(from);
        query.orderBy(builder.asc(from.get(Contato_.nome)));
        
		List<Predicate> predicados = new ArrayList<Predicate>();
		
		Predicate pAtivo = builder.equal(from.get(Contato_.active), true);
		predicados.add(pAtivo);
		
		try{			
			Emitente emitente = EDao.carregar(xEmitente);
			Predicate pEmitente = builder.equal(from.<Emitente> get(Contato_.emitente), emitente);
			predicados.add(pEmitente);
		}catch(Exception e){
			Logger.getLogger(getClass().getName()).log(
					Level.SEVERE, e.getLocalizedMessage(), e);
		}

		// varios predicados construidos dinamicamente............
		
		
		if ((numDoc != null) && (!(numDoc.isEmpty()))
				&& (!numDoc.equalsIgnoreCase("todos"))) {
			predicados.add(builder.like(from.<String> get(Contato_.numDoc),
					numDoc));
		}
		
		
		 // cria o where com as clausulas de filtro da select  
        if (predicados.size() > 0) {  
            query.where(builder.and(predicados.toArray(new Predicate[]{})));  
        } 
		
		TypedQuery<Contato> q = em.createQuery(query);
        
        Contato results = q.getSingleResult();
       
        return results;
    }
	
	@Transactional
	public Contato create(Contato p){
		if (p.getCodigo() == null || p.getCodigo().isEmpty()) {
			p.setCodigo(String.valueOf(super.maxId() + 1));
		}
		this.em.persist(p);
		this.em.flush();
		return p;
	}
	
}
