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
 * Servlet implementation class CustomerAction
 */
@WebServlet("/CustomerAction/getCustomerDetails")
public class CustomerAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String count = request.getParameter("count");
		int connectionCount = 0;
		try {
			connectionCount = Integer.parseInt(count);
		} catch (Exception e) {
			connectionCount = 8;
		}
		
		EnginePropertyReader engineProps = new EnginePropertyReader();
		Properties enginePropsObj = engineProps.getEnginePropsObj();
		String getUrl = enginePropsObj.getProperty("GET_URL");
		String userAgent = enginePropsObj.getProperty("USER_AGENT");
		String finalUrl=getUrl+connectionCount;
		System.out.println("process engine server send the request to : "+finalUrl);
		EngineCallerService.processCart(finalUrl.trim(),userAgent.trim());
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

}
