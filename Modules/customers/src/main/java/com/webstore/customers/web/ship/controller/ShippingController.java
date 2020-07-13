package com.webstore.customers.web.ship.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webstore.customers.web.ship.jms.PushShippingDetails;
import com.webstore.customers.web.ship.jms.SendShippingDetails;

/**
 * Servlet implementation class ShippingController
 */
@WebServlet("/shippingController")
public class ShippingController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShippingController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String jmsCount = request.getParameter("count");
		SendShippingDetails producerConsumer = new SendShippingDetails();
		producerConsumer.sendDetails(jmsCount);
		response.getWriter().append("send customer details to queue at: ").append(request.getContextPath());
	}

}
