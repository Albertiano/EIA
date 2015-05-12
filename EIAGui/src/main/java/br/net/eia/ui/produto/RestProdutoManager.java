package br.net.eia.ui.produto;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.ws.rs.core.Response.Status;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import br.net.eia.emitente.Emitente;
import br.net.eia.produto.Produto;
import br.net.eia.produto.Produtos;
import br.net.eia.ui.BaseClientService;
import com.sun.jersey.api.client.ClientResponse;

@SuppressWarnings("restriction")
public class RestProdutoManager extends BaseClientService {

	{
		setResourcePath("/services/produto");
	}

	public Produto inserir(Produto c) {
		ClientResponse clientResponse = resource().accept("application/xml")
				.post(ClientResponse.class, c);
		String location = clientResponse.getHeaders().getFirst("Location");
		location = location.substring(getPath().length()
				+ getResourcePath().length());
		Produto newPerson = resource().path(location).get(Produto.class);
		return newPerson;
	}


	public Produto atualizar(Produto cliente) {

		Produto cAlterado = resource().put(Produto.class, cliente);

		return cAlterado;

	}

	public boolean remover(Produto cliente) {
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
	
	public ObservableList<Produto> filtrar(Emitente emitente, String codigo, String descricao, String unidade) {
		Produtos results = null;
		
		if(codigo.isEmpty()){
			codigo="todos";
		}
		if(descricao.isEmpty()){
			descricao="todos";
		}
		if(unidade.isEmpty()){
			unidade="todos";
		}
		try {
			StringBuilder stb = new StringBuilder("/");
			stb.append("emitente=").append(emitente.getToken().toString());
			stb.append("&codigo=").append(codigo);
			stb.append("&descricao=").append(descricao);
			stb.append("&unidade=").append(unidade);
			
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
			results = clientResponse.getEntity(Produtos.class);

		} catch (Exception e) {

			Logger.getLogger(getClass().getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);

		}
		if(results==null){
			results = new Produtos();
		}
		return FXCollections.observableArrayList(results.getEntities());
	}
	
	public Produto pesquisar(Long idFornecedor ,String codFornecedor) {
		Produto newPerson = resource().path("/fornecedor="+idFornecedor+"&codFornecedor="+codFornecedor).get(Produto.class);
		return newPerson;
	}
	
	public Produto pesquisar(Emitente emitente, String codigo) {
		Produto newPerson = resource().path("/emitente=".concat(emitente.getToken().toString()).concat("&codigo="+codigo)).get(Produto.class);
		return newPerson;
	}
	
}
