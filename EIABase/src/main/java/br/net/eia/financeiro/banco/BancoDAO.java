package br.net.eia.financeiro.banco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.net.eia.emitente.EmitenteDAO;
import br.net.eia.persistence.JpaDAO;

@Repository
public class BancoDAO extends JpaDAO<Banco>{
	
	@Autowired
	EmitenteDAO EDao;
	
	public BancoDAO() {
		super(Banco.class);
	}
}
