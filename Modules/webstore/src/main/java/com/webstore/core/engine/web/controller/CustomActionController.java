package com.webstore.core.engine.web.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CpuLaoadController
 */
@WebServlet("/CustomActionController")
public class CustomActionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CustomActionController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean keepLooping = true;
		long cpuDuration = Long.parseLong(request.getParameter("duration"));
		long timeStart = System.currentTimeMillis();
		getProductDetails(keepLooping,cpuDuration,timeStart);
	}

	private void getProductDetails(boolean keepLooping, long cpuDuration, long timeStart) {
		getProductPriceDetails(keepLooping,cpuDuration,timeStart);		
	}

	private void getProductPriceDetails(boolean keepLooping, long cpuDuration, long timeStart) {
		checkProductAvailability(keepLooping,cpuDuration,timeStart);		
	}

	private void checkProductAvailability(boolean keepLooping, long cpuDuration, long timeStart) {
		checkStock(keepLooping,cpuDuration,timeStart);		
	}

	private void checkStock(boolean keepLooping, long cpuDuration, long timeStart) {
		sendProductStockDetails(keepLooping,cpuDuration,timeStart);
	}

	private void sendProductStockDetails(boolean keepLooping, long cpuDuration, long timeStart) {
		while(keepLooping){
			long timeNow = System.currentTimeMillis();			
			if((timeNow - timeStart) >= (cpuDuration * 1000) || (timeNow - timeStart) >= (300 * 1000)){
				keepLooping = false;
			}
		}	
	}
}
