package br.net.eia.ui.compra;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.ws.rs.core.Response.Status;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.controlsfx.dialog.Dialogs;
import br.net.eia.compra.Compra;
import br.net.eia.compra.Compras;
import br.net.eia.emitente.Emitente;
import br.net.eia.ui.BaseClientService;
import br.net.eia.ui.MainApp;
import com.sun.jersey.api.client.ClientResponse;

public class RestCompraManager extends BaseClientService {

	{
		setResourcePath("/services/compra");
	}
	
	MainApp mainApp;
	
	public RestCompraManager(){
		
	}

	public RestCompraManager(MainApp mainAPP) {
		this.mainApp = mainAPP;
	}
	
	public Compra inserir(Compra c) {
		ClientResponse clientResponse = resource().accept("application/xml")
				.post(ClientResponse.class, c);
		String location = clientResponse.getHeaders().getFirst("Location");
		location = location.substring(getPath().length()
				+ getResourcePath().length());
		Compra newPerson = resource().path(location).get(Compra.class);
		return newPerson;
	}

	public ObservableList<Compra> getAll(Emitente emit) {
		Compras compras = null;
		try {
			ClientResponse clientResponse = resource().path("/emitente=".concat(emit.getNumDoc()))
					.accept("application/xml").get(ClientResponse.class);
			if (clientResponse.getStatus() != 200) {
				Logger.getLogger(getClass().getName()).log(
						Level.ERROR, "Falha : codigo do erro HTTP: "+ clientResponse.getStatus(), null);
				Dialogs.create()
				.owner(mainApp.getPrimaryStage())
				.title("Erro")
				.masthead("Falha ao carregar")
				.message("Descrição: "+clientResponse.getClientResponseStatus())
				.showError();
			}
			compras = clientResponse.getEntity(Compras.class);

		} catch (Exception e) {

			Logger.getLogger(getClass().getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);

		}
		return FXCollections.observableArrayList(compras.getEntities());
	}

	public Compra atualizar(Compra cliente) {

		Compra cAlterado = resource().put(Compra.class, cliente);

		return cAlterado;

	}
	
	public Compra atualizarEntrada(Compra compra) {
		
		if(compra.getId()==null){
			compra=inserir(compra);
		}
		ClientResponse clientResponse = resource().path("/atualizarEntrada").accept("application/xml")
				.put(ClientResponse.class, compra);
		
		Compra compraAtualizada = new Compra();
		
		if(clientResponse.getClientResponseStatus() == ClientResponse.Status.OK){
			
			String location = "/"+compra.getId();
			compraAtualizada = resource().path(location).get(Compra.class);
		}else{
			Dialogs.create()
			.owner(mainApp.getPrimaryStage())
			.title("Aviso")
			.masthead("Descrição: "+clientResponse.getClientResponseStatus())
			.message("Erro na operação.")
			.showError();
		}
		return compraAtualizada;

	}
	
public boolean atualizarRemover(Compra compra) {
		
		ClientResponse clientResponse = resource().path("/atualizarRemover").accept("application/xml")
				.put(ClientResponse.class, compra);
		
		if(clientResponse.getClientResponseStatus() == ClientResponse.Status.OK){
			return true;
		}else{
			Dialogs.create()
			.owner(mainApp.getPrimaryStage())
			.title("Aviso")
			.masthead("Descrição: "+clientResponse.getClientResponseStatus())
			.message("Erro na operação.")
			.showError();
		}
		return false;

	}

	public boolean remover(Compra cliente) {

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

	public ObservableList<Compra> filtrar(Emitente emitente, Date dataIni, Date dataFim,
			String pedido, String notaFiscal, String fornecedor) {
		long lDataIni = -1;
		long lDataFim = -1;
		if(dataIni!=null){
			lDataIni=dataIni.getTime();
		}
		if(dataFim!=null){
			lDataFim=dataFim.getTime();
		}
		if(pedido.isEmpty()){
			pedido="todos";
		}
		if(notaFiscal.isEmpty()){
			notaFiscal="todos";
		}
		if(fornecedor.isEmpty()){
			fornecedor="todos";
		}
		Compras compras = null;
		try {
			StringBuilder stb = new StringBuilder("/");
			stb.append("emitente=").append(emitente.getNumDoc());
			stb.append("&dataIni=").append(lDataIni);
			stb.append("&dataFim=").append(lDataFim);
			stb.append("&pedido=").append(pedido);
			stb.append("&notaFiscal=").append(notaFiscal);
			stb.append("&fornecedor=").append(fornecedor);
			
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
			compras = clientResponse.getEntity(Compras.class);

		} catch (Exception e) {

			Logger.getLogger(getClass().getName()).log(
					Level.ERROR, e.getLocalizedMessage(), e);

		}
		Collection<Compra> lCompras = compras.getEntities();
		if(lCompras==null){
			lCompras = new ArrayList<Compra>();
		}
		return FXCollections.observableArrayList(lCompras);
	}
}
