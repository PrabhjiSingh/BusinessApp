package com.business.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.business.beans.HibernateSessionFactory;
import com.business.models.Transaction;
import com.business.validation.Constants;

@Component
public class TransactionDAO {

	@Autowired
	private HibernateSessionFactory hibernateSessionFactory;

	public void saveTransaction(Transaction transaction) throws Exception {
		Session session = getSessionFromSessionFactory();
		session.beginTransaction();
		session.save(transaction);
		session.getTransaction().commit();
	}

	public Transaction getTransactionById(String transactionId) throws Exception {
		Session session = getSessionFromSessionFactory();
		session.beginTransaction();
		Transaction transaction = session.get(Transaction.class, transactionId);
		session.getTransaction().commit();
		return transaction;
	}

	public void updateTransaction(Transaction transaction) throws Exception {
		Session session = getSessionFromSessionFactory();
		session.beginTransaction();
		session.update(transaction);
		session.getTransaction().commit();
	}

	public Boolean deleteTransactionById(String transactionId) throws Exception {
		Boolean flag = false;
		Session session = getSessionFromSessionFactory();
		session.beginTransaction();
		Transaction transaction = session.get(Transaction.class, transactionId);
		if (transaction != null) {
			flag = true;
			session.delete(transaction);
		}
		session.getTransaction().commit();
		return flag;
	}

	public List<Transaction> getTransactionsByPartyId(Integer partyId) throws Exception {
		Session session = getSessionFromSessionFactory();
		session.beginTransaction();
		List<Transaction> transactions = session.createNamedQuery(Constants.QUERY_GET_TRANSACTIONS_BY_PARTY_ID)
				.setParameter("partyId", partyId).getResultList();
		session.getTransaction().commit();
		return transactions;
	}

	private Session getSessionFromSessionFactory() throws Exception {
		return hibernateSessionFactory.getSessionFactory().getCurrentSession();
	}

	public Double getPaymentReceivedAmountFromParty(Integer partyId) throws Exception {
		Session session = getSessionFromSessionFactory();
		session.beginTransaction();
		Double saleAmount = (Double) session.createNamedQuery(Constants.QUERY_GET_PAYMENT_RECEIVED_AMOUNT_BY_PARTY_ID)
				.setParameter("partyId", partyId).getSingleResult();
		session.getTransaction().commit();
		return saleAmount;
	}
	
	public Double getPaymentMadeAmountFromParty(Integer partyId) throws Exception {
		Session session = getSessionFromSessionFactory();
		session.beginTransaction();
		Double saleAmount = (Double) session.createNamedQuery(Constants.QUERY_GET_PAYMENT_MADE_AMOUNT_BY_PARTY_ID)
				.setParameter("partyId", partyId).getSingleResult();
		session.getTransaction().commit();
		return saleAmount;
	}

}
