package com.business.validation;

import com.business.models.Bill;

public class BillValidation {

	public static boolean validateBill(Bill bill) {
		return true;
		//return (ValidationUtility.checkForNullOrEmptyString(bill.getInvoiceNumber()));
	}

}
