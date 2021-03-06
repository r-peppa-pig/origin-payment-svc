package au.com.origin.payment.error;

import lombok.Data;

/**
 * Represents exception occurring in the application.
 * @author peppapig
 *
 */
@Data
public class PaymentException  extends PaymentBaseException {

	private static final long serialVersionUID = 3930731265366331256L;
	
	public PaymentException(final String code, final String message) {
		super(code, message);
	}

	public PaymentException(final String code, final String message, final Throwable cause) {
		super(code, message, cause);
	}
}
