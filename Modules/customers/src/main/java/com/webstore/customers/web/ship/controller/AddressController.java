package com.webstore.customers.web.ship.controller;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webstore.customers.web.ship.http.ShipCallerService;
import com.webstore.customers.web.ship.props.ShipPropertyReader;

/**
 * Servlet implementation class AddressController
 */
@WebServlet("/verifyAddress")
public class AddressController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddressController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ShipPropertyReader propertyReader = new ShipPropertyReader();
		Properties shipProps = propertyReader.getShipPropsObj();
		String userAgent = shipProps.getProperty("USER_AGENT");
		String getUrl = shipProps.getProperty("GET_URL_1");
		ShipCallerService.sendShipDetails(getUrl, userAgent);
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
}
