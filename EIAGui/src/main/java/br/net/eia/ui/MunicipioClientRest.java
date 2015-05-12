package br.net.eia.ui;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import br.net.eia.municipio.Municipio;
import br.net.eia.municipio.Municipios;

import com.sun.jersey.api.client.ClientResponse;

public class MunicipioClientRest extends BaseClientService {
	public MunicipioClientRest() {
		setResourcePath("/services/municipio");
	}
	
	public ObservableList<Municipio> getAll() {
		Municipios municipios = null;
        try {
            ClientResponse clientResponse = resource().path("/all")
                    .accept("application/xml").get(ClientResponse.class);
            if (clientResponse.getStatus() != 200) {
                System.out.println("Falha : codigo do erro HTTP: "
                        + clientResponse.getStatus());
            }
            municipios = clientResponse.getEntity(Municipios.class);
            

        } catch (Exception e) {

            Logger.getLogger(getClass().getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);

        }
		return FXCollections.observableArrayList(municipios.getEntities());
    }
	
	public ObservableList<Municipio> getAllUF(String uf) {
		Municipios municipios = null;
        try {
            ClientResponse clientResponse = resource().path("/UF."+uf)
                    .accept("application/xml").get(ClientResponse.class);
            if (clientResponse.getStatus() != 200) {System.out.println(getPath());
                System.out.println("Falha : codigo do erro HTTP: "
                        + clientResponse.getStatus());
            }
            municipios = clientResponse.getEntity(Municipios.class);
            

        } catch (Exception e) {

            Logger.getLogger(getClass().getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);

        }
		return FXCollections.observableArrayList(municipios.getEntities());
    }
	
	public Municipio carregar(Integer cMun){
		Municipio m = resource().path("/cMun="+cMun).get(Municipio.class);
		return m;		
	}
}
