package br.net.eia.controller;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.net.eia.model.municipio.Municipio;
import br.net.eia.repository.Municipios;

@Named
@RequestScoped
public class MunicipioBean{

	@Inject
	private Municipios municipiosRepo;
	
	private List<Municipio> municipios;
	
	public void listar(){
		this.municipios = (List<Municipio>) municipiosRepo.retrieve();
	}
	
	public List<Municipio> getMunicipios() {
		return municipios;
	}

	public void setMunicipios(List<Municipio> municipios) {
		this.municipios = municipios;
	}
	
}
