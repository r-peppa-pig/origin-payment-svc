package au.com.origin.payment.rest.interceptor;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import static org.springframework.test.util.ReflectionTestUtils.*;

import au.com.origin.payment.error.PaymentAccessException;

@RunWith(MockitoJUnitRunner.class)
public class PaymentInterceptorTest {

	@InjectMocks
	PaymentInterceptor paymentInterceptor;
	
	@Mock
	private Clock clock;

	private Clock fixedClock;

	@Before
	public void setup() {
		setField(paymentInterceptor, "paymentStartTime", LocalTime.parse("09:00"));
		setField(paymentInterceptor, "paymentEndTime", LocalTime.parse("17:00"));
		setField(paymentInterceptor, "fridayPaymentStartTime", LocalTime.parse("08:00"));
		setField(paymentInterceptor, "fridayPaymentEndTime", LocalTime.parse("18:00"));
		setField(paymentInterceptor, "accessErrorCode", "104");
	}

	@Test
	public void preHandle_weekday_success() throws Exception {
	    fixedClock = Clock.fixed(Instant.parse("2022-01-05T14:15:30.00Z"), ZoneId.of("UTC"));
	    doReturn(fixedClock.instant()).when(clock).instant();
	    doReturn(fixedClock.getZone()).when(clock).getZone();
		boolean isAllowed = paymentInterceptor.preHandle(null, null, null);
		assertTrue(isAllowed);
	}

	@Test(expected = PaymentAccessException.class)
	public void preHandle_weekday_error() throws Exception {
	    fixedClock = Clock.fixed(Instant.parse("2022-01-05T17:15:30.00Z"), ZoneId.of("UTC"));
	    doReturn(fixedClock.instant()).when(clock).instant();
	    doReturn(fixedClock.getZone()).when(clock).getZone();
		boolean isAllowed = paymentInterceptor.preHandle(null, null, null);
		assertTrue(isAllowed);
	}

	@Test
	public void preHandle_friday_success() throws Exception {
	    fixedClock = Clock.fixed(Instant.parse("2022-01-07T14:15:30.00Z"), ZoneId.of("UTC"));
	    doReturn(fixedClock.instant()).when(clock).instant();
	    doReturn(fixedClock.getZone()).when(clock).getZone();
		boolean isAllowed = paymentInterceptor.preHandle(null, null, null);
		assertTrue(isAllowed);
	}

	@Test(expected = PaymentAccessException.class)
	public void preHandle_friday_failure() throws Exception {
	    fixedClock = Clock.fixed(Instant.parse("2022-01-07T19:15:30.00Z"), ZoneId.of("UTC"));
	    doReturn(fixedClock.instant()).when(clock).instant();
	    doReturn(fixedClock.getZone()).when(clock).getZone();
		boolean isAllowed = paymentInterceptor.preHandle(null, null, null);
		assertTrue(isAllowed);
	}

	@Test(expected = PaymentAccessException.class)
	public void preHandle_weekend_failure() throws Exception {
	    fixedClock = Clock.fixed(Instant.parse("2022-01-08T12:15:30.00Z"), ZoneId.of("UTC"));
	    doReturn(fixedClock.instant()).when(clock).instant();
	    doReturn(fixedClock.getZone()).when(clock).getZone();
		boolean isAllowed = paymentInterceptor.preHandle(null, null, null);
		assertTrue(isAllowed);
	}
}
