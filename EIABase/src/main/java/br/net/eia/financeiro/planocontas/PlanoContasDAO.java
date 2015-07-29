package br.net.eia.financeiro.planocontas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.net.eia.emitente.EmitenteDAO;
import br.net.eia.persistence.JpaDAO;

@Repository
public class PlanoContasDAO extends JpaDAO<PlanoContas>{
	
	@Autowired
	EmitenteDAO EDao;
	
	public PlanoContasDAO() {
		super(PlanoContas.class);
	}
}
