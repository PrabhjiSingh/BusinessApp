package com.business.services;

import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.business.dao.TransactionDAO;
import com.business.models.DetailedBalance;
import com.business.models.Transaction;
import com.business.validation.Constants;
import com.business.validation.TransactionValidation;

@Service
public class TransactionService {

	@Autowired
	private TransactionDAO transactionDAO;

	@Autowired
	private PartyService partyService;

	public boolean createTransaction(Transaction transaction) throws Exception {
		try {
			if (TransactionValidation.validateTransaction(transaction)) {
				transactionDAO.saveTransaction(transaction);
				return true;
			} else {
				throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, Constants.EXCEPTION_BAD_PAYLOAD_EXCEPTION);
			}
		} catch (ConstraintViolationException e) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
					Constants.EXCEPTION_CONSTRAINT_VIOLATION_EXCEPTION);
		}
	}

	public Transaction getTransactionById(String transactionId) throws Exception {
		Transaction transaction = transactionDAO.getTransactionById(transactionId);
		if (transaction == null) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		}
		return transaction;
	}

	public List<Transaction> getTransactionsByPartyId(Integer partyId) throws Exception {
		partyService.getPartyById(partyId);
		List<Transaction> transactions = transactionDAO.getTransactionsByPartyId(partyId);
		return transactions;
	}

	public void updateTransactionById(Transaction transaction, String transactionId) throws Exception {
		transaction.setTransactionUUID(transactionId);
		if (TransactionValidation.validateTransaction(transaction)) {
			Transaction oldTransaction = getTransactionById(transactionId);
			if (oldTransaction != null) {
				transactionDAO.updateTransaction(oldTransaction);
			}
		}
	}

	public Boolean deleteTransactionById(String transactionId) throws Exception {
		Boolean flag = transactionDAO.deleteTransactionById(transactionId);
		if (flag) {
			Transaction transaction = null;
			try {
				transaction = getTransactionById(transactionId);
			} catch (HttpClientErrorException e) {
			}
			return (transaction == null);
		} else {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, Constants.EXCEPTION_INVALID_BILL_ID);
		}
	}

	public Double getSumOfAllTransactionsOfAParty(Integer partyId, DetailedBalance detailedBalance) throws Exception {
		Double paymentMadeAmountToParty = transactionDAO.getPaymentMadeAmountFromParty(partyId);
		if(paymentMadeAmountToParty == null) {
			paymentMadeAmountToParty = 0.0;
		}
		detailedBalance.setPayamentMade(paymentMadeAmountToParty);
		
		Double paymentReceivedAmountFromParty = transactionDAO.getPaymentReceivedAmountFromParty(partyId);	
		if(paymentReceivedAmountFromParty == null) {
			paymentReceivedAmountFromParty = 0.0;
		}
		detailedBalance.setPaymentReceived(paymentReceivedAmountFromParty);
		
		return paymentMadeAmountToParty - paymentReceivedAmountFromParty;
	}

}
