package br.net.eia.services;

import java.util.List;

import br.net.eia.pais.Pais;
import br.net.eia.pais.PaisDAO;
import br.net.eia.pais.PaisXML;
import br.net.eia.pais.Paises;
import br.net.eia.services.BaseService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Path("/pais")
public class PaisResource extends BaseService<Pais, Paises> {

	public PaisResource() {
		super(Paises.class);
	}

	@Autowired
	public void setPersonDAO(PaisDAO dao) {
		setDao(dao);
	}

	@Path("/install")
	@GET
	public Response install() {
		List<Pais> paises = new PaisXML().realizaLeituraXML();
		for (Pais p : paises) {
			create(p);
		}
		Paises ps = new Paises();
		ps.setEntities(paises);
		return Response.ok(ps).build();
	}

}
