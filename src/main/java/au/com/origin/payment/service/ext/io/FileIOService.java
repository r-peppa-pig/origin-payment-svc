package au.com.origin.payment.service.ext.io;

import java.io.IOException;

import au.com.origin.payment.service.ext.io.model.FileIOPayment;

public interface FileIOService {
	public void savePayment(final String fileName, final FileIOPayment payment) throws IOException;
}
