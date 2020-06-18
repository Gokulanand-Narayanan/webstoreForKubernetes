package com.webstore.products.web.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webstore.products.web.jms.PushShippingDetails;
import com.webstore.products.web.jms.SendShippingDetails;

/**
 * Servlet implementation class WebCartController
 */
@WebServlet("/webCartController/getCartDetails")
public class WebCartController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public WebCartController() {
        // TODO Auto-generated constructor stub
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		String count = request.getParameter("count");
		try {
			
			PushShippingDetails details = new PushShippingDetails(count);
			details.sendShippingDetailsQueue();			
		} catch (Exception e) {
			SendShippingDetails producerConsumer = new SendShippingDetails();
			producerConsumer.sendDetails(count);
		}
	}
}
