package ru.datana.camel.demo.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MqToKafkaRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("activemq:demo-datana")
                .log("[MqToKafkaRoute: READ] body = ${body}")
                .to("kafka:demo-datana-kafka");
    }
}