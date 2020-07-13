package com.webstore.core.engine.web.controller;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class OrderMaker
 */
@WebServlet("/addressvalidationservice")
public class AddressValidationService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddressValidationService() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		FileReader fileReader = null;
		String sleepTime = request.getParameter("sleepTime");
		String downloadBytes = request.getParameter("downloadBytes");
		System.out.println(" Sleep Time : "+sleepTime+"  Download Size in Bytes : "+downloadBytes);
		try{
			Thread.sleep(Long.parseLong(sleepTime));
		}
		catch(Exception e){
			out.print("exception while converting time into long : "+e);
		}
		try {
		    File file = new File(this.getClass().getClassLoader().getResource("ajaxDataFile.txt").getFile());
			int bytesToDownload = Integer.parseInt(downloadBytes);
		    fileReader = new FileReader(file); 
		    int index; 
			int counter=1;
			out.println("<--------------- START -----------------><br>");
			while ((index=fileReader.read()) != -1) {
			out.print((char) index); 
				counter++;
				if(counter >= bytesToDownload){
					out.println("<br><--------------- END -----------------><br>");
					break;
				}
			}
		}catch(Exception e){
			out.println("exception while reading data from file : "+e);
		}     
		finally {
		    if (fileReader != null) {
		        try {
		        	fileReader.close();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		}
	}
}
