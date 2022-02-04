package au.com.origin.payment.rest.advice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import au.com.origin.payment.error.ErrorCode;
import au.com.origin.payment.error.PaymentAccessException;
import au.com.origin.payment.error.PaymentException;

@ControllerAdvice
public class PaymentProcessExceptionHandler {

	@Autowired
	private ErrorCode errorCode;

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest requset) {
		exception.printStackTrace();
		return new ResponseEntity<>(map(errorCode.getBadRequestCode(), exception.getFieldError().getDefaultMessage()), BAD_REQUEST);
	}

	@ExceptionHandler(PaymentAccessException.class)
	public ResponseEntity<Object> handlePaymentAccessException(PaymentAccessException exception, WebRequest requset) {
		exception.printStackTrace();
		return new ResponseEntity<>(map(exception.getCode(), exception.getMessage()), FORBIDDEN);
	}

	@ExceptionHandler(PaymentException.class)
	public ResponseEntity<Object> handleAllExceptionMethod(PaymentException exception, WebRequest requset) {
		exception.printStackTrace();
		return new ResponseEntity<>(map(exception.getCode(), exception.getMessage()), INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleAllExceptionMethod(Exception exception, WebRequest requset) {
		exception.printStackTrace();
		return new ResponseEntity<>(map(errorCode.getGenericErrorCode(), exception.getMessage()), INTERNAL_SERVER_ERROR);
	}
	
	private Map<String, String> map(String code, String msg) {
		Map<String, String> map = new HashMap<>();
		map.put("code", code);
		map.put("message", msg);
		map.put("timestamp", LocalDateTime.now().toString());
		return map;
	}
}