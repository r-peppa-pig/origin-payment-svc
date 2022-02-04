package au.com.origin.payment.mapper;

import java.math.BigDecimal;

import au.com.origin.payment.model.Payment;
import au.com.origin.payment.service.ext.io.model.FileIOPayment;


public class PaymentMapper {


	public FileIOPayment map(final Payment payment, final String paymentDateTime,
			final BigDecimal gstAmount, 
			final BigDecimal amountExcludingGst) {
		return FileIOPayment.builder()
		.paymentDateTime(paymentDateTime)
		.referenceNumber(payment.getReferenceNumber())
		.bsb(payment.getBsb())
		.accountNumber(payment.getAccountNumber())
		.totalAmountIncludingGst(payment.getAmount())
		.gstAmount(gstAmount)
		.amountExcludingGst(amountExcludingGst)
		.build();
	}
}
