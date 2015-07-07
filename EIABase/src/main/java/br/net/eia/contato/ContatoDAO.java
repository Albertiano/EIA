package br.net.eia.contato;

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
import org.springframework.transaction.annotation.Transactional;

import br.net.eia.emitente.Emitente;
import br.net.eia.emitente.EmitenteDAO;
import br.net.eia.enums.UF;
import br.net.eia.municipio.Municipio;
import br.net.eia.municipio.MunicipioDAO;
import br.net.eia.notafiscal.NotaFiscal_;
import br.net.eia.pais.Pais;
import br.net.eia.pais.PaisDAO;
import br.net.eia.persistence.JpaDAO;
import br.net.eia.util.Formatador;

@Repository
public class ContatoDAO extends JpaDAO<Contato>{
	
	@Autowired
	PaisDAO pDao;
	@Autowired
	MunicipioDAO mDao;
	@Autowired
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
					Level.ERROR, e.getLocalizedMessage(), e);
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
					Level.ERROR, e.getLocalizedMessage(), e);
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
					Level.ERROR, e.getLocalizedMessage(), e);
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
					Level.ERROR, e.getLocalizedMessage(), e);
		}

		// varios predicados construidos dinamicamente............
		
		
		if ((numDoc != null) && (!(numDoc.isEmpty()))
				&& (!numDoc.equalsIgnoreCase("todos"))) {
			predicados.add(builder.like(from.<String> get(Contato_.numDoc),
					new Formatador().formatterCNPJCPF(numDoc)));
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
