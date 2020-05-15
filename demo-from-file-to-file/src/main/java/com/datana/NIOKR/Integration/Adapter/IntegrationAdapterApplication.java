package com.datana.NIOKR.Integration.Adapter;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IntegrationAdapterApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntegrationAdapterApplication.class, args);
		CamelContext camelContext = new DefaultCamelContext();
		try {
			camelContext.addRoutes(new RouteBuilder() {
				public void configure() {
					from("file:data/inbox?noop=true")
							.to("file:data/outbox");
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		camelContext.start();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		camelContext.stop();
	}

}
