package br.net.eia.financeiro.lancamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.net.eia.emitente.EmitenteDAO;
import br.net.eia.persistence.JpaDAO;

@Repository
public class LancamentoDAO extends JpaDAO<Lancamento>{
	
	@Autowired
	EmitenteDAO EDao;
	
	public LancamentoDAO() {
		super(Lancamento.class);
	}
}
