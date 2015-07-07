package br.net.eia.services;

import java.util.Collection;

import br.net.eia.contato.Contato;
import br.net.eia.contato.ContatoDAO;
import br.net.eia.contato.Contatos;
import br.net.eia.services.BaseService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Path("/contato")
public class ContatoResource extends BaseService<Contato, Contatos> {
	@Autowired
	ContatoDAO cDao;

	public ContatoResource() {
		super(Contatos.class);
	}

	@Autowired
	public void setPersonDAO(ContatoDAO dao) {
		setDao(dao);
	}

	@GET
	@Path("/tpContato={tpContato}&emitente={emitente}&nome={nome}&pais={pais}&uf={uf}&municipio={municipio}")
	public Contatos filtrar(@PathParam("tpContato")String tpContato, @PathParam("emitente") Integer emitente,
			@PathParam("nome") String nome, @PathParam("pais") String pais, @PathParam("uf") String xUf, @PathParam("municipio") String xMunicipio) {
		
		Collection<Contato> entities = cDao.filtrar(tpContato, emitente, nome, pais,xUf,xMunicipio);
		for (Contato entity : entities) {
			entity.createStandardLinks();
		}
		Contatos collection = encapsulateEntities(entities);
		return collection;
	}
	
	@GET
	@Path("/emitente={emitente}")
	public Contatos filtrar(@PathParam("emitente") Integer emitente) {
		
		Collection<Contato> entities = cDao.filtrar("todos", emitente, "todos", "todos","todos","todos");
		for (Contato entity : entities) {
			entity.createStandardLinks();
		}
		Contatos collection = encapsulateEntities(entities);
		return collection;
	}
	
	@GET
	@Path("/token={token}&numDoc={numDoc}")
	public Contato pesquisarNumDoc(@PathParam("token") Integer token, @PathParam("numDoc") String numDoc){
		Contato c = cDao.pesquisaNumDoc(token, numDoc);
		return c;
	}
}
