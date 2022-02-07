package au.com.origin.payment.rest.model;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import static au.com.origin.payment.constant.PaymentConstant.*;

/**
 * Payment request rest model.
 * @author peppapig
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class PaymentRequest {
	
    @NotEmpty(message = "{validation.reference.number.notEmpty}")
	@Size(min = REFERENCE_NUMBER_SIZE, max = REFERENCE_NUMBER_SIZE, message = "{validation.reference.number.invalid}")
    @Digits(integer = 10, fraction = 0, message = "{validation.reference.number.format}")
	@JsonProperty("referenceNumber")
	private String referenceNumber;
	
	@Min(value = BSB_MIN_VALUE, message = "{validation.bsb.number.invalid}")
	@Max(value = BSB_MAX_VALUE, message = "{validation.bsb.number.invalid}")
	@JsonProperty("bsb")
	private int bsb;
	
	@Min(value = ACCOUNT_NUMBER_MIN_VALUE, message = "{validation.account.number.invalid}")
	@Max(value = ACCOUNT_NUMBER_MAX_VALUE, message = "{validation.account.number.invalid}")
	@JsonProperty("accountNumber")
	private int accountNumber;
	
	@NotNull(message = "{validation.amount.notEmpty}")
	@DecimalMin(value = AMOUNT_MIN_VALUE, message = "{validation.amount.value.min}")
	@DecimalMax(value = AMOUNT_MAX_VALUE, message = "{validation.amount.value.max}")
	@JsonProperty("amount")
	private BigDecimal amount;
}
