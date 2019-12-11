package com.business.validation;

import com.business.models.Party;

public class PartyValidation {
	
	public static Boolean validateParty(Party party) {
		return ValidationUtility.checkForNullOrEmptyString(party.getPartyName());
	}
}
