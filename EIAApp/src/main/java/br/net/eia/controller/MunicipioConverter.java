package br.net.eia.controller;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.net.eia.model.municipio.Municipio;
import br.net.eia.repository.Municipios;

@FacesConverter(forClass = Municipio.class, value="municipioConverter")
public class MunicipioConverter implements Converter {
	
	@Inject
	private Municipios municipioRepo;

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if(value == null || value.isEmpty()){
            return null;
        }else{
            Long id = Long.parseLong(value);
            Municipio m = municipioRepo.retrieve(id);
            return m;
        }
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		 Municipio m = (Municipio) value;
	        if(m != null){
	            return String.valueOf(m.getId());
	        }else{
	            return null;
	        }
	}

}
