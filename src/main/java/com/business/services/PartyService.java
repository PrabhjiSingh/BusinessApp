package com.business.services;

import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.business.dao.PartyDAO;
import com.business.models.BalanceType;
import com.business.models.Party;
import com.business.models.PartyBalance;
import com.business.validation.Constants;
import com.business.validation.PartyValidation;

@Service
public class PartyService {

	@Autowired
	private PartyDAO partyDAO;

	@Autowired
	private BillingService billingService;

	@Autowired
	private TransactionService transactionService;

	public boolean createParty(Party party) throws Exception {
		try {
			if (PartyValidation.validateParty(party)) {
				partyDAO.saveParty(party);
				return true;
			} else {
				throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, Constants.EXCEPTION_BAD_PAYLOAD_EXCEPTION);
			}
		} catch (ConstraintViolationException e) {
			throw new HttpClientErrorException(HttpStatus.CONFLICT, Constants.EXCEPTION_PARTY_NAME_ALREADY_EXISTS);
		}
	}

	public Party getPartyById(Integer id) throws Exception {
		Party party = partyDAO.getPartyById(id);
		if (party == null) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		}
		return party;

	}

	public List<Party> getListOfParties() throws Exception {
		List<Party> parties = null;
		parties = partyDAO.getParties();
		return parties;
	}

	public void updateParty(Party party, Integer partyId) throws Exception {
		party.setId(partyId);
		if (PartyValidation.validateParty(party)) {
			Party oldParty = partyDAO.getPartyById(partyId);
			if (oldParty != null) {
				partyDAO.updateParty(party);
			} else {
				throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, Constants.EXCEPTION_PARTY_DOES_NOT_EXIST);
			}
		}
	}

	public Boolean deleteByPartyId(Integer partyId) throws Exception {
		Boolean flag = partyDAO.deletePartyById(partyId);
		if (flag) {
			Party party = null;
			try {
				party = getPartyById(partyId);
			} catch (HttpClientErrorException e) {
			}

			if (party == null) {
				return true;
			} else {
				return false;
			}
		} else {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, Constants.EXCEPTION_PARTY_DOES_NOT_EXIST);
		}
	}

	public PartyBalance getBalanceByPartyId(Integer partyId, PartyBalance partyBalance) throws Exception {
		Party party = getPartyById(partyId);
		if (party != null) {

			partyBalance.setParty(party);

			Double billSum = 0.0;
			Double transactionSum = 0.0;
			Double totalBalance = 0.0;

			// if billSum > 0, means liabilities
			billSum = billingService.getSumOfBillAmountOfAParty(partyId, partyBalance.getDetailedBalance());

			if (billSum >= 0.0) {
				// party is seller(Amritsar)

				transactionSum = transactionService.getSumOfAllTransactionsOfAParty(partyId,
						partyBalance.getDetailedBalance());

				totalBalance = billSum - transactionSum;
				if (totalBalance >= 0.0) {
					partyBalance.setTotalBalance(totalBalance);
					partyBalance.setBalanceType(BalanceType.CREDIT);
				} else {
					partyBalance.setTotalBalance(totalBalance);
					partyBalance.setBalanceType(BalanceType.DEBIT);
				}

			} else if (billSum < 0.0) {
				// party is buyer

				transactionSum = transactionService.getSumOfAllTransactionsOfAParty(partyId,
						partyBalance.getDetailedBalance());
				totalBalance = billSum - transactionSum;
				if (totalBalance >= 0.0) {
					partyBalance.setTotalBalance(-totalBalance);
					partyBalance.setBalanceType(BalanceType.CREDIT);
				} else {
					partyBalance.setTotalBalance(-totalBalance);
					partyBalance.setBalanceType(BalanceType.DEBIT);
				}
			}
			return partyBalance;
		} else {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, Constants.EXCEPTION_PARTY_DOES_NOT_EXIST);
		}
	}

}
