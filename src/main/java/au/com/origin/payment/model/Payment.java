package au.com.origin.payment.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model representing service layer Payment object.
 * @author peppapig
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class Payment {
	
    private String referenceNumber;
	
    private int bsb;
	
	private int accountNumber;
	
	private BigDecimal amount;

	private BigDecimal gstRate;
}
