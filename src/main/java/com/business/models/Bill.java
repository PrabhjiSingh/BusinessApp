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
@Table(name = "Bill")
@NamedQueries({
		@NamedQuery(name = Constants.QUERY_GET_BILL_BY_INVOICE_NUMBER, query = "Select b from Bill b where b.invoiceNumber = :invoiceNumber"),
		@NamedQuery(name = Constants.QUERY_GET_BILLS_BY_PARTY_ID, query = "Select b from Bill b where b.party.id = :partyId"),
		@NamedQuery(name = Constants.QUERY_GET_PURCHASE_AMOUNT_BY_PARTY_ID, query = "Select SUM(b.billAmount) from Bill b where b.party.id = :partyId AND (b.billType = 'OPENING_BALANCE_PURCHASE' OR b.billType = 'PURCHASE')"),
		@NamedQuery(name = Constants.QUERY_GET_SALE_AMOUNT_BY_PARTY_ID, query = "Select SUM(b.billAmount) from Bill b where b.party.id = :partyId AND (b.billType = 'OPENING_BALANCE_SALE' OR b.billType = 'SALE_WHOLESALE' OR b.billType = 'SALE_RETAIL')"),
		
})
public class Bill {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String billUUID;

	@Basic(optional = false)
	private String invoiceNumber;

	@Basic(optional = false)
	@Enumerated(EnumType.STRING)
	private BillType billType;

	@Basic(optional = false)
	private Double billAmount;

	@Basic(optional = false)
	@Temporal(TemporalType.DATE)
	private Date billDate;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "partyId")
	private Party party;

	@Basic
	@Enumerated(EnumType.STRING)
	private BillStatus billStatus = BillStatus.BLANK;

	@Basic
	private Double unpaidBalance;

	@Basic
	private String billPlace;

	@Basic
	private String billDescription;

	@Basic
	private Byte[] billImage;

	public String getBillUUID() {
		return billUUID;
	}

	public void setBillUUID(String billUUID) {
		this.billUUID = billUUID;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public BillType getBillType() {
		return billType;
	}

	public void setBillType(BillType billType) {
		this.billType = billType;
	}

	public Double getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(Double billAmount) {
		this.billAmount = billAmount;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public BillStatus getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(BillStatus billStatus) {
		this.billStatus = billStatus;
	}

	public Double getUnpaidBalance() {
		return unpaidBalance;
	}

	public void setUnpaidBalance(Double unpaidBalance) {
		this.unpaidBalance = unpaidBalance;
	}

	public String getBillPlace() {
		return billPlace;
	}

	public void setBillPlace(String billPlace) {
		this.billPlace = billPlace;
	}

	public String getBillDescription() {
		return billDescription;
	}

	public void setBillDescription(String billDescription) {
		this.billDescription = billDescription;
	}

	public Byte[] getBillImage() {
		return billImage;
	}

	public void setBillImage(Byte[] billImage) {
		this.billImage = billImage;
	}

	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}
}
