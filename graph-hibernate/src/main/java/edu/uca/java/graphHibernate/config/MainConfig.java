package edu.uca.java.graphHibernate.config;

import java.util.Properties;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = "edu.uca.java.graphHibernate", excludeFilters = { @Filter(Configuration.class) })
@PropertySource(value = { "classpath:jdbc.properties" })
@EnableTransactionManagement
@ImportResource("classpath:repositories.xml")
public class MainConfig {

	@Inject
	private Environment environment;

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource database = new DriverManagerDataSource();
		database.setDriverClassName(environment
				.getProperty("jdbc.driverClassName"));
		database.setUrl(environment.getProperty("jdbc.url"));
		database.setUsername(environment.getProperty("jdbc.username"));
		database.setPassword(environment.getProperty("jdbc.password"));
		return database;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource());
		entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter());
		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.hbm2ddl.auto", "update");
		entityManagerFactory.setJpaProperties(jpaProperties);
		return entityManagerFactory;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory());
		transactionManager.setDataSource(dataSource());
		return transactionManager;
	}

	@Bean
	public HibernateJpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setDatabase(Database.valueOf(environment
				.getProperty("jpa.database")));
		// TODO: sacar esto a una property
		jpaVendorAdapter
				.setDatabasePlatform("org.hibernate.dialect.PostgreSQLDialect");
		jpaVendorAdapter.setGenerateDdl(Boolean.parseBoolean(environment
				.getProperty("jpa.generateDdl")));
		jpaVendorAdapter.setShowSql(Boolean.parseBoolean(environment
				.getProperty("jpa.showSql")));
		return jpaVendorAdapter;
	}

	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource());
	}

	// internal helpers

	private EntityManagerFactory entityManagerFactory() {
		return entityManagerFactoryBean().getObject();
	}
}
