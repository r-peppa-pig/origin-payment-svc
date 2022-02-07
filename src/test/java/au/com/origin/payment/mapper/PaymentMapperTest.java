package au.com.origin.payment.mapper;


import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;

import au.com.origin.payment.model.Payment;
import au.com.origin.payment.service.ext.io.model.FileIOPayment;


public class PaymentMapperTest {

	private PaymentMapper mapper;

	private Payment payment;
	private String paymentDateTime;
	private BigDecimal gstAmount; 
	private BigDecimal amountExcludingGst;

	private String referenceNumber = "12345";
	private int bsb = 123456;
	private int accountNumber = 123456789;
	private BigDecimal amount = new BigDecimal("2000.00");

	@Before
	public void setup() {
		payment = Payment.builder()
				.referenceNumber(referenceNumber)
				.bsb(bsb)
				.accountNumber(accountNumber)
				.amount(amount)
				.build();
		paymentDateTime = "202202121015";
		gstAmount = new BigDecimal("10.00"); 
		amountExcludingGst = new BigDecimal("180.00");
		mapper = new PaymentMapper();
	}

	@Test
	public void map_success() {
		FileIOPayment expected = FileIOPayment.builder()
				.paymentDateTime(paymentDateTime)
				.referenceNumber(referenceNumber)
				.bsb(bsb)
				.accountNumber(accountNumber)
				.totalAmountIncludingGst(amount)
				.gstAmount(gstAmount)
				.amountExcludingGst(amountExcludingGst)
				.build();
		FileIOPayment actual = mapper.map(payment, paymentDateTime, gstAmount, amountExcludingGst);
		assertEquals(expected, actual);
	}

	@Test
	public void map_payment_null() {
		FileIOPayment actual = mapper.map(null, paymentDateTime, gstAmount, amountExcludingGst);
		assertNull(actual);
	}
}
