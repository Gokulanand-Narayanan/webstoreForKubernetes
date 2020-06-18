package com.webstore.core.engine.web.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

import com.webstore.core.engine.web.datasource.DataBaseUtil;
import com.webstore.core.engine.web.props.EnginePropertyReader;

/**
 * Application Lifecycle Listener implementation class ProductLockRequestListner
 *
 */
@WebListener
public class ProductLockRequestListner implements ServletRequestListener {
	private static boolean flag;
	Object lock = new Object();
    /**
     * Default constructor. 
     */
    public ProductLockRequestListner() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletRequestListener#requestDestroyed(ServletRequestEvent)
     */
    public void requestDestroyed(ServletRequestEvent sre)  { 
        
    }

	/**
     * @see ServletRequestListener#requestInitialized(ServletRequestEvent)
     */
    public void requestInitialized(ServletRequestEvent event)  { 
    	HttpServletRequest http = (HttpServletRequest) event.getServletRequest();
    	String uri = http.getServletPath();
    	if (uri.equals("/webstoreLock")) {
    		System.out.println("webstore table lock request arrived");
    		Thread thread1 = new Thread(() -> {
				synchronized (lock) {
					while (flag == false) {
						try {
							EnginePropertyReader engineProps = new EnginePropertyReader();
							Properties enginePropsObj = engineProps.getEnginePropsObj();
							String driverClass = enginePropsObj.getProperty("DB_DRIVER_CLASS");
							String jdbcUrl = enginePropsObj.getProperty("DB_JDBC_URL");
							String userName = enginePropsObj.getProperty("DB_USER_NAME");
							String password = enginePropsObj.getProperty("DB_PASSWORD");
							String query = enginePropsObj.getProperty("DB_LOCK_QUERY");
							DataBaseUtil baseUtil = new DataBaseUtil();
							Connection connection = baseUtil.getNormalConnection(driverClass, jdbcUrl, userName, password);
							Statement stmt = baseUtil.getStatment(connection);
							stmt.execute(query);
							System.out.println("webstore database table ["+query+"] is  locked");
							lock.wait();
							//..un-lock
							stmt.execute("unlock tables");
							System.out.println("webstore database table ["+query+"] is  unlocked");
						} catch (InterruptedException e) {
							e.printStackTrace();
						} catch (SQLException e) {
							System.out.println("sql exction " + e);
						}
					}
				}
			});
    		thread1.start();
    	}else if (uri.equals("/webstoreunlock")) {
			System.out.println("webstore database table unlocked request received");
			Thread thread2 = new Thread(() -> {
				synchronized (lock) {
					flag = true;
					lock.notify();
				}
			});
			thread2.start();
		}
    }
	
}
