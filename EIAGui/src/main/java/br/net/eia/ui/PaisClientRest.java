package br.net.eia.ui;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import br.net.eia.pais.Pais;
import br.net.eia.pais.Paises;

import com.sun.jersey.api.client.ClientResponse;

public class PaisClientRest extends BaseClientService {
	public PaisClientRest() {
		setResourcePath("/services/pais");
	}
	
	public ObservableList<Pais> getAll() {
		Paises paises = null;
        try {
            ClientResponse clientResponse = resource().path("/all")
                    .accept("application/xml").get(ClientResponse.class);
            if (clientResponse.getStatus() != 200) {
                System.out.println("Falha : codigo do erro HTTP: "
                        + clientResponse.getStatus());
            }
            paises = clientResponse.getEntity(Paises.class);
            

        } catch (Exception e) {

            Logger.getLogger(getClass().getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);

        }
		return FXCollections.observableArrayList(paises.getEntities());
    }
}
