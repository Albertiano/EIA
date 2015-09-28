package br.net.eia.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import br.net.eia.model.pais.Pais;
import br.net.eia.repository.Paises;

@Named
@RequestScoped
public class PaisBean{

	@Inject
	private Paises paisesRepo;
	
	private List<Pais> paises;
	
	private javax.servlet.http.Part arquivo;
	
	public void listar(){
		this.paises = (List<Pais>) paisesRepo.retrieve();
	}
	
	public void install(){
		List<Pais> paises = new ArrayList<Pais>();
		try {
			paises = new PaisXML().realizaLeituraXML(arquivo.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Pais p : paises) {
			paisesRepo.create(p);
		}
	}

	public javax.servlet.http.Part getArquivo() {
		return arquivo;
	}

	public void setArquivo(javax.servlet.http.Part arquivo) {
		this.arquivo = arquivo;
	}

	public List<Pais> getPaises() {
		return paises;
	}

	public void setPaises(List<Pais> paises) {
		this.paises = paises;
	}

}
