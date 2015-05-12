package br.net.eia.services;

import java.util.Collection;
import java.util.Date;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.net.eia.notafiscal.NotaFiscal;
import br.net.eia.notafiscal.NotaFiscalDAO;
import br.net.eia.notafiscal.NotasFiscais;

@Service
@Path("/notaFiscal")
public class NotaFiscalResource extends BaseService<NotaFiscal, NotasFiscais> {

	@Autowired
	NotaFiscalDAO mDAO;
	
	public NotaFiscalResource() {
		super(NotasFiscais.class);
	}

	@Autowired
	public void setPersonDAO(NotaFiscalDAO dao) {
		setDao(dao);
	}	
		
	@GET
	@Path("/emitente={emitente}&dataIni={dataIni}&dataFim={dataFim}&notaFiscal={notaFiscal}&dest={dest}")
	public NotasFiscais filtrar(@PathParam("emitente") Integer emitente, @PathParam("dataIni") long xDataIni,
			@PathParam("dataFim") long xDataFim, @PathParam("notaFiscal") String notaFiscal, @PathParam("dest") String dest) {
		
		Date dataIni = null;
		Date dataFim = null;
		if(xDataIni > -1){
			dataIni = new Date(xDataIni);
		}
		if(xDataFim > -1){
			dataFim = new Date(xDataFim);
		}
		
		Collection<NotaFiscal> entities = mDAO.pesquisar(emitente, dataIni, dataFim, notaFiscal, dest);
		for (NotaFiscal entity : entities) {
			entity.createStandardLinks();
		}
		NotasFiscais collection = encapsulateEntities(entities);
		return collection;
	}

}
