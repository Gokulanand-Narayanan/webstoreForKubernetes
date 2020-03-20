package com.webstore.products.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class DataDao {

	public void getDetail() throws IOException {
		Properties props = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("hdb.properties");
		props.load(inputStream);
		
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml").addProperties(props);
		
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
		
		SessionFactory factory = cfg.buildSessionFactory(serviceRegistry);
		Session session = factory.openSession();

		Query qry = session.createQuery("from Product p");
		List l = qry.list();
		Iterator it = l.iterator();

		while (it.hasNext()) {
			Object o = it.next();
			Product pro = (Product) o;
		}
		session.close();
		factory.close();
	}
	public static void main(String[] args) throws IOException {
		DataDao dataDao = new DataDao();
		dataDao.getDetail();
	}
}
