package com.heavenhr.interview.dao.hibernate;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "com.heavenhr.interview.dao.hibernate.repositories" })
public class PersistenceContext {

	private static final String HIBERNATE_PACKAGETOSCAN = "com.heavenhr.interview.model";

	@Autowired
	private DataBaseConfiguration dataBaseConfiguration;

	@Bean
	public DataSource dataSource() {
		HikariConfig dataSourceConfig = new HikariConfig();
		dataSourceConfig.setDriverClassName(dataBaseConfiguration.getDbDriver());
		dataSourceConfig.setJdbcUrl(dataBaseConfiguration.getDbConnectionString());
		dataSourceConfig.setUsername(dataBaseConfiguration.getDbUser());
		dataSourceConfig.setPassword(dataBaseConfiguration.getDbPassword());

		return new HikariDataSource(dataSourceConfig);
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactoryBean.setPackagesToScan(HIBERNATE_PACKAGETOSCAN);

		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.dialect", dataBaseConfiguration.getHibernateDialect());
		jpaProperties.put("hibernate.show_sql", dataBaseConfiguration.isHibernateShowSql());
		jpaProperties.put("hibernate.hbm2ddl.auto", dataBaseConfiguration.getHibernateHbm2ddlAuto());

		entityManagerFactoryBean.setJpaProperties(jpaProperties);

		return entityManagerFactoryBean;
	}

	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}

	public DataBaseConfiguration getDataBaseConfiguration() {
		return dataBaseConfiguration;
	}

	public void setDataBaseConfiguration(DataBaseConfiguration dataBaseConfiguration) {
		this.dataBaseConfiguration = dataBaseConfiguration;
	}

}