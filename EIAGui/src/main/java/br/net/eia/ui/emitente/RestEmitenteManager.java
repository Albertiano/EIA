package br.net.eia.ui.emitente;

import javax.ws.rs.core.Response.Status;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.controlsfx.dialog.Dialogs;
import com.sun.jersey.api.client.ClientResponse;
import br.net.eia.emitente.Emitente;
import br.net.eia.emitente.Emitentes;
import br.net.eia.ui.BaseClientService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@SuppressWarnings("restriction")
public class RestEmitenteManager extends BaseClientService {

	{
		setResourcePath("/services/emitente");
	}

	public Emitente inserirCliente(Emitente c) {
		ClientResponse clientResponse = resource().accept("application/xml")
				.post(ClientResponse.class, c);
		String location = clientResponse.getHeaders().getFirst("Location");
		location = location.substring(getPath().length()
				+ getResourcePath().length());
		Emitente newPerson = resource().path(location).get(Emitente.class);
		return newPerson;
	}

	public ObservableList<Emitente> getAll() {
		Emitentes municipios = null;
		try {
			ClientResponse clientResponse = resource().path("/all")
					.accept("application/xml").get(ClientResponse.class);
			if (clientResponse.getStatus() != 200) {
				System.out.println("Falha : codigo do erro HTTP: "
						+ clientResponse.getStatus());
			}
			municipios = clientResponse.getEntity(Emitentes.class);

		} catch (Exception e) {

			Logger.getLogger(getClass().getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);

		}
		return FXCollections.observableArrayList(municipios.getEntities());
	}

	public Emitente atualizarCliente(Emitente cliente) {

		Emitente cAlterado = resource().put(Emitente.class, cliente);

		return cAlterado;

	}

	public boolean removerEmitente(Emitente cliente) {

		ClientResponse clientResponse = resource().path(
				cliente.getId().toString()).delete(ClientResponse.class);

		if (clientResponse.getStatus() == Status.OK.getStatusCode()) {

			System.out.println("Removido com sucesso, Codigo: "
					+ clientResponse.getClientResponseStatus().getStatusCode());
			return true;
		} else {
			System.out.println("Erro: "
					+ clientResponse.getClientResponseStatus().getStatusCode()
					+ " " + clientResponse.getClientResponseStatus()
					+clientResponse.getLocation());
		}
		return false;

	}

	public Emitente carregar(Integer token) {
		Emitente results = null;

		try {
			StringBuilder stb = new StringBuilder("/");
			stb.append("token=").append(token);

			String path = stb.toString();
			if (path.isEmpty()) {
				path = "/all";
			}
				ClientResponse clientResponse = resource().path(path)
						.accept("application/xml").get(ClientResponse.class);
				if (clientResponse.getStatus() != 200) {
					System.out.println("URL: "+path);
					System.out.println("Falha : codigo do erro HTTP: "
							+ clientResponse.getStatus());
					
				}
				results = clientResponse.getEntity(Emitente.class);			
		} catch (Exception e) {
			Logger.getLogger(RestEmitenteManager.class.getName()).log(
					Level.ERROR, "Erro ao Carregar o Emitente", e);
			Dialogs.create()			
            .title("Erro ao Carregar o Emitente")
            .message("Erro ao Carregar o Emitente").showException(e);
		}
		return results;
	}
}
