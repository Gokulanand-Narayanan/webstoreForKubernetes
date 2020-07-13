package com.webstore.core.engine.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webstore.core.engine.web.data.Product.ProductDate;
import com.webstore.core.engine.web.data.Product.ProductLimit;

@WebServlet("/productController/getExpireProduct")
public class ExpireProductController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 ProductDate T1 = new ProductDate();
	      ProductLimit T2 = new ProductLimit();
	      T1.start();
	      T2.start();
	      response.getWriter().append("Served at: ").append(request.getContextPath()).append("Dead lock scenarios simulated.. restart the Server");
	}
}
