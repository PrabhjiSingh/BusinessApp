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

import com.business.models.Bill;
import com.business.services.BillingService;

@RestController
public class BillingEndpoint {

	@Autowired
	private BillingService billingService;

	@PostMapping(value = "/bill", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> post(@RequestBody final Bill bill, UriComponentsBuilder builder) throws Exception {
		Boolean flag = billingService.createBill(bill);
		if (flag) {
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/party/{partyId}/bill", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Bill>> getListOfBills(@PathVariable final Integer partyId) throws Exception {
		List<Bill> bills = billingService.getBillsByPartyId(partyId);
		return new ResponseEntity<List<Bill>>(bills, HttpStatus.OK);
	}

	@GetMapping(value = "/bill/{billId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Bill> getBillById(@PathVariable final String billId) throws Exception {
		Bill bill = billingService.getBillById(billId);
		return new ResponseEntity<Bill>(bill, HttpStatus.OK);
	}
	
	@PutMapping(value = "/bill/{billId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> updateBillById(@RequestBody final Bill bill, @PathVariable final String billId)
			throws Exception {
		billingService.updateBillById(bill, billId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping(value = "/bill/{billId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteBillById(@PathVariable final String billId) throws Exception {
		Boolean flag = billingService.deleteBillById(billId);
		if (flag) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/bill/invoiceNumber/{invoiceNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Bill>> getBillByInvoiceNumber(@PathVariable final String invoiceNumber)
			throws Exception {
		List<Bill> bills = null;
		bills = billingService.getBillByInvoiceNumber(invoiceNumber);
		return new ResponseEntity<List<Bill>>(bills, HttpStatus.OK);
	}
	
	@PutMapping(value = "/bill/invoiceNumber/{invoiceNumber}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> updateBillByInvoiceNumber(@RequestBody final Bill bill, @PathVariable final String invoiceNumber)
			throws Exception {

		billingService.updateBillByInvoiceNumber(bill, invoiceNumber);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping(value = "/bill/invoiceNumber/{invoiceNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteByInvoiceNumber(@PathVariable final String invoiceNumber) throws Exception {
		Boolean flag = billingService.deleteByInvoiceNumber(invoiceNumber);
		if (flag) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}