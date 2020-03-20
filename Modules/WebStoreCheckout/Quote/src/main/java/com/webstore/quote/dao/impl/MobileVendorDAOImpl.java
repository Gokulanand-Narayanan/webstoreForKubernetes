package com.webstore.quote.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.webstore.persistence.hibernate.GenericHibernateDAO;
import com.webstore.quote.dao.MobileVendorDAO;
import com.webstore.quote.model.MobileVendor;

@Repository
public class MobileVendorDAOImpl extends GenericHibernateDAO<MobileVendor, Integer> implements MobileVendorDAO{

	@Autowired
	  private SessionFactory sessionFactory;
	  
	  public void setSessionFactory(SessionFactory sf)
	  {
	    this.sessionFactory = sf;
	  }
	  
	@Override
	public List getMobileVendorDetails() {
		long start = System.currentTimeMillis();
		this.sessionFactory.getCurrentSession().createSQLQuery("select mobilevend0_.id as id1_1_, mobilevend0_.description as descript2_1_, mobilevend0_.name as name3_1_, mobilevend0_.pro_id as pro_id5_1_, mobilevend0_.price as price4_1_ from MobileVendor mobilevend0_").list();
		long end = System.currentTimeMillis();
		System.out.println(end-start);
		List aList = this.sessionFactory.getCurrentSession().createQuery("from MobileVendor mobile").list();
		
		return aList;
	}
}
