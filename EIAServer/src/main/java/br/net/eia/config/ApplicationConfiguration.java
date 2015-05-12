package br.net.eia.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import br.net.eia.logger.GeradorLog;
import com.sun.jersey.spi.spring.container.servlet.SpringServlet;

/**
 * This class implements all the configuration that would otherwise be placed
 * inside web.xml. the steps taken in the configuration are:
 * <ul>
 * <li>Bootstrap Spring Container
 * <li>Register Shiro Security Filter
 * <li>Register Jersey REST servlet
 * </ul>
 * 
 * @author victor
 * @see WebApplicationInitializer
 */
public class ApplicationConfiguration implements WebApplicationInitializer {

	

	// REST services are mapped only with this root
	private static final String JERSEY_SERVLET_MAPPING = "/services/*";

	public void onStartup(final ServletContext servletContext)
			throws ServletException {

		GeradorLog.inicialize();

		AnnotationConfigWebApplicationContext root = null;

		// Bootstrap
		root = bootstrapSpring(servletContext);
		// Security
		// registerSecurityFilter(servletContext, root);
		// REST
		registerJerseyServlet(servletContext, root);
	}

	// Initializes a Configurable Bean Context
	private AnnotationConfigWebApplicationContext bootstrapSpring(
			ServletContext servletContext) {

		final AnnotationConfigWebApplicationContext root = new AnnotationConfigWebApplicationContext();
		root.setServletContext(servletContext); // registers the servlet context
		root.register(SpringConfiguration.class); // registers our configuration
		root.getEnvironment().setActiveProfiles("desenvolvimento");
		root.refresh(); // refreshes all beans

		return root;
	}

	private void registerJerseyServlet(final ServletContext servletContext,
			final AnnotationConfigWebApplicationContext root) {

		// Adds the Slightly modified Jersey Spring Servlet
		final ServletRegistration.Dynamic jerseyServlet = servletContext
				.addServlet(SpringServlet.class.getName(),
						new JerseyConfiguration(root));
		jerseyServlet.setLoadOnStartup(1);
		jerseyServlet.addMapping(JERSEY_SERVLET_MAPPING);

		jerseyServlet.setInitParameter(
				"com.sun.jersey.api.json.POJOMappingFeature", "true");
	}

	private void registerSecurityFilter(final ServletContext servletContext,
			final AnnotationConfigWebApplicationContext root) {

		// Register Spring security filter
			final	FilterRegistration.Dynamic springSecurityFilterChain = 
						servletContext.addFilter("springSecurityFilterChain", new DelegatingFilterProxy(
								"springSecurityFilterChain", root));
				springSecurityFilterChain.setInitParameter("targetFilterLifecycle",
						Boolean.TRUE.toString());
				springSecurityFilterChain.addMappingForUrlPatterns(null, false,
						JERSEY_SERVLET_MAPPING);
	}

}
