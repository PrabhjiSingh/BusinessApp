package com.business.services;

import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.business.dao.BillDAO;
import com.business.models.Bill;
import com.business.models.DetailedBalance;
import com.business.validation.BillValidation;
import com.business.validation.Constants;

@Service
public class BillingService {

	@Autowired
	private BillDAO billDAO;

	@Autowired
	private PartyService partyService;

	public boolean createBill(Bill bill) throws Exception {
		try {
			if (BillValidation.validateBill(bill)) {
				billDAO.saveBill(bill);
				return true;
			} else {
				throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, Constants.EXCEPTION_BAD_PAYLOAD_EXCEPTION);
			}
		} catch (ConstraintViolationException e) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
					Constants.EXCEPTION_CONSTRAINT_VIOLATION_EXCEPTION);
		}
	}

	public Bill getBillById(String billId) throws Exception {
		Bill bill = billDAO.getBillById(billId);
		if (bill == null) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		}
		return bill;
	}

	public List<Bill> getBillsByPartyId(Integer partyId) throws Exception {
		partyService.getPartyById(partyId);
		List<Bill> bills = billDAO.getBillsByPartyId(partyId);
		return bills;
	}

	public void updateBillById(Bill bill, String billId) throws Exception {
		bill.setBillUUID(billId);
		if (BillValidation.validateBill(bill)) {
			Bill oldBill = getBillById(billId);
			if (oldBill != null) {
				billDAO.updateBill(bill);
			}
		}
	}

	public Boolean deleteBillById(String billId) throws Exception {
		Boolean flag = billDAO.deleteBillById(billId);
		if (flag) {
			Bill bill = null;
			try {
				bill = getBillById(billId);
			} catch (HttpClientErrorException e) {
			}

			if (bill == null) {
				return true;
			} else {
				return false;
			}
		} else {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, Constants.EXCEPTION_INVALID_BILL_ID);
		}
	}

	public List<Bill> getBillByInvoiceNumber(String invoiceNumber) throws Exception {
		List<Bill> billList = billDAO.getBillByInvoiceNumber(invoiceNumber);
		if (billList == null || billList.isEmpty()) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		}
		return billList;
	}

	public void updateBillByInvoiceNumber(Bill bill, String invoiceNumber) throws Exception {
		bill.setInvoiceNumber(invoiceNumber);
		if (BillValidation.validateBill(bill)) {

			List<Bill> bills = getBillByInvoiceNumber(invoiceNumber);
			if (bills == null) {
				throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, Constants.EXCEPTION_INVALID_INVOICE_NUMBER);
			} else if (bills.isEmpty()) {
				throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, Constants.EXCEPTION_INVALID_INVOICE_NUMBER);
			} else if (bills.size() > 1) {
				throw new HttpClientErrorException(HttpStatus.FORBIDDEN,
						Constants.EXCEPTION_MULTIPLE_BILLS_FOUND_WITH_SAME_INVOICE_NUMBER);
			} else {
				Bill oldBill = bills.get(0);
				if (oldBill != null) {
					bill.setBillUUID(oldBill.getBillUUID());
					billDAO.updateBill(bill);
				}
			}
		}
	}

	public Boolean deleteByInvoiceNumber(String invoiceNumber) throws Exception {
		List<Bill> bills = getBillByInvoiceNumber(invoiceNumber);
		if (bills.size() > 1) {
			throw new HttpClientErrorException(HttpStatus.FORBIDDEN,
					Constants.EXCEPTION_MULTIPLE_BILLS_FOUND_WITH_SAME_INVOICE_NUMBER);
		}

		Boolean flag = billDAO.deleteByInvoiceNumber(invoiceNumber);
		if (flag) {
			bills = null;
			try {
				bills = getBillByInvoiceNumber(invoiceNumber);
			} catch (HttpClientErrorException e) {
				return true;
			}

			if (bills != null && bills.isEmpty()) {
				return true;
			} else {
				return false;
			}
		} else {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
					Constants.EXCEPTION_INVALID_INVOICE_NUMBER_EXCEPTION);
		}
	}

	// purchase - sale
	public Double getSumOfBillAmountOfAParty(Integer partyId, DetailedBalance detailedBalance) throws Exception {
		Double purchaseAmountFromParty = billDAO.getPurchaseAmountOfParty(partyId);
		detailedBalance.setPurchaseAmount(purchaseAmountFromParty);
		Double saleAmountToParty = billDAO.getSaleAmountToParty(partyId);
		detailedBalance.setSaleAmount(saleAmountToParty);
		if(purchaseAmountFromParty == null) {
			purchaseAmountFromParty = 0.0;
		}	
		if(saleAmountToParty == null) {
			saleAmountToParty = 0.0;
		}
		return purchaseAmountFromParty - saleAmountToParty;
	}
}
