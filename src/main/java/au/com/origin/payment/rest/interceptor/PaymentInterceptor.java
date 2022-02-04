package au.com.origin.payment.rest.interceptor;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import au.com.origin.payment.error.PaymentAccessException;
import lombok.Setter;

@Component
@Setter
public class PaymentInterceptor implements HandlerInterceptor {

	@Value("${payment.start.time}")
	private int paymentStartTime;

	@Value("${payment.end.time}")
	private int paymentEndTime;

	@Value("${friday.payment.start.time}")
	private int fridayPaymentStartTime;

	@Value("${friday.payment.end.time}")
	private int fridayPaymentEndTime;

	@Value("${origin.payment.access.error.code}")
	private String accessErrorCode;

	@Value("${origin.payment.access.error.msg}")
	private String accessErrorMsg;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//Calendar cal = Calendar.getInstance();
		//int hour = cal.get(cal.HOUR_OF_DAY);
		LocalDateTime now = LocalDateTime.now();
		DayOfWeek day = now.getDayOfWeek();
		int hour = now.getHour();

		System.out.println("Hour now is ===> " + hour);
		System.out.println("MINIMAL: INTERCEPTOR PREHANDLE CALLED");

		switch(day) {
		case MONDAY:
		case TUESDAY:
		case WEDNESDAY:
		case THURSDAY:
			if (hour >= paymentStartTime && hour <= paymentEndTime) {
				return true;
			} 
			break;
		case FRIDAY:
			if (hour >= fridayPaymentStartTime && hour <= fridayPaymentEndTime) {
				return true;
			} 
			break;
		default:
			break;				
		}
		throw PaymentAccessException.builder()
		.code(accessErrorCode)
		.message(accessErrorMsg).build();
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable ModelAndView modelAndView) throws Exception {
		System.out.println("MINIMAL: INTERCEPTOR POSTHANDLE CALLED");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable Exception ex) throws Exception {
		System.out.println("MINIMAL: INTERCEPTOR AFTERCOMPLETION CALLED");
	}
}
