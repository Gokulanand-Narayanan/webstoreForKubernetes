package com.webstore.products.web.model;

import java.util.concurrent.TimeUnit;

public class Cart {
	
	public void getCartId(long time){
		long startTime = System.currentTimeMillis();
		for (long stop=System.nanoTime()+TimeUnit.SECONDS.toNanos(5);stop>System.nanoTime();) {
			
		}
		System.out.println("Cart.getCartNumber took "+(System.currentTimeMillis()-startTime));
	}
	public static void main(String[] args) {
		Cart cart = new Cart();
		cart.getCartId(68592);
	}
}
