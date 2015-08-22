package br.net.eia.controller;

import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;

import br.net.eia.model.pais.Pais;
import br.net.eia.repository.Paises;

@Named
@javax.faces.view.ViewScoped
public class PaisBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Inject
	private Paises paisesRepo;
	
	private List<Pais> paises;
	
	public void listar(){
		this.paises = (List<Pais>) paisesRepo.retrieve();
		System.out.println("---------------------"+paises.size());
	}

	public List<Pais> getPaises() {
		return paises;
	}

	public void setPaises(List<Pais> paises) {
		this.paises = paises;
	}

}
