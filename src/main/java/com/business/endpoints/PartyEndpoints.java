package com.business.endpoints;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;

import com.business.models.DetailedBalance;
import com.business.models.Party;
import com.business.models.PartyBalance;
import com.business.services.PartyService;

@RestController
public class PartyEndpoints {

	@Autowired
	private PartyService partyService;

	@GetMapping(value = "/party", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Party>> getListOfParties() throws Exception {
		List<Party> parties = partyService.getListOfParties();
		return new ResponseEntity<List<Party>>(parties, HttpStatus.OK);
	}

	@GetMapping(value = "/party/{partyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Party> getByPartyId(@PathVariable final Integer partyId) throws Exception {
		Party party = null;
		try {
			party = partyService.getPartyById(partyId);
		} catch (HttpClientErrorException e) {
			if (e.getMessage().equalsIgnoreCase("404 NOT_FOUND")) {
				return new ResponseEntity<Party>(HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<Party>(party, HttpStatus.OK);
	}

	@DeleteMapping(value = "/party/{partyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteByPartyId(@PathVariable final Integer partyId) throws Exception {
		Boolean flag = partyService.deleteByPartyId(partyId);
		if (flag) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/party/{partyId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> updatePartyById(@RequestBody final Party party, @PathVariable final Integer partyId)
			throws Exception {

		partyService.updateParty(party, partyId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@PostMapping(value = "/party", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> post(@RequestBody final Party party, UriComponentsBuilder builder) throws Exception {
		Boolean flag = partyService.createParty(party);
		if (flag) {
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/party/{partyId}/balance", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PartyBalance> getBalanceByPartyId(@PathVariable final Integer partyId) throws Exception {
		
		PartyBalance partyBalance = new PartyBalance();
		partyBalance.setDetailedBalance(new DetailedBalance());
		try {
			partyBalance = partyService.getBalanceByPartyId(partyId, partyBalance);
		} catch (HttpClientErrorException e) {
			if (e.getMessage().equalsIgnoreCase("404 NOT_FOUND")) {
				return new ResponseEntity<PartyBalance>(HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<PartyBalance>(partyBalance, HttpStatus.OK);
	}
}
