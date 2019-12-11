package com.business.models;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.business.validation.Constants;

@Entity
@Table(name = "Transaction")
@NamedQueries({
		@NamedQuery(name = Constants.QUERY_GET_TRANSACTIONS_BY_PARTY_ID, query = "Select t from Transaction t where t.transactionParty.id = :partyId"),
		@NamedQuery(name = Constants.QUERY_GET_PAYMENT_RECEIVED_AMOUNT_BY_PARTY_ID, query = "Select SUM(t.transactionAmount) from Transaction t where t.transactionParty.id = :partyId AND t.transactionType = 'PAYMENT_RECEIVED'"),
		@NamedQuery(name = Constants.QUERY_GET_PAYMENT_MADE_AMOUNT_BY_PARTY_ID, query = "Select SUM(t.transactionAmount) from Transaction t where t.transactionParty.id = :partyId AND t.transactionType = 'PAYMENT_MADE'")
})
public class Transaction {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String transactionUUID;

	@Basic(optional = false)
	@Temporal(TemporalType.DATE)
	private Date transactionDate;

	@Basic(optional = false)
	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;

	@Basic(optional = false)
	@Enumerated(EnumType.STRING)
	private TransactionSubType transactionSubType;

	@Basic(optional = false)
	private Double transactionAmount;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "partyId")
	private Party transactionParty;

	@Basic(optional = false)
	@Enumerated(EnumType.STRING)
	private TransactionMode transactionMode = TransactionMode.CASH;

	@Basic(optional = false)
	private String transactioNumber;

	@Basic
	private String transactionPlace;

	@Basic
	private String transactionDescription;

	@Basic
	private Byte[] transactionImage;
	
	
	public Transaction() {}

	public Byte[] getTransactionImage() {
		return transactionImage;
	}

	public void setTransactionImage(Byte[] transactionImage) {
		this.transactionImage = transactionImage;
	}

	public String getTransactionUUID() {
		return transactionUUID;
	}

	public void setTransactionUUID(String transactionUUID) {
		this.transactionUUID = transactionUUID;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(Double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public TransactionSubType getTransactionSubType() {
		return transactionSubType;
	}

	public void setTransactionSubType(TransactionSubType transactionSubType) {
		this.transactionSubType = transactionSubType;
	}

	public TransactionMode getTransactionMode() {
		return transactionMode;
	}

	public void setTransactionMode(TransactionMode transactionMode) {
		this.transactionMode = transactionMode;
	}

	public String getTransactioNumber() {
		return transactioNumber;
	}

	public void setTransactioNumber(String transactioNumber) {
		this.transactioNumber = transactioNumber;
	}

	public String getTransactionDescription() {
		return transactionDescription;
	}

	public void setTransactionDescription(String transactionDescription) {
		this.transactionDescription = transactionDescription;
	}

	public String getTransactionPlace() {
		return transactionPlace;
	}

	public void setTransactionPlace(String transactionPlace) {
		this.transactionPlace = transactionPlace;
	}

	public Party getTransactionParty() {
		return transactionParty;
	}

	public void setTransactionParty(Party transactionParty) {
		this.transactionParty = transactionParty;
	}
}
