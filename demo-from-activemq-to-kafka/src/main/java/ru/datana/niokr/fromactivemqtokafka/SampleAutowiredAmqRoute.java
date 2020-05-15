package ru.datana.niokr.fromactivemqtokafka;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class SampleAutowiredAmqRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
//***Get Message from ActiveMQ send Message to Kafka***
        from("activemq:demo-datana")
                .log("[READ] body = ${body}")
                .to("kafka:demo-datana-kafka");
    }
}