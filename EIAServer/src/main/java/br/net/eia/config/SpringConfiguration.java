package br.net.eia.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("br.net.eia")
@Import({ //SecurityConfiguration.class, //
		BeanValidationConfiguration.class, //
		RepositoryConfiguration.class })
public class SpringConfiguration {
}
