package br.net.eia.ui.nfe;

import javax.ws.rs.core.Response.Status;
import br.net.eia.nfe.NFe;
import br.net.eia.ui.BaseClientService;
import com.sun.jersey.api.client.ClientResponse;
public class RestNFeManager extends BaseClientService {

	{
		setResourcePath("/services/nfe");
	}

	public NFe inserir(NFe c) {
		ClientResponse clientResponse = resource().accept("application/xml")
				.post(ClientResponse.class, c);		
		String location = clientResponse.getHeaders().getFirst("Location");		
		location = location.substring(getPath().length()
				+ getResourcePath().length());
		NFe newPerson = resource().path(location).get(NFe.class);
		return newPerson;
	}
	
	public NFe pesquisaChave(String chave) {
		NFe newPerson = null;
		try{
			newPerson = resource().path("/chave="+chave).get(NFe.class);
		}catch(Exception e){
			System.out.println("NFe: "+chave);
			System.out.println("Não Encontrada");
		}
		
		return newPerson;
	}

	public NFe atualizar(NFe cliente) {

		NFe cAlterado = resource().put(NFe.class, cliente);

		return cAlterado;

	}

	public boolean remover(NFe cliente) {

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
}
