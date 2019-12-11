package com.business.models;

public class BusinessConfiguration {
	
	private BillBalanceProcessor adjustBillBalance = BillBalanceProcessor.OLD_TO_NEW;

	public BillBalanceProcessor getAdjustBillBalance() {
		return adjustBillBalance;
	}

	public void setAdjustBillBalance(BillBalanceProcessor adjustBillBalance) {
		this.adjustBillBalance = adjustBillBalance;
	}

}
