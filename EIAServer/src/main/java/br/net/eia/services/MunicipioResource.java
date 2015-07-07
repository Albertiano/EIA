package br.net.eia.services;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilderException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.net.eia.municipio.Municipio;
import br.net.eia.municipio.MunicipioDAO;
import br.net.eia.municipio.MunicipioXML;
import br.net.eia.municipio.Municipios;

@Service
@Path("/municipio")
public class MunicipioResource extends BaseService<Municipio, Municipios> {

	@Autowired
	MunicipioDAO mDAO;
	
	public MunicipioResource() {
		super(Municipios.class);
	}

	@Autowired
	public void setPersonDAO(MunicipioDAO dao) {
		setDao(dao);
	}

	@Path("/install")
	@GET
	public Response install() {
		List<Municipio> municipios = new MunicipioXML().realizaLeituraXML();
		for (Municipio m : municipios) {
			create(m);
		}
		Municipios ps = new Municipios();
		ps.setEntities(municipios);
		return Response.ok(ps).build();
	}
	
	@GET
	@Path("/UF.{uf}")
	public Municipios retrieveUF(@PathParam("uf") String uf) throws UnsupportedEncodingException, IllegalArgumentException, UriBuilderException {
		
		Collection<Municipio> entities = mDAO.retrieveUF(uf);
		for (Municipio entity : entities) {
			entity.createStandardLinks();
		}
		Municipios collection = encapsulateEntities(entities);		
		return collection;
	}

	@GET
	@Path("/cMun={cMun}")
	public Response carregar(@PathParam("cMun") Integer cMun) {
		Municipio entity = mDAO.carregar(cMun);
		if (entity != null) {
			entity.createStandardLinks();
			return Response.ok(entity).build();
		}

		return Response.status(Status.NOT_FOUND).build();
	}
}
