package com.webstore.core.engine.web.controller;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webstore.core.engine.web.data.ProductModel;

/**
 * Servlet implementation class ProductController
 */
@WebServlet("/productController/getProduct")
public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getProduct();
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	public void getProduct() {
		long startTime = System.currentTimeMillis();
		for (long stop=System.nanoTime()+TimeUnit.MILLISECONDS.toNanos(297);stop>System.nanoTime();) {
			
		}
		System.out.println("ProductController.getProduct took "+(System.currentTimeMillis()-startTime));
		verifyStock();
		ProductModel model = new ProductModel();
		model.processProduct();
	}

	private void verifyStock() {
		long startTime = System.currentTimeMillis();
		for (long stop=System.nanoTime()+TimeUnit.MILLISECONDS.toNanos(412);stop>System.nanoTime();) {
			
		}
		System.out.println("ProductController.verifyStock took "+(System.currentTimeMillis()-startTime));
		checkProductDetails();
	}
	
	private void getPriceDetails() {
		long startTime = System.currentTimeMillis();
		for (long stop=System.nanoTime()+TimeUnit.MILLISECONDS.toNanos(351);stop>System.nanoTime();) {
			
		}
		System.out.println("ProductController.getPriceDetails took "+(System.currentTimeMillis()-startTime));
	}

	private void verifyProductQuality() {
		long startTime = System.currentTimeMillis();
		for (long stop=System.nanoTime()+TimeUnit.MILLISECONDS.toNanos(245);stop>System.nanoTime();) {
			
		}
		System.out.println("ProductController.verifyProductQuality took "+(System.currentTimeMillis()-startTime));
		getPriceDetails();
	}

	private void checkProductDetails() {
		long startTime = System.currentTimeMillis();
		for (long stop=System.nanoTime()+TimeUnit.MILLISECONDS.toNanos(268);stop>System.nanoTime();) {
			
		}
		System.out.println("ProductController.checkProductDetails took "+(System.currentTimeMillis()-startTime));
		verifyProductQuality();
	}

	public static void main(String[] args) {
		ProductController controller = new ProductController();
		controller.getProduct();
	}
}
