package au.com.origin.payment.error;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

/**
 * Contains error codes used by application.
 * @author peppapig
 *
 */
@ConfigurationProperties
@Configuration
@PropertySource("classpath:error.properties")
@Data
public class ErrorCode {

    @Value("${origin.payment.error.code.file.save}")
    private String errorCodeFileSave;

    @Value("${origin.payment.error.msg.file.save}")
    private String errorMsgFileSave;

    @Value("${origin.payment.bad.request.code}")
    private String badRequestCode;

    @Value("${origin.payment.bad.request.msg}")
    private String badRequestMsg;
    
    @Value("${origin.payment.generic.error.code}")
    private String genericErrorCode;

    @Value("${origin.payment.generic.error.msg}")
    private String genericErrorMsg;

    @Value("${origin.payment.file.io.write.error.code}")
    private String fileIoWriteErrorCode;

    @Value("${origin.payment.file.io.write.error.msg}")
    private String fileIoWriteErrorMsg;
    
}
