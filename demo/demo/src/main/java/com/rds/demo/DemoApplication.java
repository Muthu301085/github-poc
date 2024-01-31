package com.rds.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	public Object callRDSApi(Order order) {

		try {
			ResponseEntity<String> iFastOrderResponse = restTemplate.exchange(graphQLProperties.getUrl(), HttpMethod.POST, httpEntityOf(buildNewIfastOrder(order, IFastConstants.REQUEST_TYPE_ORDER)), String.class);
			log.info("IFast Order Response  :: " + iFastOrderResponse);
			ensureThatRequiredBodyIsPresent(iFastOrderResponse);
			DataResponse iFastGnOrderResponse = objectMapper.readValue(iFastOrderResponse.getBody(), new TypeReference<DataResponse>() {
			});
			ensureThatRequiredObjectForOrderIsNotNull(iFastGnOrderResponse);

			//TODO Remove logger once Graphql logging added to Kibana
			log.info("IFast Response  :: " + iFastGnOrderResponse.getData().getOrder());

			return iFastGnOrderResponse;
		} catch (Exception e) {
			log.error("Exception occured while calling IFast : " + e.getMessage());
		}
	}
}
