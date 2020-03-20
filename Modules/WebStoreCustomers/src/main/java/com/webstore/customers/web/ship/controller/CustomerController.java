package com.webstore.customers.web.ship.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webstore.customers.web.ship.datasource.DataBaseUtil;
import com.webstore.customers.web.ship.datasource.DataSourceUtil;
import com.webstore.customers.web.ship.props.ShipPropertyReader;

/**
 * Servlet implementation class CustomerController
 */
@WebServlet("/getCustomerDetails")
public class CustomerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ShipPropertyReader propertyReader = new ShipPropertyReader();
		Properties shipProps = propertyReader.getShipPropsObj();
		String dataSourceName = shipProps.getProperty("DATA_SOURCE_JNDI_TOMCAT");
		String pstmtQuery = shipProps.getProperty("PREPARED_STATMENT_QUERY");
		
		DataSourceUtil sourceUtil = new DataSourceUtil(dataSourceName);
		DataBaseUtil dataBaseUtil = new DataBaseUtil();
		Connection connection = null;
		try {
			connection = sourceUtil.getConnection();
			PreparedStatement pstmt = dataBaseUtil.getPreparedStatment(connection, pstmtQuery);
			pstmt.setInt(0, 12);
			pstmt.executeQuery();
		} catch (Exception e) {
			System.err.println("got an exception while making prepared statment call");
			e.printStackTrace();
		}finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e2) {
				System.err.println("got an exception while closing prepared statment connection"+e2.getCause());
			}
		}
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
}
