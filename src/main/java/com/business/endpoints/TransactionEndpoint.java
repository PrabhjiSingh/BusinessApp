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
import org.springframework.web.util.UriComponentsBuilder;

import com.business.models.Transaction;
import com.business.services.TransactionService;

@RestController
public class TransactionEndpoint {

	@Autowired
	private TransactionService transactionService;

	@PostMapping(value = "/transaction", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> post(@RequestBody final Transaction transaction, UriComponentsBuilder builder)
			throws Exception {
		Boolean flag = transactionService.createTransaction(transaction);
		if (flag) {
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/party/{partyId}/transaction", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Transaction>> getListOfTransactions(@PathVariable final Integer partyId)
			throws Exception {
		List<Transaction> transactions = transactionService.getTransactionsByPartyId(partyId);
		return new ResponseEntity<List<Transaction>>(transactions, HttpStatus.OK);
	}

	@GetMapping(value = "/transaction/{transactionId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Transaction> getTransactionById(@PathVariable final String transactionId) throws Exception {
		Transaction transaction = transactionService.getTransactionById(transactionId);
		return new ResponseEntity<Transaction>(transaction, HttpStatus.OK);
	}

	@PutMapping(value = "/transaction/{transactionId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> updateTransactionById(@RequestBody final Transaction transaction,
			@PathVariable final String transactionId) throws Exception {
		transactionService.updateTransactionById(transaction, transactionId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping(value = "/transaction/{transactionId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteTransactionById(@PathVariable final String transactionId) throws Exception {
		Boolean flag = transactionService.deleteTransactionById(transactionId);
		if (flag) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
