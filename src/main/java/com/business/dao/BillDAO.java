package com.business.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.business.beans.HibernateSessionFactory;
import com.business.models.Bill;
import com.business.validation.Constants;

@Component
public class BillDAO {

	@Autowired
	private HibernateSessionFactory hibernateSessionFactory;

	public void saveBill(Bill bill) throws Exception {
		Session session = getSessionFromSessionFactory();
		session.beginTransaction();
		session.save(bill);
		session.getTransaction().commit();
	}

	public Bill getBillById(String billId) throws Exception {
		Session session = getSessionFromSessionFactory();
		session.beginTransaction();
		Bill bill = session.get(Bill.class, billId);
		session.getTransaction().commit();
		return bill;
	}

	public void updateBill(Bill bill) throws Exception {
		Session session = getSessionFromSessionFactory();
		session.beginTransaction();
		session.update(bill);
		session.getTransaction().commit();
	}

	public Boolean deleteBillById(String billId) throws Exception {
		Boolean flag = false;
		Session session = getSessionFromSessionFactory();
		session.beginTransaction();
		Bill bill = session.get(Bill.class, billId);
		if (bill != null) {
			flag = true;
			session.delete(bill);
		}
		session.getTransaction().commit();
		return flag;
	}

	public List<Bill> getBillsByPartyId(Integer partyId) throws Exception {
		Session session = getSessionFromSessionFactory();
		session.beginTransaction();
		List<Bill> bills = session.createNamedQuery(Constants.QUERY_GET_BILLS_BY_PARTY_ID)
				.setParameter("partyId", partyId).getResultList();
		session.getTransaction().commit();
		return bills;
	}

	public List<Bill> getBillByInvoiceNumber(String invoiceNumber) throws Exception {
		Session session = getSessionFromSessionFactory();
		session.beginTransaction();
		List<Bill> bill = session.createNamedQuery(Constants.QUERY_GET_BILL_BY_INVOICE_NUMBER)
				.setParameter("invoiceNumber", invoiceNumber).getResultList();// named query for invoice Number
		session.getTransaction().commit();
		return bill;
	}

	public Boolean deleteByInvoiceNumber(String invoiceNumber) throws Exception {
		Boolean flag = false;
		Session session = getSessionFromSessionFactory();
		session.beginTransaction();
		List<Bill> bills = session.createNamedQuery(Constants.QUERY_GET_BILL_BY_INVOICE_NUMBER)
				.setParameter("invoiceNumber", invoiceNumber).getResultList();
		if (!bills.isEmpty()) {
			flag = true;
			session.delete(bills.get(0));
		}
		session.getTransaction().commit();
		return flag;
	}

	private Session getSessionFromSessionFactory() throws Exception {
		return hibernateSessionFactory.getSessionFactory().getCurrentSession();
	}

	public Double getPurchaseAmountOfParty(Integer partyId) throws Exception {
		Session session = getSessionFromSessionFactory();
		session.beginTransaction();
		Double purchaseAmount = (Double) session.createNamedQuery(Constants.QUERY_GET_PURCHASE_AMOUNT_BY_PARTY_ID)
				.setParameter("partyId", partyId).getSingleResult();
		session.getTransaction().commit();
		return purchaseAmount;
	}
	
	public Double getSaleAmountToParty(Integer partyId) throws Exception {
		Session session = getSessionFromSessionFactory();
		session.beginTransaction();
		Double saleAmount = (Double) session.createNamedQuery(Constants.QUERY_GET_SALE_AMOUNT_BY_PARTY_ID)
				.setParameter("partyId", partyId).getSingleResult();
		session.getTransaction().commit();
		return saleAmount;
	}
	
}
