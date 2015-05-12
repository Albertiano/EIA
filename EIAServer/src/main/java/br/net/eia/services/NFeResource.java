package br.net.eia.services;

import br.net.eia.nfe.NFe;
import br.net.eia.nfe.NFeDAO;
import br.net.eia.nfe.NFsE;
import br.net.eia.services.BaseService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Path("/nfe")
public class NFeResource extends BaseService<NFe, NFsE> {
	@Autowired
	NFeDAO cDao;

	public NFeResource() {
		super(NFsE.class);
	}

	@Autowired
	public void setPersonDAO(NFeDAO dao) {
		setDao(dao);
	}

	@GET
	@Path("/chave={chave}")
	public Response filtrar(@PathParam("chave")String chave) {		
		NFe entity = cDao.pesquisaChave(chave);
		if (entity != null) {
			entity.createStandardLinks();
			return Response.ok(entity).build();
		}

		return Response.status(Status.NOT_FOUND).build();
	}
}
