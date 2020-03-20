package com.webstore.customers.web.ship.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webstore.customers.web.ship.cpu.CpuLoad;

/**
 * Servlet implementation class DispatchProductController
 */
@WebServlet("/dispatchProductController")
public class DispatchProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DispatchProductController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sleepTimeStr = request.getParameter("sleepTime");
		long msleepTime = 0;
		try {
			long sleepTime = Integer.parseInt(sleepTimeStr);
			msleepTime = sleepTime * 1000;
		} catch (Exception e) {
			System.err.println("exception while converting param in dispatchProductController controller : "+e.getCause());
		}
		
		CpuLoad cpuLoad = new CpuLoad(msleepTime);
		cpuLoad.start();
		
		response.getWriter().append("started cpu utilization thread for : "+msleepTime).append(" ms. ").append(request.getContextPath());
	}

}
