package com.webstore.quote.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.webstore.persistence.hibernate.GenericHibernateDAO;
import com.webstore.quote.dao.CheckoutQuoteItemDAO;
import com.webstore.quote.model.CheckoutQuoteItem;

@Repository
public class PhoneModelsDAOImpl extends GenericHibernateDAO<CheckoutQuoteItem, Integer>
{
	
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf)
	{
	  this.sessionFactory = sf;
	}

	public void listCheckoutQuoteItems(int quoteId)
	{
	   this.sessionFactory.getCurrentSession().createQuery("from MobileVendor mobile");
	}

	
}
