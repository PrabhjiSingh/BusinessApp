package com.business.validation;

import com.business.models.Transaction;

public class TransactionValidation {

	public static boolean validateTransaction(Transaction transaction) {
		return true;
		//return (ValidationUtility.checkForNullOrEmptyString(bill.getInvoiceNumber()));
	}
}
