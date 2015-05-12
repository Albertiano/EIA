package br.net.eia.ui.produto.imposto;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.ws.rs.core.Response.Status;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import br.net.eia.emitente.Emitente;
import br.net.eia.produto.imposto.Tributacao;
import br.net.eia.produto.imposto.Tributacoes;
import br.net.eia.ui.BaseClientService;
import com.sun.jersey.api.client.ClientResponse;

@SuppressWarnings("restriction")
public class RestTributacaoManager extends BaseClientService {

	{
		setResourcePath("/services/tributacao");
	}

	public Tributacao inserir(Tributacao c) {
		ClientResponse clientResponse = resource().accept("application/xml")
				.post(ClientResponse.class, c);
		String location = clientResponse.getHeaders().getFirst("Location");
		location = location.substring(getPath().length() + getResourcePath().length());
		Tributacao newPerson = resource().path(location).get(Tributacao.class);
		return newPerson;
	}

	public ObservableList<Tributacao> getAll(Emitente emit) {
		Tributacoes municipios = null;
		try {
			ClientResponse clientResponse = resource().path("/emitente=".concat(emit.getToken().toString()))
					.accept("application/xml").get(ClientResponse.class);
			if (clientResponse.getStatus() != 200) {
				System.out.println("Falha : codigo do erro HTTP: "
						+ clientResponse.getStatus());
			}
			municipios = clientResponse.getEntity(Tributacoes.class);

		} catch (Exception e) {

			Logger.getLogger(getClass().getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);

		}
		return FXCollections.observableArrayList(municipios.getEntities());
	}

	public Tributacao atualizar(Tributacao cliente) {

		Tributacao cAlterado = resource().put(Tributacao.class, cliente);

		return cAlterado;

	}

	public boolean remover(Tributacao cliente) {
try{

		ClientResponse clientResponse = resource().path(
				cliente.getId().toString()).delete(ClientResponse.class);

		if (clientResponse.getStatus() == Status.OK.getStatusCode()) {

			return true;
		} else {
			System.out.println("Erro: "
					+ clientResponse.getClientResponseStatus().getStatusCode()
					+ " " + clientResponse.getClientResponseStatus()
					+clientResponse.getLocation());
		}
	
		
	}catch(Exception e){
		Logger.getLogger(getClass().getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);
	}
		return false;

	}
	
	
}
