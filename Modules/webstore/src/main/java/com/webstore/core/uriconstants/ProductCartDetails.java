package com.webstore.core.uriconstants;

public class ProductCartDetails {
	
	private static int orderId = 0;
	private static double totalAmount = 0.0D;
	
	public static void getOrderIdDetails(int min, int max){
	    int x = (int)(Math.random()*((max-min)+1))+min;
	    setOrderId(x);
	}

	public static void getAmountDetails(double min, double max){
		double x = (int)(Math.random()*((max-min)+1))+min;
	    setTotalAmount(x);
	}

	public static int getOrderId(int min, int max) {
		getOrderIdDetails(min,max);
		return orderId;
	}

	public static double getTotalAmount(double min,double max) {
		getAmountDetails(min,max);
		return totalAmount;
	}

	public static void setOrderId(int orderIdValue) {
		orderId = orderIdValue;
	}

	public static void setTotalAmount(double totalAmountValue) {
		totalAmount = totalAmountValue;
	}
	
}
