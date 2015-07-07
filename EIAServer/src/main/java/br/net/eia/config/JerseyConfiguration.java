package br.net.eia.config;

import org.springframework.context.ConfigurableApplicationContext;

import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.spi.container.WebApplication;
import com.sun.jersey.spi.spring.container.SpringComponentProviderFactory;
import com.sun.jersey.spi.spring.container.servlet.SpringServlet;

public class JerseyConfiguration extends SpringServlet {

	private static final long serialVersionUID = 1L;
	private final ConfigurableApplicationContext cac;

	public JerseyConfiguration(ConfigurableApplicationContext cac) {
		this.cac = cac;
	}

	@Override
	protected void initiate(ResourceConfig rc, WebApplication wa) {
		// May throw RuntimeException
		wa.initiate(rc, new SpringComponentProviderFactory(rc, cac));
	}
}
