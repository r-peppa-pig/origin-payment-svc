package au.com.origin.payment.rest.controller;

import static java.util.Optional.ofNullable;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import au.com.origin.payment.model.Payment;
import au.com.origin.payment.rest.model.PaymentRequest;
import au.com.origin.payment.service.PaymentService;

@RestController
public class PaymentProcessController {

	@Autowired
	private PaymentService service;

	@RequestMapping(value = "/payment", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> create(@RequestBody @Valid @NotNull PaymentRequest paymentRequest) {
		System.out.println("PaymentRequest ===> " + paymentRequest);
		ofNullable(paymentRequest)
		.map(req -> Payment.builder()
				.referenceNumber(req.getReferenceNumber())
				.bsb(req.getBsb())
				.accountNumber(req.getAccountNumber())
				.amount(req.getAmount())
				.build())
		.ifPresent(service::savePayment);
		return ResponseEntity.ok().body(1);
	}
}
