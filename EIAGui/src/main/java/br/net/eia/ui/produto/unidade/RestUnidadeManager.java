package br.net.eia.ui.produto.unidade;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.ws.rs.core.Response.Status;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import br.net.eia.emitente.Emitente;
import br.net.eia.produto.unidade.Unidade;
import br.net.eia.produto.unidade.Unidades;
import br.net.eia.ui.BaseClientService;
import com.sun.jersey.api.client.ClientResponse;

@SuppressWarnings("restriction")
public class RestUnidadeManager extends BaseClientService {

	{
		setResourcePath("/services/unidade");
	}

	public Unidade inserir(Unidade c) {
		ClientResponse clientResponse = resource().accept("application/xml")
				.post(ClientResponse.class, c);		
		String location = clientResponse.getHeaders().getFirst("Location");
		location = location.substring(getPath().length()
				+ getResourcePath().length());
		Unidade newPerson = resource().path(location).get(Unidade.class);
		return newPerson;
	}

	public ObservableList<Unidade> getAll(Emitente emit) {
		Unidades municipios = null;
		try {
			ClientResponse clientResponse = resource().path("/emitente=".concat(emit.getToken().toString()))
					.accept("application/xml").get(ClientResponse.class);
			if (clientResponse.getStatus() != 200) {
				System.out.println("Falha : codigo do erro HTTP: "
						+ clientResponse.getStatus());
			}
			municipios = clientResponse.getEntity(Unidades.class);
			if(municipios.getEntities() == null){
				return FXCollections.observableArrayList();
			}
			

		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);

		}
		return FXCollections.observableArrayList(municipios.getEntities());
	}

	public Unidade atualizar(Unidade cliente) {

		Unidade cAlterado = resource().put(Unidade.class, cliente);

		return cAlterado;

	}

	public boolean remover(Unidade cliente) {

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
	
	public ObservableList<Unidade> filtrar(Emitente emitente) {
		Unidades results = null;
		
		try {
			StringBuilder stb = new StringBuilder("/");
			stb.append("emitente=").append(emitente.getToken().toString());
			
			String path = stb.toString();
			if(path.isEmpty()){
				path="/emitente=".concat(emitente.getToken().toString());
			}
			ClientResponse clientResponse = resource().path(path)
					.accept("application/xml").get(ClientResponse.class);
			if (clientResponse.getStatus() != 200) {
				System.out.println("Falha : codigo do erro HTTP: "
						+ clientResponse.getStatus());
			}
			results = clientResponse.getEntity(Unidades.class);

		} catch (Exception e) {

			Logger.getLogger(getClass().getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);

		}
		if(results==null){
			results = new Unidades();
		}
		return FXCollections.observableArrayList(results.getEntities());
	}
}
