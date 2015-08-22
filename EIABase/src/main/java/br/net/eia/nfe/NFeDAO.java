package br.net.eia.nfe;

import org.springframework.stereotype.Repository;

import br.net.eia.persistence.JpaDAO;
import br.net.eia.util.Formatador;

@Repository
public class NFeDAO  extends JpaDAO<NFe>{

	public NFeDAO(){
		super(NFe.class);
	}
	
	public NFe pesquisaChave(String numDoc){  
		NFe fornecedor = null;
        StringBuilder query = new StringBuilder("select entity from ")
        .append(getManagedClass().getSimpleName())
        .append(" entity where entity.active=true ")
        .append("and entity.chave like '")
        .append( new Formatador().formatterCNPJCPF(numDoc))
        .append("'");
        try{
        	fornecedor = (NFe) this.em.createQuery(query.toString()).getSingleResult();
        }catch(Exception e){
        	System.out.println(e.getMessage());
        	return fornecedor;        	
        }        
        
		return fornecedor;
    }
}
