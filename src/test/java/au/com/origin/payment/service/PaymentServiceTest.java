package au.com.origin.payment.service;

import static au.com.origin.payment.constant.PaymentConstant.DATE_TIME_FORMAT;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;

import au.com.origin.payment.error.ErrorCode;
import au.com.origin.payment.error.PaymentException;
import au.com.origin.payment.mapper.PaymentMapper;
import au.com.origin.payment.model.Payment;
import au.com.origin.payment.service.ext.io.FileIOService;

@RunWith(MockitoJUnitRunner.class)
public class PaymentServiceTest {

	@InjectMocks
	private PaymentService paymentService;

	@Mock
	private FileIOService fileIoService;

	@Mock
	private PaymentMapper paymentMapper;

	@Mock
	private ErrorCode errorCode;

	@Value("#{new Double('${payment.gst.rate}')}")
	private BigDecimal gstRate;

	@Value("${payment.file.location.base}")
	private String fileLocationBase;

	private Payment payment;


	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	@Before
	public void setup() {
		ReflectionTestUtils.setField(paymentService, "dateTimeFormatter", DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
		ReflectionTestUtils.setField(paymentService, "gstRate", new BigDecimal("10.00"));

		payment = Payment.builder()
				.referenceNumber("483820")
				.bsb(123456)
				.accountNumber(123456)
				.amount(new BigDecimal("1000.12"))
				.build();
	}

	@Test
	public void savePayment_success() throws IOException {
		paymentService.savePayment(payment);
		verify(fileIoService,times(1)).savePayment(any(String.class), any());
	}

	@Test(expected = PaymentException.class)
	public void savePayment_IOException() throws IOException {
		doThrow(IOException.class).when(fileIoService).savePayment(any(String.class), any());
		paymentService.savePayment(payment);
	}

	@Test(expected = RuntimeException.class)
	public void savePayment_RuntimeException() throws IOException {
		doThrow(RuntimeException.class).when(fileIoService).savePayment(any(String.class), any());
		paymentService.savePayment(payment);
	}

}
