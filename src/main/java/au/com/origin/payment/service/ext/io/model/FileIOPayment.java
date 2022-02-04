package au.com.origin.payment.service.ext.io.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileIOPayment {
	private String paymentDateTime;

	private String referenceNumber;

	private int bsb;

	private int accountNumber;

	//private BigDecimal amount;
	
	private BigDecimal totalAmountIncludingGst;
	
	private BigDecimal gstAmount;
	
	private BigDecimal amountExcludingGst;

}
