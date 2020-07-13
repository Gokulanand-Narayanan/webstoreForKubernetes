package com.webstore.core.engine.web.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webstore.core.engine.web.http.EngineCallerService;
import com.webstore.core.engine.web.props.EnginePropertyReader;

/**
 * Servlet implementation class RetailerInfoManagement
 */
@WebServlet("/RetailerInfoManagement/getInfo")
public class RetailerInfoManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RetailerInfoManagement() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String count = request.getParameter("count");
		int threadCount = 0;
		try {
			threadCount = Integer.parseInt(count);
		} catch (Exception e) {
			threadCount = 10;
		}
		
		EnginePropertyReader engineProps = new EnginePropertyReader();
		Properties enginePropsObj = engineProps.getEnginePropsObj();
		String getUrl = enginePropsObj.getProperty("SERVLET_GET_URL");
		String userAgent = enginePropsObj.getProperty("USER_AGENT");
		
		ExecutorService service = Executors.newCachedThreadPool();
        Set <Callable<String>> callables = new HashSet <Callable<String>> ();
        
		for(int index=0;index<=threadCount;index++) {
			callables.add(new Callable<String>()
	        {
	            @Override
	            public String call() throws Exception
	            {
	            	EngineCallerService.processCart(getUrl.trim(),userAgent.trim());
	                return "making a servlet call, and its response store here";
	            }
	        });
		}
		try {
		     List<Future<String>> futures = service.invokeAll(callables);
		     for (Future<String> future : futures)
		     {
		        System.out.println (future.get());
		     }
		  }
		catch (Exception e){
		  e.printStackTrace();
		}
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

}
