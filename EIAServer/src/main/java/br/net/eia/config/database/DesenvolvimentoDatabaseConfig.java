package br.net.eia.config.database;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import br.net.eia.config.annotation.Desenvolvimento;

@Desenvolvimento
@Configuration
@EnableTransactionManagement
public class DesenvolvimentoDatabaseConfig implements IDatabaseConfig {
	/** 
	 * Postgressql
	 *
	 
	 static String DRIVER_CLASS = "org.postgresql.Driver"; 
	 static String URL = "jdbc:postgresql://localhost:5432/postgres?tcpkeepalive=true";
	 static String USERNAME = "postgres"; 
	 static String PASSWD = "2010";
	 static String DIALECT = "org.hibernate.dialect.PostgreSQLDialect";
	 static Database DATABASE = Database.POSTGRESQL;
	 */
	
	/**
	 * H2 
	 *  
	 * 
	 *  */	
	static String DRIVER_CLASS = "org.h2.Driver";
	static String URL = "jdbc:h2:eia";
	static String USERNAME = "eia";
	static String PASSWD = "2010";	
	static String DIALECT = "org.hibernate.dialect.H2Dialect";
	static Database DATABASE = Database.H2;
	
	 
	 /** 
		 * MySql
		 *
		
		 static String DRIVER_CLASS = "com.mysql.jdbc.Driver"; 
		 static String URL = "jdbc:mysql://localhost:3307/albertiano?autoReconnect=true";
		 static String USERNAME = "albertiano"; 
		 static String PASSWD = "Erick20102009";
		 static String DIALECT = "org.hibernate.dialect.MySQLDialect";
		 static Database DATABASE = Database.MYSQL;
 */
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(DRIVER_CLASS);
		dataSource.setUrl(URL);
		dataSource.setUsername(USERNAME);
		dataSource.setPassword(PASSWD);

		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factoryBean
			       = new LocalContainerEntityManagerFactoryBean();
			      factoryBean.setDataSource( this.dataSource() );
			      factoryBean.setPackagesToScan( new String[ ] { "br.net.eia" } );			       
			      factoryBean.setJpaVendorAdapter(this.jpaAdapter());
			      factoryBean.setJpaProperties( this.additionlProperties() );
			 
			      return factoryBean;
	}

	private Properties additionlProperties() {
		Properties p = new Properties();
		p.setProperty("hibernate.dialect", DIALECT);
		p.setProperty("hibernate.hbm2ddl.auto", "update");
		p.setProperty("hibernate.ejb.naming_strategy","org.hibernate.cfg.ImprovedNamingStrategy");
		p.setProperty("hibernate.connection.charSet","UTF-8");
		p.setProperty("hibernate.session_factory_name","atec");
		p.setProperty("hibernate.show_sql","false");
		p.setProperty("hibernate.format_sql","true");
		return p;
	}

	@Bean
	public JpaVendorAdapter jpaAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(true);
		hibernateJpaVendorAdapter.setDatabase(DATABASE);
		return hibernateJpaVendorAdapter;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(this.entityManagerFactory()
				.getObject());

		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
}