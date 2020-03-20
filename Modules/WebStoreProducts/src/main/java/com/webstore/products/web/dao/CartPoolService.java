package com.webstore.products.web.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

import com.webstore.products.web.datasource.DataBaseUtil;
import com.webstore.products.web.datasource.DataSourceUtil;
import com.webstore.products.web.props.CartPropertyReader;

public class CartPoolService {
	
	static ArrayList<Connection> connList = new ArrayList<Connection>();
	
	public static void createAndCloseConnection(int number,String dataSourceJndiName){
		Connection conn = null;
		for(int i = 0 ; i <= number;i++){
			try {
				DataSourceUtil dataSourceUtil = new DataSourceUtil(dataSourceJndiName);
				conn = dataSourceUtil.getConnection();
				getCustomercartDetails(conn);
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				System.err.println("exception will fetching the connection from datasource in method [createAndCloseConnection] "+e.getCause());
			}finally {
				try {
					if (conn !=null) {
						conn.close();
					}
				} catch (Exception e2) {
					System.err.println("unable to close the connection"+e2.getCause());
					e2.getStackTrace();
				}
			}
		}
	}
	
	private static void getCustomercartDetails(Connection conn) {
		try {
			CartPropertyReader cartProps = new CartPropertyReader();
			Properties cartPropsObj = cartProps.getCartPropsObj();
			String pQuery = cartPropsObj.getProperty("PREPARED_STATMENT_QUERY");
			DataBaseUtil dataBaseUtil = new DataBaseUtil();
			PreparedStatement pstmt = dataBaseUtil.getPreparedStatment(conn, pQuery);
			pstmt.setInt(1, 2);
			ResultSet result = null;
			result = pstmt.executeQuery();
			if (result != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
	}

	public static void createAndHoldConnection(int number,String dataSourceJndiName){
		for(int i = 0 ; i <= number;i++){
			try {
				DataSourceUtil dataSourceUtil = new DataSourceUtil(dataSourceJndiName);
				Connection conn = dataSourceUtil.getConnection();
				connList.add(conn);
			} catch (Exception e) {
				System.err.println("exception will fetching the connection from datasource in method [createAndHoldConnection] "+e.getCause());
			}
		}
	}
	
	public static void destoryAllConnection(){
		if (connList != null && connList.size() > 0) {
			for(int i = 0 ; i <= connList.size();i++){
				try {
					connList.get(i).close();
				} catch (Exception e) {
					System.err.println("exception will releasing the connection in method [destoryAllConnection] "+e.getCause());
				}
			}
		}
	}
}
