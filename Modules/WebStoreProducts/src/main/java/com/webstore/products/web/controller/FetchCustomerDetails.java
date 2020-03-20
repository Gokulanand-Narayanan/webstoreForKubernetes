package com.webstore.products.web.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webstore.products.web.datasource.DataBaseUtil;
import com.webstore.products.web.datasource.DataSourceUtil;
import com.webstore.products.web.props.CartPropertyReader;

/**
 * Servlet implementation class FetchCustomerDetails
 */
@WebServlet("/fetchCustomerDetails")
public class FetchCustomerDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FetchCustomerDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int queryCount = Integer.parseInt(request.getParameter("count"));
			System.out.println("received : ["+queryCount+"] query to execute in database to generate N+1 problem");

			CartPropertyReader cartProps = new CartPropertyReader();
			Properties cartPropsObj = cartProps.getCartPropsObj();
			String dataSourceJndiName = cartPropsObj.getProperty("DATA_SOURCE_JNDI_JBOSS");
			String pQuery = cartPropsObj.getProperty("PREPARED_STATMENT_QUERY");
			String sQuery = cartPropsObj.getProperty("STATMENT_QUERY");
			
			DataSourceUtil dataSourceUtil = new DataSourceUtil(dataSourceJndiName);
			Connection connection = dataSourceUtil.getConnection();
			DataBaseUtil dataBaseUtil = new DataBaseUtil();
			Statement stmt = dataBaseUtil.getStatment(connection);
			PreparedStatement pstmt = dataBaseUtil.getPreparedStatment(connection, pQuery);
			pstmt.setInt(1, 2);
			ResultSet result = null;
			for (int i = 0; i < queryCount/2; i++) {
				result = pstmt.executeQuery();
			}
			for (int i = 0; i < queryCount/2; i++) {
				result = dataBaseUtil.getResultSet(stmt, sQuery);
			}
			
			if (result != null) {
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
}
