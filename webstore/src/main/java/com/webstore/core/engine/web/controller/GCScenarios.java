package com.webstore.core.engine.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GCAppln
 */
@WebServlet("/GCScenario/highgc")
public class GCScenarios extends HttpServlet
{
  String action = null;

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    doPost(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    this.action = request.getParameter("action");
    if ((this.action.equalsIgnoreCase("GCThread")) || (this.action.equalsIgnoreCase("OtherThread"))) {
      GarbageCollectionCThread gcThread = new GarbageCollectionCThread();
      gcThread.start();
    }
    else {
    	while(true) {
    		
    		
    	}
    }
  }

  private class GarbageCollectionCThread extends Thread
  {
	  public void run()
	  {
	      if(action.equalsIgnoreCase("GCThread")) {
	    	  int max = 500000;
		      int min = 32;
		      Object[] arr = new Object[min];
		      Runtime rt = Runtime.getRuntime();
		      System.out.println("Step/TotalMemory/FreeMemory/UsedMemory:");
		      while (true) {
		        for (int n = 0; n < min - 1; ++n) {
		        	arr[(min - n - 1)] = arr[(min - n - 2)];
		        }
		        arr[0] = getOneMega();
		      }
	      }
	      else {
	    	  while(true) {
	    	  
	    	  }
	      }
    }

    private Object getOneMega()
    {
    	Object[] lst = new Object[10];
	    lst[0] = new long[32768];
	    lst[1] = new int[65536];
	    lst[2] = new double[32768];
	    lst[3] = new float[16384];
	    lst[4] = new byte[65536];
	    String[] l = new String[4096];
	    for (int i = 0; i < 4096; ++i)
	        l[i] = new String("12345678");
	    lst[5] = l;
	    lst[6] = new char[32768];
	    return lst;
    }
  }
}