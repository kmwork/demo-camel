package ru.datana.camel.demo.route;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.boot.SpringApplication;

@Component
public class FileRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("file:data/inbox?noop=true")
				.to("file:data/outbox");
	}
}
