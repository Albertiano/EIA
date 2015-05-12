package br.net.eia.config.database;

import javax.sql.DataSource;

import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

public interface IDatabaseConfig {

	
	public abstract DataSource dataSource();

	
	public abstract LocalContainerEntityManagerFactoryBean entityManagerFactory();

	
	public abstract PlatformTransactionManager transactionManager();

	
	public abstract PersistenceExceptionTranslationPostProcessor exceptionTranslation();

}