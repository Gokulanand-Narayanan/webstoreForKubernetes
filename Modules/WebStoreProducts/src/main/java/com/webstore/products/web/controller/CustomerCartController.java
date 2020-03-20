package com.webstore.products.web.controller;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webstore.products.web.dao.CartPoolService;
import com.webstore.products.web.props.CartPropertyReader;

/**
 * Servlet implementation class CustomerCartController
 */
@WebServlet("/customerCartController")
public class CustomerCartController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerCartController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String option = request.getParameter("option");
		String count = request.getParameter("count");
		int connectionCount = 0;
		try {
			connectionCount = Integer.parseInt(count);
		} catch (Exception e) {
			connectionCount = 8;
		}
		
		CartPropertyReader cartProps = new CartPropertyReader();
		Properties cartPropsObj = cartProps.getCartPropsObj();
		String dataSourceJndiName = cartPropsObj.getProperty("DATA_SOURCE_JNDI_JBOSS");
		
		if (option!= null && option.equalsIgnoreCase("holdConnection")) {
			CartPoolService.createAndHoldConnection(connectionCount, dataSourceJndiName);
		}else if(option!= null && option.equalsIgnoreCase("reuse")){
			CartPoolService.createAndCloseConnection(connectionCount, dataSourceJndiName);
		}else {
			CartPoolService.destoryAllConnection();
		}
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

}
