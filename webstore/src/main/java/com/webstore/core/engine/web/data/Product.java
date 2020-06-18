package com.webstore.core.engine.web.data;

public class Product {
	public static Object Lock1 = new Object();
	public static Object Lock2 = new Object();
	  
	 public static void main(String args[]) {
	      ProductDate T1 = new ProductDate();
	      ProductLimit T2 = new ProductLimit();
	      T1.start();
	      T2.start();
	 }	
	 
	 public static class ProductDate extends Thread {
	      public void run() {
	         synchronized (Lock1) {
	            
	            try { Thread.sleep(10); }
	            catch (InterruptedException e) {}
	            System.out.println("ProductDate : Waiting for lock...");
	            
	            synchronized (Lock2) {
	               System.out.println("ProductDate : Holding locks ...");
	            }
	         }
	      }
	   }
	 
	   public static class ProductLimit extends Thread {
	      public void run() {
	         synchronized (Lock2) {
	            System.out.println("ProductLimit : Holding lock ...");
	            
	            try { Thread.sleep(10); }
	            catch (InterruptedException e) {}
	            System.out.println("ProductLimit : Waiting for lock ...");
	            
	            synchronized (Lock1) {
	               System.out.println("ProductLimit : Holding lock ...");
	            }
	         }
	      }
	   } 
}
