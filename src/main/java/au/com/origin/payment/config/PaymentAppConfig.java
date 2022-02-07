package au.com.origin.payment.config;

import java.time.Clock;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import au.com.origin.payment.mapper.PaymentMapper;
import au.com.origin.payment.rest.interceptor.PaymentInterceptor;
import static au.com.origin.payment.constant.PaymentConstant.DATE_TIME_FORMAT;

/**
 * Configuration for the application. 
 * @author peppapig
 *
 */
@Configuration
public class PaymentAppConfig  implements WebMvcConfigurer {

	@Autowired
	private PaymentInterceptor paymentInterceptor;

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:ValidationMessages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	public LocalValidatorFactoryBean validator() {
		LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
		validatorFactoryBean.setValidationMessageSource(messageSource());
		return validatorFactoryBean;
	}

	@Bean(name = "paymentMapper")
	public PaymentMapper paymentMapper() {
		return new PaymentMapper();
	}

	@Bean(name = "dateTimeFormatter") 
	public DateTimeFormatter dateTimeFormatter() {
		return DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
	}

	@Bean
	public Clock clock() {
	    return Clock.systemDefaultZone();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry){
		registry.addInterceptor(paymentInterceptor).addPathPatterns("/**");
	}
}
