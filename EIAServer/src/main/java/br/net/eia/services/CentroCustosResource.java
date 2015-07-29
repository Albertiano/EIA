package br.net.eia.services;

import javax.ws.rs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.net.eia.financeiro.centrocustos.CentroCustos;
import br.net.eia.financeiro.centrocustos.CentroCustosDAO;
import br.net.eia.financeiro.centrocustos.CentrosCustos;

@Service
@Path("/centroCustos")
public class CentroCustosResource extends BaseService<CentroCustos, CentrosCustos> {
	
	public CentroCustosResource() {
		super(CentrosCustos.class);
	}
	
	@Autowired
	public void setPersonDAO(CentroCustosDAO dao) {
		setDao(dao);
	}	
}
