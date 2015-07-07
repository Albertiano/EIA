package br.net.eia.ui.contato;

import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.sun.jersey.api.client.ClientResponse;

import br.net.eia.contato.Contato;
import br.net.eia.contato.Contatos;
import br.net.eia.emitente.Emitente;
import br.net.eia.ui.BaseClientService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@SuppressWarnings("restriction")
public class RestContatoManager extends BaseClientService {

	{
		setResourcePath("/services/contato");
	}

	public Contato inserir(Contato c) {
		ClientResponse clientResponse = resource().accept("application/xml")
				.post(ClientResponse.class, c);		
		String location = clientResponse.getHeaders().getFirst("Location");		
		location = location.substring(getPath().length()
				+ getResourcePath().length());
		Contato newPerson = resource().path(location).get(Contato.class);
		return newPerson;
	}
	
	public Contato pesquisaNumDoc(String token, String numDoc) {
Contato newPerson = resource().path("/token="+token+"&numDoc="+numDoc).get(Contato.class);
		return newPerson;
	}

	public ObservableList<Contato> getAll(Emitente emit) {
		Contatos municipios = null;
		try {
			ClientResponse clientResponse = resource().path("/emitente=".concat(emit.getNumDoc()))
					.accept("application/xml").get(ClientResponse.class);
			if (clientResponse.getStatus() != 200) {
				System.out.println("Falha : codigo do erro HTTP: "
						+ clientResponse.getStatus());
			}
			municipios = clientResponse.getEntity(Contatos.class);

		} catch (Exception e) {

			Logger.getLogger(getClass().getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);

		}
		return FXCollections.observableArrayList(municipios.getEntities());
	}

	public Contato atualizar(Contato cliente) {

		Contato cAlterado = resource().put(Contato.class, cliente);

		return cAlterado;

	}

	public boolean remover(Contato cliente) {

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
	
	public ObservableList<Contato> filtrar(String tpContato, Emitente emitente, String nome, String pais, String xUf, String xMunicipio) {
		Contatos results = null;
		
		if(tpContato.isEmpty()){
			tpContato="todos";
		}
		
		if(nome.isEmpty()){
			nome="todos";
		}
		if(pais.equalsIgnoreCase("null")){
			pais="todos";
		}
		if(xUf.equalsIgnoreCase("null")){
			xUf="todos";
		}
		if(xMunicipio.equalsIgnoreCase("null")){
			xMunicipio="todos";
		}
		try {
			StringBuilder stb = new StringBuilder("/");
			stb.append("tpContato=").append(tpContato);
			stb.append("&emitente=").append(emitente.getToken().toString());
			stb.append("&nome=").append(nome);
			stb.append("&pais=").append(pais);
			stb.append("&uf=").append(xUf);
			stb.append("&municipio=").append(xMunicipio);
			
			String path = stb.toString();
			if(path.isEmpty()){
				path="/emitente=".concat(emitente.getNumDoc());
			}
			
			ClientResponse clientResponse = resource().path(path)
					.accept("application/xml").get(ClientResponse.class);
			if (clientResponse.getStatus() != 200) {
				System.out.println("Falha : codigo do erro HTTP: "
						+ clientResponse.getStatus());
			}
			results = clientResponse.getEntity(Contatos.class);

		} catch (Exception e) {

			Logger.getLogger(getClass().getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);

		}
		return FXCollections.observableArrayList(results.getEntities());
	}
}
