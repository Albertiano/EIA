package br.net.eia.financeiro.planocontas;

import org.springframework.beans.factory.annotation.Autowired;
import br.net.eia.emitente.EmitenteDAO;
import br.net.eia.persistence.JpaDAO;

public class PlanoContasDAO extends JpaDAO<PlanoContas>{
	
	@Autowired
	EmitenteDAO EDao;
	
	public PlanoContasDAO() {
		super(PlanoContas.class);
	}
}
