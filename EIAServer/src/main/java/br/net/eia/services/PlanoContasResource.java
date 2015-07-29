package br.net.eia.services;

import javax.ws.rs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.net.eia.financeiro.planocontas.PlanoContas;
import br.net.eia.financeiro.planocontas.PlanoContasDAO;
import br.net.eia.financeiro.planocontas.PlanosContas;

@Service
@Path("/planoContas")
public class PlanoContasResource extends BaseService<PlanoContas, PlanosContas> {
	
	public PlanoContasResource() {
		super(PlanosContas.class);
	}
	
	@Autowired
	public void setPersonDAO(PlanoContasDAO dao) {
		setDao(dao);
	}	
}