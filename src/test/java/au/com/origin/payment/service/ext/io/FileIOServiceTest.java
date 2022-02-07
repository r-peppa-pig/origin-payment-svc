package au.com.origin.payment.service.ext.io;

import org.apache.commons.csv.CSVPrinter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class FileIOServiceTest {
	
	@InjectMocks
	FileIOServiceImpl fileIOService;

    private CSVPrinter csvPrinter;

    @Before
    public void setup() {
    	csvPrinter = mock(CSVPrinter.class);
    }

    //@Test
	public void savePayment() throws IOException {
    	fileIOService.savePayment(null, null);
    	
    	/*
		try (CSVPrinter csvPrinter = new CSVPrinter(Files.newBufferedWriter(Paths.get(fileName)), DEFAULT.withHeader(HEADERS));) {
			csvPrinter.printRecord(payment.getPaymentDateTime(), payment.getReferenceNumber(), payment.getBsb(),
					payment.getAccountNumber(), payment.getTotalAmountIncludingGst(), payment.getGstAmount(),
					payment.getAmountExcludingGst());
			csvPrinter.flush();
		}
		*/
	}

}
