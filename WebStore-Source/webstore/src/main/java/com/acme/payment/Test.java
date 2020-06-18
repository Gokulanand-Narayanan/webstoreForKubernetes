package com.acme.payment;

import java.util.Random;

public class Test {

	public static void main(String[] args) {
		long t1 = System.currentTimeMillis();
		Random r = new Random();
		int Low = 8;
		int High = 16;
		int random = r.nextInt(High-Low) + Low;
		int count = 1000000;
		int max = random * 1000;
		System.out.println(" MAX :"+max);
		double total_amount = 1500.00D;
		try {
			int rate = 0;
			while(rate <= max) {
				for(int j=0;j<count;j++) {
					total_amount = total_amount + 0;
				}
				rate++;
			}
			/*for(int i=0;i<16000;i++) {
				for(int j=0;j<count;j++) {
					total_amount = total_amount + 0;
				}
			}*/
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		long t2 = System.currentTimeMillis();
		System.out.println(" Diff :"+(t2-t1));

	}

}
