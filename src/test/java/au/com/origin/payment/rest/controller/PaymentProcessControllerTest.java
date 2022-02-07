package au.com.origin.payment.rest.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import au.com.origin.payment.rest.model.PaymentRequest;
import au.com.origin.payment.service.PaymentService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PaymentProcessController.class)
@WebAppConfiguration
@EnableWebMvc
public class PaymentProcessControllerTest {

	protected MockMvc mvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	private PaymentService service;

	private ObjectWriter objectWriter; 
	@Before
	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		objectWriter = mapper.writer().withDefaultPrettyPrinter();

	}

	@Test
	public void test_payment() throws Exception {

		PaymentRequest request = PaymentRequest
				.builder()
				.referenceNumber("12345")
				.bsb(123456)
				.accountNumber(123456)
				.amount(new BigDecimal("100.00"))
				.build();
		String requestJson = objectWriter.writeValueAsString(request);

		mvc.perform(post("/payment")
				.accept(APPLICATION_JSON_VALUE)
				.contentType(APPLICATION_JSON_VALUE)
				.characterEncoding(Charset.defaultCharset())
				.content(requestJson))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isCreated());
	}
}
