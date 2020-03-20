/**
 * 
 */
package com.webstore.core.engine.web.datasource;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DataSourceUtil {
	
	public Connection getConnection(String dataSourceJndiName){
		DataSource dataSource = getDataSource(dataSourceJndiName);
		Connection connection = null;
		try {
			if (dataSource != null) {
				connection = dataSource.getConnection();
			}
		} catch (Exception e) {
			System.out.println("exception in  getConnection method : "+e.getCause());
			e.printStackTrace();
		}
		return connection;
	}
	
	private DataSource getDataSource(String dataSourceJndiName){
		DataSource datasource = null;
		try {
			Context initialContext = new InitialContext();
			datasource = (DataSource)initialContext.lookup(dataSourceJndiName);
		} catch (Exception e) {
			System.out.println("unable to get the datasource : "+e.getCause());
			e.printStackTrace();
		}
		return datasource;
	}
}
