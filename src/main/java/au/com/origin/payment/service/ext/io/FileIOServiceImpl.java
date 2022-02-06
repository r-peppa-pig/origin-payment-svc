package au.com.origin.payment.service.ext.io;

import static au.com.origin.payment.constant.PaymentConstant.ACCOUNT_NUMBER;
import static au.com.origin.payment.constant.PaymentConstant.AMOUNT_EXCLUDING_GST;
import static au.com.origin.payment.constant.PaymentConstant.BSB;
import static au.com.origin.payment.constant.PaymentConstant.GST_AMOUNT;
import static au.com.origin.payment.constant.PaymentConstant.PAYMENT_DATE_TIME;
import static au.com.origin.payment.constant.PaymentConstant.REFERENCE_NUMBER;
import static au.com.origin.payment.constant.PaymentConstant.TOTAL_AMOUNT_INCLUDING_GST;
import static org.apache.commons.csv.CSVFormat.DEFAULT;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import au.com.origin.payment.service.ext.io.model.FileIOPayment;

@Service
public class FileIOServiceImpl implements FileIOService {

	private static final String[] HEADERS = { PAYMENT_DATE_TIME, REFERENCE_NUMBER, BSB, ACCOUNT_NUMBER,
			TOTAL_AMOUNT_INCLUDING_GST, GST_AMOUNT, AMOUNT_EXCLUDING_GST };

	@Override
	public void savePayment(final String fileName, final FileIOPayment payment) throws IOException {
		try (CSVPrinter csvPrinter = new CSVPrinter(Files.newBufferedWriter(Paths.get(fileName)), DEFAULT.withHeader(HEADERS));) {
			csvPrinter.printRecord(payment.getPaymentDateTime(), payment.getReferenceNumber(), payment.getBsb(),
					payment.getAccountNumber(), payment.getTotalAmountIncludingGst(), payment.getGstAmount(),
					payment.getAmountExcludingGst());
			csvPrinter.flush();
		}
	}

}
