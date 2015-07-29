package br.net.eia.services;

import javax.ws.rs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.net.eia.financeiro.lancamento.Lancamento;
import br.net.eia.financeiro.lancamento.LancamentoDAO;
import br.net.eia.financeiro.lancamento.Lancamentos;

@Service
@Path("/lancamento")
public class LancamentoResource extends BaseService<Lancamento, Lancamentos> {
	
	public LancamentoResource() {
		super(Lancamentos.class);
	}
	
	@Autowired
	public void setPersonDAO(LancamentoDAO dao) {
		setDao(dao);
	}	
}