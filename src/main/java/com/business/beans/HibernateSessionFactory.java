package com.business.beans;

import javax.annotation.PostConstruct;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

import com.business.models.Bill;
import com.business.models.Party;
import com.business.models.PartyPhoneNumber;
import com.business.models.Transaction;

@Component
public class HibernateSessionFactory {

	private SessionFactory sessionFactory;
	
	@PostConstruct
    public void createSessionFactory() 
    {
		setSessionFactory(new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Party.class)
				.addAnnotatedClass(PartyPhoneNumber.class)
				.addAnnotatedClass(Bill.class)
				.addAnnotatedClass(Transaction.class)
				.buildSessionFactory());
		System.out.println("Hibernate Session Factory initialized");
    }

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
