package br.net.eia.financeiro.centrocustos;

import org.springframework.beans.factory.annotation.Autowired;
import br.net.eia.emitente.EmitenteDAO;
import br.net.eia.persistence.JpaDAO;

public class CentroCustosDAO extends JpaDAO<CentroCustos>{
	
	@Autowired
	EmitenteDAO EDao;
	
	public CentroCustosDAO() {
		super(CentroCustos.class);
	}
}
