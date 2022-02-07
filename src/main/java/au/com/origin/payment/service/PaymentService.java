package au.com.origin.payment.service;

import static au.com.origin.payment.constant.PaymentConstant.ONE_HUNDRED;
import static au.com.origin.payment.constant.PaymentConstant.PAYMENT;
import static au.com.origin.payment.constant.PaymentConstant.HYPHEN;
import static au.com.origin.payment.constant.PaymentConstant.PERIOD;
import static au.com.origin.payment.constant.PaymentConstant.CSV;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import au.com.origin.payment.error.ErrorCode;
import au.com.origin.payment.error.PaymentException;
import au.com.origin.payment.mapper.PaymentMapper;
import au.com.origin.payment.model.Payment;
import au.com.origin.payment.service.ext.io.FileIOService;

/**
 * Saves data to file.
 * @author peppapig
 *
 */
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
		String fileName = 		new StringBuilder()
				.append(fileLocationBase).append(PAYMENT).append(HYPHEN).append(now).append(HYPHEN).append(UUID.randomUUID().toString())
				.append(PERIOD).append(CSV).toString();
		try {
			var fileIOPayment = paymentMapper.map(payment, now, gstAmount, amountExcludingGst);
			fileIoService.savePayment(fileName, fileIOPayment);
		} catch (IOException ioException) {
			throw new PaymentException(errorCode.getFileIoWriteErrorCode(), errorCode.getFileIoWriteErrorMsg(), ioException);
		}
	}
}
