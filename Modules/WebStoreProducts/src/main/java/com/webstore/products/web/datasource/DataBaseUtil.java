package com.webstore.products.web.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseUtil {
	
	public Connection getNormalConnection(String driverClass,String jdbcUrl,String userName, String password)
	{
		Connection connection = null;
		try{
			Class.forName(driverClass);
			connection = DriverManager.getConnection(jdbcUrl, userName , password);
			
		}
		catch(Exception e){
			System.out.println("Exception Occured while Getting Connection "+e);
			e.printStackTrace();
		}
		return connection;
	}

	public Statement getStatment(Connection connection)
	{
		Statement stmt = null;
		try{
			if(connection!=null)
			{
				stmt = connection.createStatement();
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception Occured while Creating Statement...");
			e.printStackTrace();
		}
		return stmt;
	}

	public PreparedStatement getPreparedStatment(Connection connection,String query)
	{
		PreparedStatement pstmt = null;
		try{
			if(connection!=null)
			{
				pstmt = connection.prepareStatement(query);
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception Occured while Creating PreparedStatment...");
			e.printStackTrace();
		}
		return pstmt;
	}
		
	public ResultSet getResultSet(Statement stmt,String query) throws SQLException
	{
		ResultSet rs = null;
		try{
			if(stmt!=null)
			{
				rs = stmt.executeQuery(query);
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception Occured while Getting ResultSet....");
			e.printStackTrace();
		}
		return rs;
	}
}
