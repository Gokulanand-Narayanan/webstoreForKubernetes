package com.webstore.products.web.controller;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webstore.products.web.http.CartCallerService;
import com.webstore.products.web.model.Cart;
import com.webstore.products.web.props.CartPropertyReader;

/**
 * Servlet implementation class WebAuthenticateController
 */
@WebServlet("/webAuthenticateController")
public class WebAuthenticateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WebAuthenticateController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cart cart = new Cart();
		cart.getCartId(68592);
		
		CartPropertyReader cartProps = new CartPropertyReader();
		Properties cartPropsObj = cartProps.getCartPropsObj();
		
		String GET_URL = cartPropsObj.getProperty("GET_URL_1");
		String USER_AGENT = cartPropsObj.getProperty("USER_AGENT");
		CartCallerService.sendCartDetails(GET_URL, USER_AGENT);
		
		response.getWriter().append("Served at: ").append(request.getContextPath()).append("<br> <br> send customer details into queue...");
	}
}
