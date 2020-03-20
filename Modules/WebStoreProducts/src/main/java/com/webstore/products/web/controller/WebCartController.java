package com.webstore.products.web.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webstore.products.web.datasource.DataBaseUtil;
import com.webstore.products.web.datasource.DataSourceUtil;
import com.webstore.products.web.http.CartCallerService;
import com.webstore.products.web.props.CartPropertyReader;

/**
 * Servlet implementation class WebCartController
 */
@WebServlet("/webCartController")
public class WebCartController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public WebCartController() {
        // TODO Auto-generated constructor stub
    }

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		try {
			int queryCount = Integer.parseInt(request.getParameter("sqlQuery"));
			System.out.println("received : ["+queryCount+"] query to execute in database to generate N+1 problem");

			CartPropertyReader cartProps = new CartPropertyReader();
			Properties cartPropsObj = cartProps.getCartPropsObj();
			String dataSourceJndiName = cartPropsObj.getProperty("DATA_SOURCE_JNDI_JBOSS");
			String query = cartPropsObj.getProperty("PREPARED_STATMENT_QUERY");
			
			DataSourceUtil dataSourceUtil = new DataSourceUtil(dataSourceJndiName);
			Connection connection = dataSourceUtil.getConnection();
			DataBaseUtil dataBaseUtil = new DataBaseUtil();
			PreparedStatement pstmt = dataBaseUtil.getPreparedStatment(connection, query);
			pstmt.setInt(1, 2);
			ResultSet result = null;
			for (int i = 0; i < queryCount; i++) {
				result = pstmt.executeQuery();
			}
			if (result != null) {
				connection.close();
			}
			
			String GET_URL = cartPropsObj.getProperty("GET_URL_2");
			String USER_AGENT = cartPropsObj.getProperty("USER_AGENT");
			CartCallerService.sendCartDetails(GET_URL, USER_AGENT);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
