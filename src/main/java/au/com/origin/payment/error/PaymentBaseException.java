package au.com.origin.payment.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
//@AllArgsConstructor
public class PaymentBaseException  extends RuntimeException {

	private static final long serialVersionUID = -3597418755345253763L;
	private String code;
	private String message;
	
	public PaymentBaseException(final String code, final String message, final Throwable cause) {
		super(cause);
		this.code = code;
		this.message = message;
	}

	public PaymentBaseException(final String code, final String message) {
		this.code = code;
		this.message = message;
	}
}
