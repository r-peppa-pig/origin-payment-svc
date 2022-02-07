package au.com.origin.payment.rest.advice;

import static au.com.origin.payment.constant.PaymentConstant.CODE;
import static au.com.origin.payment.constant.PaymentConstant.MESSAGE;
import static au.com.origin.payment.constant.PaymentConstant.TIMESTAMP;
import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import au.com.origin.payment.error.ErrorCode;
import au.com.origin.payment.error.PaymentAccessException;
import au.com.origin.payment.error.PaymentException;

/**
 * Global exception handling for the application.
 * Returns specified error code and message and does not send the exception trace to prevent info leakage.
 * @author peppapig
 *
 */
@ControllerAdvice
public class PaymentProcessExceptionHandler {

	@Autowired
	private ErrorCode errorCode;

	/**
	 * Handles invalid arguments exception. 
	 * @param exception
	 * @param requset
	 * @return 400 Bad Request
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest requset) {
		return new ResponseEntity<>(map(errorCode.getBadRequestCode(),
				ofNullable(exception)
				.map(MethodArgumentNotValidException::getFieldError)
				.map(FieldError::getDefaultMessage)
				.orElse(errorCode.getBadRequestMsg())), BAD_REQUEST);
	}

	/**
	 * Handles application access exception.
	 * @param exception
	 * @param requset
	 * @return FORBIDDEN 403
	 */
	@ExceptionHandler(PaymentAccessException.class)
	public ResponseEntity<Object> handlePaymentAccessException(PaymentAccessException exception, WebRequest requset) {
		return new ResponseEntity<>(map(exception.getCode(), exception.getMessage()), FORBIDDEN);
	}

	/**
	 * Handles application business exception
	 * @param exception
	 * @param requset
	 * @return INTERNAL_SERVER_ERROR 500
	 */
	@ExceptionHandler(PaymentException.class)
	public ResponseEntity<Object> handlePaymentException(PaymentException exception, WebRequest requset) {
		return new ResponseEntity<>(map(exception.getCode(), exception.getMessage()), INTERNAL_SERVER_ERROR);
	}

	/**
	 * Handles all exceptions not covered as failsafe.
	 * @param exception
	 * @param requset
	 * @return INTERNAL_SERVER_ERROR 500
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleException(Exception exception, WebRequest requset) {
		return new ResponseEntity<>(map(errorCode.getGenericErrorCode(), exception.getMessage()), INTERNAL_SERVER_ERROR);
	}

	private Map<String, String> map(String code, String msg) {
		Map<String, String> map = new HashMap<>();
		map.put(CODE, code);
		map.put(MESSAGE, msg);
		map.put(TIMESTAMP, LocalDateTime.now().toString());
		return map;
	}
}