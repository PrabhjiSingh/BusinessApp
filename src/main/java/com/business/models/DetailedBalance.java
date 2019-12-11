package com.business.models;

public class DetailedBalance {

	private Double purchaseAmount;

	private Double saleAmount;

	private Double paymentReceived;

	private Double payamentMade;

	public Double getPurchaseAmount() {
		return purchaseAmount;
	}

	public void setPurchaseAmount(Double purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}

	public Double getSaleAmount() {
		return saleAmount;
	}

	public void setSaleAmount(Double saleAmount) {
		this.saleAmount = saleAmount;
	}

	public Double getPaymentReceived() {
		return paymentReceived;
	}

	public void setPaymentReceived(Double paymentReceived) {
		this.paymentReceived = paymentReceived;
	}

	public Double getPayamentMade() {
		return payamentMade;
	}

	public void setPayamentMade(Double payamentMade) {
		this.payamentMade = payamentMade;
	}
	
}
