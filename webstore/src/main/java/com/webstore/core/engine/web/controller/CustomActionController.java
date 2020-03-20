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
@WebServlet("/HighCPUGenerator")
public class CustomActionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CustomActionController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean keepLooping = true;
		long cpuDuration = Long.parseLong(request.getParameter("duration"));
		long timeStart = System.currentTimeMillis();
		
		while(keepLooping){
			long timeNow = System.currentTimeMillis();			
			if((timeNow - timeStart) >= (cpuDuration * 1000) || (timeNow - timeStart) >= (300 * 1000)){
				keepLooping = false;
			}
		}
	}
}
