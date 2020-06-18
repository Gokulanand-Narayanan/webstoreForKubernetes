package com.webstore.core.engine.web.data;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ProductModel {
	public void processProduct(){
		long startTime = System.currentTimeMillis();
		for (long stop=System.nanoTime()+TimeUnit.MILLISECONDS.toNanos(279);stop>System.nanoTime();) {
			
		}
		System.out.println("ProductController.processProduct took "+(System.currentTimeMillis()-startTime));
		verifyProduct();
	}
	
	public void verifyProduct(){
		long startTime = System.currentTimeMillis();
		for (long stop=System.nanoTime()+TimeUnit.MILLISECONDS.toNanos(379);stop>System.nanoTime();) {
			
		}
		System.out.println("ProductController.processProduct took "+(System.currentTimeMillis()-startTime));
		getProduct();
	}
	
	public void getProduct(){
		long startTime = System.currentTimeMillis();
		for (long stop=System.nanoTime()+TimeUnit.MILLISECONDS.toNanos(279);stop>System.nanoTime();) {
			
		}
		System.out.println("ProductController.processProduct took "+(System.currentTimeMillis()-startTime));
		getProductDescription();
	}
	
	public void getProductDescription(){
		long startTime = System.currentTimeMillis();
		for (long stop=System.nanoTime()+TimeUnit.MILLISECONDS.toNanos(279);stop>System.nanoTime();) {
			
		}
		System.out.println("ProductController.processProduct took "+(System.currentTimeMillis()-startTime));
		getDetailProduct();
	}
	
	public void getDetailProduct(){
		long startTime = System.currentTimeMillis();
		for (long stop=System.nanoTime()+TimeUnit.MILLISECONDS.toNanos(279);stop>System.nanoTime();) {
			
		}
		System.out.println("ProductController.processProduct took "+(System.currentTimeMillis()-startTime));
		makeProductAvailable();
	}
	
	public void makeProductAvailable(){
		long startTime = System.currentTimeMillis();
		for (long stop=System.nanoTime()+TimeUnit.MILLISECONDS.toNanos(279);stop>System.nanoTime();) {
			
		}
		System.out.println("ProductController.processProduct took "+(System.currentTimeMillis()-startTime));
		checkProductStock();
	}
	
	public void checkProductStock(){
		long startTime = System.currentTimeMillis();
		for (long stop=System.nanoTime()+TimeUnit.MILLISECONDS.toNanos(279);stop>System.nanoTime();) {
			
		}
		System.out.println("ProductController.processProduct took "+(System.currentTimeMillis()-startTime));
		checkProductPrice();
	}
	
	public void checkProductPrice(){
		long startTime = System.currentTimeMillis();
		for (long stop=System.nanoTime()+TimeUnit.MILLISECONDS.toNanos(279);stop>System.nanoTime();) {
			
		}
		System.out.println("ProductController.processProduct took "+(System.currentTimeMillis()-startTime));
		checkMarketProductPrice();
	}
	
	public void checkMarketProductPrice(){
		long startTime = System.currentTimeMillis();
		for (long stop=System.nanoTime()+TimeUnit.MILLISECONDS.toNanos(279);stop>System.nanoTime();) {
			
		}
		System.out.println("ProductController.processProduct took "+(System.currentTimeMillis()-startTime));
		checktProductFeatures();
	}
	
	public void checktProductFeatures(){
		long startTime = System.currentTimeMillis();
		for (long stop=System.nanoTime()+TimeUnit.MILLISECONDS.toNanos(279);stop>System.nanoTime();) {
			
		}
		System.out.println("ProductController.processProduct took "+(System.currentTimeMillis()-startTime));
		checktcartAvailbility();
	}
	
	public void checktcartAvailbility(){
		long startTime = System.currentTimeMillis();
		for (long stop=System.nanoTime()+TimeUnit.MILLISECONDS.toNanos(279);stop>System.nanoTime();) {
			
		}
		System.out.println("ProductController.processProduct took "+(System.currentTimeMillis()-startTime));
		verifyAddress();
	}
	
	public void verifyAddress(){
		long startTime = System.currentTimeMillis();
		for (long stop=System.nanoTime()+TimeUnit.MILLISECONDS.toNanos(279);stop>System.nanoTime();) {
			
		}
		System.out.println("ProductController.processProduct took "+(System.currentTimeMillis()-startTime));
		getDiliveryAddress();
	}
	
	public void getDiliveryAddress(){
		long startTime = System.currentTimeMillis();
		for (long stop=System.nanoTime()+TimeUnit.MILLISECONDS.toNanos(281);stop>System.nanoTime();) {
			
		}
		System.out.println("ProductController.processProduct took "+(System.currentTimeMillis()-startTime));
		checkAccount();
	}
	
	public void checkAccount(){
		long startTime = System.currentTimeMillis();
		for (long stop=System.nanoTime()+TimeUnit.MILLISECONDS.toNanos(285);stop>System.nanoTime();) {
			
		}
		System.out.println("ProductController.processProduct took "+(System.currentTimeMillis()-startTime));
		makePayment();
	}
	
	public void makePayment(){
		long startTime = System.currentTimeMillis();
		for (long stop=System.nanoTime()+TimeUnit.MILLISECONDS.toNanos(265);stop>System.nanoTime();) {
			
		}
		System.out.println("ProductController.processProduct took "+(System.currentTimeMillis()-startTime));
		getProductExprieDate();
	}
	
	public void getProductExprieDate(){
		long startTime = System.currentTimeMillis();
		for (long stop=System.nanoTime()+TimeUnit.MILLISECONDS.toNanos(265);stop>System.nanoTime();) {
			
		}
		System.out.println("ProductController.processProduct took "+(System.currentTimeMillis()-startTime));
		verifyFinalProduct();
	}
	
	public void verifyFinalProduct(){
		long startTime = System.currentTimeMillis();
		for (long stop=System.nanoTime()+TimeUnit.MILLISECONDS.toNanos(265);stop>System.nanoTime();) {
			
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
		for (long stop=System.nanoTime()+TimeUnit.MILLISECONDS.toNanos(491);stop>System.nanoTime();) {
			
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
