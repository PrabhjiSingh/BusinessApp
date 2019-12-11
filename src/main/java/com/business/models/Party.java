package com.business.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "Party", uniqueConstraints = { @UniqueConstraint(columnNames = { "partyName" })} )
public class Party {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Basic(optional = false)
	private String partyName;

	@Enumerated(EnumType.STRING)
	private PartyType partyType;

	@Basic
	private String partyOwner;

	@Basic
	@Pattern(regexp = ".+@.+")
	private String partyEmail;

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	@JoinColumn(name = "partyId")
	private Set<PartyPhoneNumber> phoneNumber = new HashSet<PartyPhoneNumber>();

	@Basic
	private String address;

	@Basic
	private String partyDescription;

	public Party() {
		// Do nothing. Required by hibernate for operations
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPartyDescription() {
		return partyDescription;
	}

	public void setPartyDescription(String partyDescription) {
		this.partyDescription = partyDescription;
	}

	public PartyType getPartyType() {
		return partyType;
	}

	public void setPartyType(PartyType partyType) {
		this.partyType = partyType;
	}

	public String getPartyOwner() {
		return partyOwner;
	}

	public void setPartyOwner(String partyOwner) {
		this.partyOwner = partyOwner;
	}

	public String getPartyEmail() {
		return partyEmail;
	}

	public void setPartyEmail(String partyEmail) {
		this.partyEmail = partyEmail;
	}

	public Set<PartyPhoneNumber> getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Set<PartyPhoneNumber> phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
