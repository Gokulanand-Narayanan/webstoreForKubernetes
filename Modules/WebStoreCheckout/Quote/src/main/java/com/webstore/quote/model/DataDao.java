package com.webstore.quote.model;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DataDao {

	public static void getDetail() {
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		SessionFactory factory = null;
		Session session = null;
		try{
			factory = cfg.buildSessionFactory();
			session = factory.openSession();
			String query = "from MobileVendor mobile";
			Query qry = session.createQuery(query);
			List l = qry.list();
			Iterator it = l.iterator();

			while (it.hasNext()) {
				Object o = it.next();
				MobileVendor pro = (MobileVendor) o;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (factory !=null && session !=null) {
				session.close();
				factory.close();
				System.out.println(" Sucessfully closed open connection... ");
			}
		}
	}
	
	public static void main(String[] args) {
		DataDao.getDetail();
	}
	
}
