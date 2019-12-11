package com.business.models;

public class PartyBalance {
	
	private Double totalBalance;
	
	private BalanceType balanceType;
	
	private Party party;
	
	private DetailedBalance detailedBalance;

	public Double getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(Double totalBalance) {
		this.totalBalance = totalBalance;
	}

	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}

	public DetailedBalance getDetailedBalance() {
		return detailedBalance;
	}

	public void setDetailedBalance(DetailedBalance detailedBalance) {
		this.detailedBalance = detailedBalance;
	}

	public BalanceType getBalanceType() {
		return balanceType;
	}

	public void setBalanceType(BalanceType balanceType) {
		this.balanceType = balanceType;
	}

}
