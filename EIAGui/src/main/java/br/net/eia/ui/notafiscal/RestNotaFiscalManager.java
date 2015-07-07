package br.net.eia.ui.notafiscal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import br.net.eia.emitente.Emitente;
import br.net.eia.notafiscal.NotaFiscal;
import br.net.eia.notafiscal.NotasFiscais;
import br.net.eia.ui.BaseClientService;
import br.net.eia.ui.MainApp;

import com.sun.jersey.api.client.ClientResponse;

@SuppressWarnings("restriction")
public class RestNotaFiscalManager extends BaseClientService {

	{
		setResourcePath("/services/notaFiscal");
	}

	MainApp mainApp;

	public RestNotaFiscalManager() {

	}

	public RestNotaFiscalManager(MainApp mainAPP) {
		this.mainApp = mainAPP;
	}

	public NotaFiscal inserir(NotaFiscal c) {
		ClientResponse clientResponse = resource().accept("application/xml")
				.post(ClientResponse.class, c);
		//String location = clientResponse.getHeaders().getFirst("Location");
		//location = location.substring(getPath().length()+ getResourcePath().length());
		//NotaFiscal newPerson = resource().path(location).get(NotaFiscal.class);
		return null;
	}


	public NotaFiscal atualizar(NotaFiscal cliente) {
		NotaFiscal cAlterado = resource().put(NotaFiscal.class, cliente);
		return cAlterado;

	}

	public boolean remover(NotaFiscal cliente) {
		ClientResponse clientResponse = resource().path(
				cliente.getId().toString()).delete(ClientResponse.class);
		if (clientResponse.getStatus() == Status.OK.getStatusCode()) {
			return true;
		} else {
			Logger.getLogger(getClass().getName()).log(
					Level.ERROR,
					"Erro: "
							+ clientResponse.getClientResponseStatus()
									.getStatusCode() + " "
							+ clientResponse.getClientResponseStatus()
							+ clientResponse.getLocation());
		}
		return false;
	}
	
	public ObservableList<NotaFiscal> filtrar(Emitente emitente, Date dataIni, Date dataFim,
			String notaFiscal, String dest) {
		
		long lDataIni = -1;
		long lDataFim = -1;
		if (dataIni != null) {
			lDataIni = dataIni.getTime();
		}
		if (dataFim != null) {
			lDataFim = dataFim.getTime();
		}
		if (notaFiscal.isEmpty()) {
			notaFiscal = "todos";
		}
		if (dest.isEmpty()) {
			dest = "todos";
		}
		NotasFiscais compras = null;
		try {
			StringBuilder stb = new StringBuilder("/");
			stb.append("emitente=").append(emitente.getToken().toString());
			stb.append("&dataIni=").append(lDataIni);
			stb.append("&dataFim=").append(lDataFim);
			stb.append("&notaFiscal=").append(notaFiscal);
			stb.append("&dest=").append(dest);

			String path = stb.toString();
			if (path.isEmpty()) {
				path = "/emitente=".concat(emitente.getToken().toString());
			}

			ClientResponse clientResponse = resource().path(path)
					.accept("application/xml").get(ClientResponse.class);
			if (clientResponse.getStatus() != 200) {				
				System.out.println("Falha : codigo do erro HTTP: "
						+ clientResponse.getStatus());
			}
			compras = clientResponse.getEntity(NotasFiscais.class);

		} catch (Exception e) {

			Logger.getLogger(getClass().getName()).log(Level.ERROR,
					e.getLocalizedMessage(), e);

		}
		Collection<NotaFiscal> lCompras = compras.getEntities();
		if (lCompras == null) {
			lCompras = new ArrayList<NotaFiscal>();
		}
		return FXCollections.observableArrayList(lCompras);
	}

}
