package au.com.origin.payment.rest.interceptor;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import au.com.origin.payment.error.PaymentAccessException;
import lombok.Setter;

/**
 * Provides cross cutting functionality for handling incoming requests.
 * @author peppapig
 *
 */
@Component
@Setter
public class PaymentInterceptor implements HandlerInterceptor {
	
    private Clock clock;

    public PaymentInterceptor() {
    	clock = Clock.systemDefaultZone();
    }
    
    public PaymentInterceptor(Clock clock) {
    	this.clock = clock;
    }
	@Value("#{ T(java.time.LocalTime).parse('${payment.start.time}')}")
	private LocalTime paymentStartTime;
	
	@Value("#{ T(java.time.LocalTime).parse('${payment.end.time}')}")
	private LocalTime paymentEndTime;
	
	@Value("#{ T(java.time.LocalTime).parse('${friday.payment.start.time}')}")
	private LocalTime fridayPaymentStartTime;

	@Value("#{ T(java.time.LocalTime).parse('${friday.payment.end.time}')}")
	private LocalTime fridayPaymentEndTime;

	@Value("${origin.payment.access.error.code}")
	private String accessErrorCode;

	@Value("${origin.payment.access.error.msg}")
	private String accessErrorMsg;

	/**
	 * Determines if the request is allowed based on configured time. 
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		LocalDateTime now = LocalDateTime.now(clock);
		LocalTime nowTime = now.toLocalTime();

		switch(now.getDayOfWeek()) {
		case MONDAY:
		case TUESDAY:
		case WEDNESDAY:
		case THURSDAY:
			if(nowTime.isAfter(paymentStartTime) && nowTime.isBefore(paymentEndTime)) {
				return true;
			}
			break;
		case FRIDAY:
			if(nowTime.isAfter(fridayPaymentStartTime) && nowTime.isBefore(fridayPaymentEndTime)) {
				return true;
			}
			break;
		case SATURDAY:
		case SUNDAY:
		default:
			break;				
		}
		throw new PaymentAccessException(accessErrorCode, accessErrorMsg);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable Exception ex) throws Exception {
	}
}
