package br.net.eia.services;

import javax.ws.rs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.net.eia.financeiro.banco.Banco;
import br.net.eia.financeiro.banco.BancoDAO;
import br.net.eia.financeiro.banco.Bancos;

@Service
@Path("/banco")
public class BancoResource extends BaseService<Banco, Bancos> {
	
	public BancoResource() {
		super(Bancos.class);
	}
	
	@Autowired
	public void setPersonDAO(BancoDAO dao) {
		setDao(dao);
	}	
}
