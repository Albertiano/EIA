package br.net.eia.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.net.eia.app.util.Transactional;
import br.net.eia.model.pais.Pais;
import br.net.eia.repository.Paises;

public class CadastroPaises implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Paises paises;
	
	public void salvar(Pais p) throws NegocioException {
		
	}
}
