package au.com.origin.payment.rest.advice;

import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import au.com.origin.payment.error.ErrorCode;
import au.com.origin.payment.error.PaymentAccessException;
import au.com.origin.payment.error.PaymentException;

@RunWith(MockitoJUnitRunner.class)
public class PaymentProcessExceptionHandlerTest {

	@InjectMocks
	PaymentProcessExceptionHandler paymentProcessExceptionHandler;
	
	@Mock
	private ErrorCode errorCode;

	@Test
	public void handleMethodArgumentNotValidException() {
		ResponseEntity<Object> response = paymentProcessExceptionHandler.handleMethodArgumentNotValidException(
				null, null);
		assertEquals(BAD_REQUEST.value(), response.getStatusCodeValue());
	}

	@Test
	public void handlePaymentAccessException() {
		ResponseEntity<Object> response = paymentProcessExceptionHandler.handlePaymentAccessException(
				new PaymentAccessException(null, null), null);
		assertEquals(FORBIDDEN.value(), response.getStatusCodeValue());
	}

	@Test
	public void handlePaymentException() {
		ResponseEntity<Object> response = paymentProcessExceptionHandler.handlePaymentException(
				new PaymentException(null, null), null);
		assertEquals(INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
	}

	@Test
	public void handleException() {
		ResponseEntity<Object> response = paymentProcessExceptionHandler.handleException(
				new RuntimeException(""), null);
		assertEquals(INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
	}
}