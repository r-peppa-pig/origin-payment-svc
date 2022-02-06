package au.com.origin.payment.error;

import lombok.Data;

@Data
public class PaymentAccessException extends PaymentBaseException {

	private static final long serialVersionUID = -1816242846070628719L;
	
	public PaymentAccessException(final String code, final String message) {
		super(code, message);
	}

	public PaymentAccessException(final String code, final String message, final Throwable cause) {
		super(code, message, cause);
	}

}
