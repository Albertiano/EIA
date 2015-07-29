package br.net.eia.financeiro.conta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.net.eia.emitente.EmitenteDAO;
import br.net.eia.persistence.JpaDAO;

@Repository
public class ContaDAO extends JpaDAO<Conta>{
	
	@Autowired
	EmitenteDAO EDao;
	
	public ContaDAO() {
		super(Conta.class);
	}
}
