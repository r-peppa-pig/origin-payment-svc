package au.com.origin.payment.rest.controller;

import static au.com.origin.payment.constant.PaymentConstant.MESSAGE;
import static au.com.origin.payment.constant.PaymentConstant.SUCCESS;
import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import au.com.origin.payment.model.Payment;
import au.com.origin.payment.rest.model.PaymentRequest;
import au.com.origin.payment.service.PaymentService;

/**
 * Provides restful interface for the application.
 * @author peppapig
 *
 */
@RestController
public class PaymentProcessController {

	@Autowired
	private PaymentService service;

	/**
	 * Saves payment information.
	 * @param paymentRequest
	 * @return
	 */
	@RequestMapping(value = "/payment", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> create(@RequestBody @Valid @NotNull PaymentRequest paymentRequest) {
		ofNullable(paymentRequest)
		.map(req -> Payment.builder()
				.referenceNumber(req.getReferenceNumber())
				.bsb(req.getBsb())
				.accountNumber(req.getAccountNumber())
				.amount(req.getAmount())
				.build())
		.ifPresent(service::savePayment);
		return ResponseEntity.status(CREATED).body(Map.of(MESSAGE, SUCCESS));
	}
}
