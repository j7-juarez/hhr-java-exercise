package com.heavenhr.interview.dao.hibernate;

public class DataBaseConfiguration {

	private String dbConnectionString;

	private String dbUser;

	private String dbPassword;

	private String dbDriver;

	private String hibernateDialect;

	private boolean hibernateShowSql;

	private String hibernateHbm2ddlAuto;

	public String getDbConnectionString() {
		return dbConnectionString;
	}

	public void setDbConnectionString(String dbConnectionString) {
		this.dbConnectionString = dbConnectionString;
	}

	public String getDbUser() {
		return dbUser;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public String getDbDriver() {
		return dbDriver;
	}

	public void setDbDriver(String dbDriver) {
		this.dbDriver = dbDriver;
	}

	public String getHibernateDialect() {
		return hibernateDialect;
	}

	public void setHibernateDialect(String hibernateDialect) {
		this.hibernateDialect = hibernateDialect;
	}

	public boolean isHibernateShowSql() {
		return hibernateShowSql;
	}

	public void setHibernateShowSql(boolean hibernateShowSql) {
		this.hibernateShowSql = hibernateShowSql;
	}

	public String getHibernateHbm2ddlAuto() {
		return hibernateHbm2ddlAuto;
	}

	public void setHibernateHbm2ddlAuto(String hibernateHbm2ddlAuto) {
		this.hibernateHbm2ddlAuto = hibernateHbm2ddlAuto;
	}

}
