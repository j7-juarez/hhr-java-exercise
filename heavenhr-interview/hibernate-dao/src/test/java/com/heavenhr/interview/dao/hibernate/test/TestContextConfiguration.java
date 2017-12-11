package com.heavenhr.interview.dao.hibernate.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.heavenhr.interview.dao.hibernate.DataBaseConfiguration;
import com.heavenhr.interview.dao.hibernate.HibernateDaoApplicationContext;

@Configuration
@PropertySource("classpath:app.properties")
@Import({HibernateDaoApplicationContext.class})
public class TestContextConfiguration {

	private static final String PROPERTY_NAME_DB_CONNECTION_STRING = "db_connectionstring";
	private static final String PROPERTY_NAME_DB_USER = "db_user";
	private static final String PROPERTY_NAME_DB_PASSWORD = "db_password";
	private static final String PROPERTY_NAME_DB_DRIVER = "db_driver";
	private static final String PROPERTY_NAME_DB_HIBERNATE_DIALECT = "db_hibernatedialect";
	private static final String PROPERTY_NAME_DB_HIBERNATE_SHOW_SQL = "db_showsql";
	private static final String PROPERTY_NAME_DB_HIBERNATE_HBM2DLLAUTO = "db_hbm2ddlauto";
	
	@Autowired
	private Environment environment;

	@Bean
	public DataBaseConfiguration getBiAnalyticsDataStoreConfiguration() {
		DataBaseConfiguration conf = new DataBaseConfiguration();
		conf.setDbConnectionString(environment.getProperty(PROPERTY_NAME_DB_CONNECTION_STRING));
		conf.setDbDriver(environment.getProperty(PROPERTY_NAME_DB_DRIVER));
		conf.setDbUser(environment.getProperty(PROPERTY_NAME_DB_USER));
		conf.setDbPassword(environment.getProperty(PROPERTY_NAME_DB_PASSWORD));
		conf.setHibernateDialect(environment.getProperty(PROPERTY_NAME_DB_HIBERNATE_DIALECT));
		conf.setHibernateShowSql(Boolean.parseBoolean(environment.getProperty(PROPERTY_NAME_DB_HIBERNATE_SHOW_SQL)));
		conf.setHibernateHbm2ddlAuto(environment.getProperty(PROPERTY_NAME_DB_HIBERNATE_HBM2DLLAUTO));
		return conf;
	}
	
	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
	
}
