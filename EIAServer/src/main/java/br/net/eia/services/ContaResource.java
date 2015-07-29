package br.net.eia.services;

import javax.ws.rs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.net.eia.financeiro.conta.Conta;
import br.net.eia.financeiro.conta.ContaDAO;
import br.net.eia.financeiro.conta.Contas;

@Service
@Path("/conta")
public class ContaResource extends BaseService<Conta, Contas> {
	
	public ContaResource() {
		super(Contas.class);
	}
	
	@Autowired
	public void setPersonDAO(ContaDAO dao) {
		setDao(dao);
	}	
}