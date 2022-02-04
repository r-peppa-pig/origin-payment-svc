package au.com.origin.payment.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import au.com.origin.payment.error.ErrorCode;
import au.com.origin.payment.error.PaymentException;
import au.com.origin.payment.mapper.PaymentMapper;
import au.com.origin.payment.model.Payment;
import au.com.origin.payment.service.ext.io.FileIOService;

import static au.com.origin.payment.constant.PaymentConstant.ONE_HUNDRED;

@Service
public class PaymentService {

	@Autowired
	private FileIOService fileIoService;

	@Autowired
	private PaymentMapper paymentMapper;

	@Autowired
	private DateTimeFormatter dateTimeFormatter; 

	@Autowired
	private ErrorCode errorCode;

	@Value("#{new Double('${payment.gst.rate}')}")
	private BigDecimal gstRate;

	@Value("${payment.file.location.base}")
	private String fileLocationBase;

	public void savePayment(final Payment payment) {
		String now = dateTimeFormatter.format(java.time.LocalDateTime.now());
		BigDecimal gstAmount = payment.getAmount().multiply(gstRate).divide(ONE_HUNDRED); 
		BigDecimal amountExcludingGst = payment.getAmount().subtract(gstAmount);
		
		String fileName = fileLocationBase + "payment-"+ now + "-"+ UUID.randomUUID().toString() + ".csv";
		System.out.println("gstAmount ===> " + gstAmount);
		System.out.println("amountExcludingGst ===> " + amountExcludingGst);
		try {
			fileIoService.savePayment(fileName, paymentMapper.map(payment, now, gstAmount, amountExcludingGst));
		} catch (IOException ioException) {
			throw PaymentException.builder().code(errorCode.getFileIoWriteErrorCode())
			.message(errorCode.getFileIoWriteErrorMsg()).timestamp(LocalDateTime.now().toString())
			.cause(ioException).build();
		}

	}

}
