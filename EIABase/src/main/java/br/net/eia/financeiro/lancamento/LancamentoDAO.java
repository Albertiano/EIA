package br.net.eia.financeiro.lancamento;

import org.springframework.beans.factory.annotation.Autowired;
import br.net.eia.emitente.EmitenteDAO;
import br.net.eia.persistence.JpaDAO;

public class LancamentoDAO extends JpaDAO<Lancamento>{
	
	@Autowired
	EmitenteDAO EDao;
	
	public LancamentoDAO() {
		super(Lancamento.class);
	}
}
