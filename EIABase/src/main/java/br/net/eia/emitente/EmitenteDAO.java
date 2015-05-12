package br.net.eia.emitente;

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
import br.net.eia.enums.UF;
import br.net.eia.municipio.Municipio;
import br.net.eia.municipio.MunicipioDAO;
import br.net.eia.pais.Pais;
import br.net.eia.pais.PaisDAO;
import br.net.eia.persistence.JpaDAO;

@Repository
public class EmitenteDAO extends JpaDAO<Emitente>{
	
	@Autowired
	PaisDAO paisDAO;
	@Autowired
	MunicipioDAO mDao;

	public EmitenteDAO() {
		super(Emitente.class);
	}
    @SuppressWarnings("unchecked")
	public List<Emitente> listarPorNome(){        
        StringBuilder query = new StringBuilder("select entity from ")
        .append(getManagedClass().getSimpleName())
        .append(" entity where entity.active=true ")
        .append("order by entity.nome");
		return this.em.createQuery(query.toString()).getResultList();
    }
	
	public List<Emitente> filtrar(String id, String nome, String xPais, String xUf, String xMunicipio){
		
		CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Emitente> query = builder.createQuery(Emitente.class);
        Root<Emitente> from = query.from(Emitente.class);
        query.select(from);

		List<Predicate> predicados = new ArrayList<Predicate>();

		// varios predicados construidos dinamicamente............
		if ((id != null) && (!(id.isEmpty()))
				&& (!id.equalsIgnoreCase("todos"))) {
			try{
        		Long lId = Long.parseLong(id);
        		predicados.add(builder.equal(from.get(Emitente_.id), lId));
        	}catch(Exception e){
        		
        	} 
			
		}

		if ((nome != null) && (!(nome.isEmpty()))
				&& (!nome.equalsIgnoreCase("todos"))) {
			System.out.println(Emitente_.nome);
			predicados.add(builder.like(
					builder.upper(from.<String> get(Emitente_.nome)),
					"%" + nome.toUpperCase() + "%"));
		}
		
		if ((xPais != null) && (!(xPais.isEmpty()))
				&& (!xPais.equalsIgnoreCase("todos")) && (!xPais.equalsIgnoreCase("null"))) {
			
			Pais pais = paisDAO.getPais(xPais);
			try{
				Predicate pPais = builder.equal(from.<Pais> get(Emitente_.pais), pais);
				predicados.add(pPais);
			}catch(Exception e){
				Logger.getLogger(getClass().getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);
			}			
			
		}  
		
		if ((xUf != null) && (!(xUf.isEmpty()))
				&& (!xUf.equalsIgnoreCase("todos")) && (!xUf.equalsIgnoreCase("null"))) {	
			predicados.add(builder.equal(from.<UF> get(Emitente_.uf), UF.valueOf(xUf)));
		}
		
		if ((xMunicipio != null) && (!(xMunicipio.isEmpty()))
				&& (!xMunicipio.equalsIgnoreCase("todos")) && (!xMunicipio.equalsIgnoreCase("null")) 
				&& (xUf != null) && (!(xUf.isEmpty()))
				&& (!xUf.equalsIgnoreCase("todos")) && (!xUf.equalsIgnoreCase("null"))) {	
			
			Municipio municipio = mDao.getMunicipio(xMunicipio, xUf);
			predicados.add(builder.equal(from.<Municipio> get(Emitente_.municipio),
					municipio));
		}
		
		 // cria o where com as clausulas de filtro da select  
        if (predicados.size() > 0) {  
            query.where(builder.and(predicados.toArray(new Predicate[]{})));  
        } 
		
		TypedQuery<Emitente> q = em.createQuery(query);
        
        List<Emitente> results = q.getResultList();
       
        return results;
	}
	
public Emitente carregar(Integer token){
		
		CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Emitente> query = builder.createQuery(Emitente.class);
        Root<Emitente> from = query.from(Emitente.class);
        query.select(from);

		List<Predicate> predicados = new ArrayList<Predicate>();

		// varios predicados construidos dinamicamente............
		
		
			
        predicados.add(builder.equal(from.get(Emitente_.token), token));
        	
		
		
		 // cria o where com as clausulas de filtro da select  
        if (predicados.size() > 0) {  
            query.where(builder.and(predicados.toArray(new Predicate[]{})));  
        } 
		
		TypedQuery<Emitente> q = em.createQuery(query);
        
		Emitente results = q.getSingleResult();
       
        return results;
	}
	
}
