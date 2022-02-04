package au.com.origin.payment.constant;

import java.math.BigDecimal;

public class PaymentConstant {
	
	
	public static final int REFERENCE_NUMBER_SIZE = 5;
	public static final int BSB_MIN_VALUE = 100000;
	public static final int BSB_MAX_VALUE = 999999;
	public static final int ACCOUNT_NUMBER_MIN_VALUE = 100000;
	public static final int ACCOUNT_NUMBER_MAX_VALUE = 999999;
	public static final String AMOUNT_MIN_VALUE = "0.01";
	public static final String AMOUNT_MAX_VALUE = "2000.00";
	
	
	
	public static final String PAYMENT_DATE_TIME = "Payment date/time";
	public static final String REFERENCE_NUMBER = "Reference number";
	public static final String BSB = "BSB";
	public static final String ACCOUNT_NUMBER = "Account number";
	public static final String TOTAL_AMOUNT_INCLUDING_GST = "Total amount including GST";
	public static final String GST_AMOUNT = "GST amount";
	public static final String AMOUNT_EXCLUDING_GST = "Amount excluding GST";
	
	public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
	
	public static final String DATE_TIME_FORMAT = "yyyyMMddHHmmss";

}
