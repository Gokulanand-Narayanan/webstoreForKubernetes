package com.webstore.core.engine.web.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.webstore.core.engine.web.datasource.DataSourceUtil;

public class OnlineWebPoolService {
	
	static ArrayList<Connection> connList = null;
	
	public static void createAndCloseConnection(int number,String dataSourceJndiName){
		Connection conn = null;
		for(int i = 0 ; i <= number;i++){
			try {
				DataSourceUtil dataSourceUtil = new DataSourceUtil();
				conn = dataSourceUtil.getConnection(dataSourceJndiName);
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
	
	public void createAndHoldConnection(int number,String dataSourceJndiName){
		for(int i = 0 ; i <= number;i++){
			try {
				DataSourceUtil dataSourceUtil = new DataSourceUtil();
				Connection conn = dataSourceUtil.getConnection(dataSourceJndiName);
				connList = new ArrayList<Connection>();
				connList.add(conn);
			} catch (Exception e) {
				System.err.println("exception will fetching the connection from datasource in method [createAndHoldConnection] "+e.getCause());
			}
		}
	}
	
	public void destoryAllConnection(){
		if (connList.isEmpty() && connList.size() > 0) {
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
