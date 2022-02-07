package au.com.origin.payment.mapper;

import java.math.BigDecimal;
import static java.util.Optional.ofNullable;

import org.springframework.stereotype.Component;

import au.com.origin.payment.model.Payment;
import au.com.origin.payment.service.ext.io.model.FileIOPayment;


@Component
public class PaymentMapper {
	public FileIOPayment map(final Payment payment, final String paymentDateTime,
			final BigDecimal gstAmount, 
			final BigDecimal amountExcludingGst) {
		return ofNullable(payment)
		.map(obj -> FileIOPayment.builder()
				.paymentDateTime(paymentDateTime)
				.referenceNumber(payment.getReferenceNumber())
				.bsb(payment.getBsb())
				.accountNumber(payment.getAccountNumber())
				.totalAmountIncludingGst(payment.getAmount())
				.gstAmount(gstAmount)
				.amountExcludingGst(amountExcludingGst)
				.build())
		.orElse(null);
	}
}
