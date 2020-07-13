package com.webstore.core.engine.web.controller;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webstore.core.engine.web.http.EngineCallerService;
import com.webstore.core.engine.web.props.EnginePropertyReader;


/**
 * Servlet implementation class ShippingController
 */
@WebServlet("/shippingController/dispatch")
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
		String count = request.getParameter("count");
		int messageCount = 0;
		try {
			messageCount = Integer.parseInt(count);
		} catch (Exception e) {
			messageCount = 8;
		}
		
		EnginePropertyReader engineProps = new EnginePropertyReader();
		Properties enginePropsObj = engineProps.getEnginePropsObj();
		String getUrl = enginePropsObj.getProperty("JMS_GET_URL");
		String userAgent = enginePropsObj.getProperty("USER_AGENT");
		String finalUrl=getUrl+messageCount;
		System.out.println("process engine server send the request to : "+finalUrl);
		EngineCallerService.processCart(finalUrl.trim(),userAgent.trim());
		
		response.getWriter().append("send customer details to queue at: ").append(request.getContextPath());
	}
}
