package br.net.eia.services;

import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.net.eia.produto.unidade.Unidade;
import br.net.eia.produto.unidade.UnidadeDAO;
import br.net.eia.produto.unidade.Unidades;

@Service
@Path("/unidade")
public class UnidadeResource extends BaseService<Unidade, Unidades> {

	@Autowired
	UnidadeDAO mDAO;
	
	public UnidadeResource() {
		super(Unidades.class);
	}

	@Autowired
	public void setPersonDAO(UnidadeDAO dao) {
		setDao(dao);
	}	
	
	@GET
	@Path("/emitente={emitente}")
	public Unidades filtrar(@PathParam("emitente") Integer emitente) {
		
		Collection<Unidade> entities = mDAO.filtrar(emitente);
		for (Unidade entity : entities) {
			entity.createStandardLinks();
		}
		Unidades collection = encapsulateEntities(entities);
		return collection;
	}

}
