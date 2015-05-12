package br.net.eia.compra;

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
import br.net.eia.contato.Contato;
import br.net.eia.contato.Contato_;
import br.net.eia.emitente.Emitente;
import br.net.eia.emitente.EmitenteDAO;
import br.net.eia.persistence.JpaDAO;

@Repository
public class CompraDAO extends JpaDAO<Compra>{
	@Autowired
	EmitenteDAO EDao;
	
	public CompraDAO() {
		super(Compra.class);
	}	
	
	public List<Compra> pesquisar(Integer xEmitente, Date dateIni, Date dateFim, String pedido, String notaFiscal, String fornecedor) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Compra> query = builder.createQuery(Compra.class);
        Root<Compra> root = query.from(Compra.class);        
        query.select(root);
        
        Join<Compra, Contato> fornecedores = root.join(Compra_.fornecedor);

		List<Predicate> predicados = new ArrayList<Predicate>();
		
		Predicate pAtivo = builder.equal(root.get(Compra_.active), true);
		predicados.add(pAtivo);
		
		try{			
			Emitente emitente = EDao.carregar(xEmitente);
			Predicate pEmitente = builder.equal(root.<Emitente> get(Compra_.emitente), emitente);
			predicados.add(pEmitente);
		}catch(Exception e){
			Logger.getLogger(getClass().getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);
		}
		
		if(dateIni!=null){
			predicados.add(builder.greaterThanOrEqualTo(root.<Date> get(Compra_.emissao), dateIni));
		}
		if(dateFim!=null){
			predicados.add(builder.lessThanOrEqualTo(root.<Date> get(Compra_.emissao), dateFim));
		}
		
		if (!pedido.equalsIgnoreCase("todos")) {
			predicados.add(builder.like(
					builder.upper(root.get(Compra_.pedido)),
					pedido.toUpperCase()));
		}
				
		
		if (!notaFiscal.equalsIgnoreCase("todos")) {
			predicados.add(builder.like(
					builder.upper(root.get(Compra_.notaFiscal)),
					notaFiscal.toUpperCase()));
		}
				
		if (!fornecedor.equalsIgnoreCase("todos")) {
			predicados.add(builder.like(
					builder.upper(fornecedores.get(Contato_.nome)), "%"
							+ fornecedor.toUpperCase() + "%"));
		}        	
		
		 // cria o where com as clausulas de filtro da select  
        if (predicados.size() > 0) {  
            query.where(builder.and(predicados.toArray(new Predicate[]{})));  
        } 
		
		TypedQuery<Compra> q = em.createQuery(query);
		List<Compra> results = null;
        try{
        	results = q.getResultList();
        }catch(Exception e){
        	Logger.getLogger(getClass().getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);
        }
       
        return results;
	}
}