package com.online.payment.gateway;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class paymentController {
	
	public static int i = 0;

	@RequestMapping("/visa")
	public String visaPaymentGateway() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "Success ! redirecting...";
	}
	@RequestMapping("/master")
	public String masterPaymentGateway() {
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("called /master from paymentController - gateway");
		return "Could not get a timely response from the payment gateway!";
	}
	
	@RequestMapping("/mastercard")
	@ResponseStatus(code=HttpStatus.INTERNAL_SERVER_ERROR,reason="Could not get a timely response from the payment gateway!")
	public String masterCardPaymentGateway() {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("called /mastercard from masterCardPaymentGateway - gateway");
		return "Could not get a timely response from the payment gateway!";
	}
}
