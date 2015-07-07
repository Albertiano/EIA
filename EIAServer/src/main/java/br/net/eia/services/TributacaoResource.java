package br.net.eia.services;

import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.net.eia.produto.imposto.Tributacao;
import br.net.eia.produto.imposto.TributacaoDAO;
import br.net.eia.produto.imposto.Tributacoes;

@Service
@Path("/tributacao")
public class TributacaoResource extends BaseService<Tributacao, Tributacoes> {

	@Autowired
	TributacaoDAO mDAO;
	
	public TributacaoResource() {
		super(Tributacoes.class);
	}

	@Autowired
	public void setPersonDAO(TributacaoDAO dao) {
		setDao(dao);
	}	
	
	@GET
	@Path("/emitente={emitente}")
	public Tributacoes filtrar(@PathParam("emitente") Integer emitente) {
		
		Collection<Tributacao> entities = mDAO.filtrar(emitente);
		for (Tributacao entity : entities) {
			entity.createStandardLinks();
		}
		Tributacoes collection = encapsulateEntities(entities);
		return collection;
	}

}
