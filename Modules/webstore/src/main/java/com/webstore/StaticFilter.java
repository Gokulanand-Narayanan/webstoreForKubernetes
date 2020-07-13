package com.webstore;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import java.util.*;

public class StaticFilter implements Filter {

	FilterConfig config; 

	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
		String slowParam = req.getParameter("slow");
		if(slowParam != null){
			String minParam = this.config.getInitParameter("min");
			String maxParam = this.config.getInitParameter("max");
			int min = 1000, max = 2000;
			try{
				min = Integer.parseInt(minParam);
				max = Integer.parseInt(maxParam);
			}catch(Exception ex){	
			}
			if (min >= max) {
				 min = 1000;
				 max = 2000;
			}
			int randomInt = 1000;
			try{
				Random r = new Random();
				randomInt = r.nextInt((max - min) + 1) + min;
			}
			catch(Exception ex){}
			
			try{
				randomInt = Integer.parseInt(slowParam);
			}catch(Exception ex){	
			}
			
			try{
				Thread.sleep(randomInt);
			}
			catch(Exception ex){}
		}
		chain.doFilter(req, res);
	}

	public void destroy() {
		
	}
}