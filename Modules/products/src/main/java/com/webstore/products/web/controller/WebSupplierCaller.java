package com.webstore.products.web.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webstore.products.model.DataDao;

/**
 * Servlet implementation class WebSupplierCaller
 */
@WebServlet("/webSupplierCaller")
public class WebSupplierCaller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WebSupplierCaller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("received a request to generate N+1 problem in database...");
		DataDao dataDao = new DataDao();
		dataDao.getDetail();
		response.getWriter().append("Served at : [").append(request.getContextPath()).append("] and generate N+1 problem");
	}

}
