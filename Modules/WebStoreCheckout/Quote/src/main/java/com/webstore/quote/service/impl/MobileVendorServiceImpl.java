package com.webstore.quote.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.webstore.quote.dao.MobileVendorDAO;
import com.webstore.quote.service.MobileVendorService;

public class MobileVendorServiceImpl implements MobileVendorService{

	private MobileVendorDAO mobileVendorDAO;
	
	
	public void setMobileVendorDAO(MobileVendorDAO mobileVendorDAO) {
		this.mobileVendorDAO = mobileVendorDAO;
	}

	@Override
	@Transactional(readOnly=true)
	public List getMobileDetails() {
		return this.mobileVendorDAO.getMobileVendorDetails();
	}

}
