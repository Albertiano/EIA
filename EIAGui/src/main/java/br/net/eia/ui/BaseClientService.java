package br.net.eia.ui;

import br.net.eia.ui.emitente.RestEmitenteManager;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.ClientFilter;

import java.net.URI;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriBuilder;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.security.crypto.codec.Base64;

public abstract class BaseClientService {

	public final String CONTEXT_PATH = "EIAServer";
	String resourcePath;

	protected String getPath() {
		return getBaseURI().toASCIIString() + CONTEXT_PATH;
	}

	protected URI getBaseURI() {
		String host = MainApp.getProps().getProperty("host");
		String port = MainApp.getProps().getProperty("porta");
		int porta = 8080;
		if(host==null){
			host = "http://ec2-52-24-90-238.us-west-2.compute.amazonaws.com/";
		}
		if(port!=null){
			porta = Integer.valueOf(port);
		}
		return UriBuilder.fromUri(host).port(porta).build();
	}

	public WebResource resource() {
		Client client = Client.create();
		client.addFilter(new ClientFilter() {

			@Override
			public ClientResponse handle(ClientRequest cr) {
				try {
					cr.getHeaders().add(
							HttpHeaders.AUTHORIZATION,
							"Basic " + new String(Base64.encode("user:2010".getBytes())));
					return getNext().handle(cr);
				} catch (ClientHandlerException e) {
					Logger.getLogger(RestEmitenteManager.class.getName()).log(
							Level.ERROR, e.getLocalizedMessage(), e);
				}
				return null;
			}
		});
		return client.resource(getPath() + getResourcePath());
	}

	protected String getResourcePath() {
		return resourcePath;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}

}
