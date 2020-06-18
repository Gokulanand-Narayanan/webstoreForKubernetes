package com.acme.view;

import java.util.ArrayList;
import java.util.List;

public class ServicePlan {
	
	private String planName;
	
	public ServicePlan() {
		planName = "Dummy";
	}
	
	public String getPlanName() {
		selectAllServicePlan();
		return planName;
	}
	
	public boolean isPlanExists(String name) {
		try {
			Thread.sleep(150L);
			selectAllServicePlan();
		}
		catch(Exception e) {
		}
		return true;
	}
	
	public String[] getServicePlan(String name) {
		String plan[]  = new String[1];
		if(isPlanExists(name)) {
			try {
				Thread.sleep(650L);
			}
			catch(Exception e) {
			}
		}
		return plan;
	}
	
	public List reviewServicePlan(String name) {
		List aList = new ArrayList();
		if(isPlanExists(name)) {
			try {
				Thread.sleep(850L);
			}
			catch(Exception e) {
			}
		}
		return aList;
	}
	
	public boolean updateServicePlan(String name) {
		boolean result = false;
		if(isPlanExists(name)) {
			try {
				Thread.sleep(1230L);
				result = true;
			}
			catch(Exception e) {
				result = false;
			}
			updatePlanDetails("name", 100, false);
		}
		return result;
	}
	
	private void updatePlanDetails(String name, int no , boolean flag) {
		try {
			Thread.sleep(610L);
		}catch(Exception e) {
		}
	}
	
	
	public ArrayList selectAllServicePlan() {
		ArrayList list = null;
		
		try {
			Thread.sleep(1000L);
		}
		catch(Exception e) {
			
		}
		
		list = new ArrayList();
		for(int i=0;i<1000;i++) {
			list.add("SerPN-"+i);
		}
		return list;
	}

}
