package com.webstore.core.engine.web.data;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ProductModel {
	public void processProduct(){
		long startTime = System.currentTimeMillis();
		for (long stop=System.nanoTime()+TimeUnit.MILLISECONDS.toNanos(279);stop>System.nanoTime();) {
			
		}
		System.out.println("ProductController.processProduct took "+(System.currentTimeMillis()-startTime));
        addProductsInCart();
	}
	
	private void addProductsInCart() {
		long startTime = System.currentTimeMillis();
		for (long stop=System.nanoTime()+TimeUnit.SECONDS.toNanos(getCartId(5,7));stop>System.nanoTime();) {
			
		}
		System.out.println("ProductController.addProductsInCart took "+(System.currentTimeMillis()-startTime));
	}
	

	private int getCartId(int min, int max) {
		long startTime = System.currentTimeMillis();
		for (long stop=System.nanoTime()+TimeUnit.MILLISECONDS.toNanos(261);stop>System.nanoTime();) {
			
		}
		System.out.println("ProductController.getCartId took "+(System.currentTimeMillis()-startTime));
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	public static void main(String[] args) {
		ProductModel productModel = new ProductModel();
		long start_time = System.currentTimeMillis();
		productModel.processProduct();
		System.out.println(System.currentTimeMillis() - start_time);
	}
}
