package br.net.eia.services;

import br.net.eia.emitente.Emitente;
import br.net.eia.emitente.EmitenteDAO;
import br.net.eia.emitente.Emitentes;
import br.net.eia.services.BaseService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Path("/emitente")
public class EmitenteResource extends BaseService<Emitente, Emitentes> {
	@Autowired
	EmitenteDAO cDao;

	public EmitenteResource() {
		super(Emitentes.class);
	}

	@Autowired
	public void setPersonDAO(EmitenteDAO dao) {
		setDao(dao);
	}

	@GET
	@Path("/token={token}")
	public Emitente carregar(@PathParam("token") Integer token) {		
		Emitente collection = cDao.carregar(token);
		return collection;
	}
}
