package ru.datana.camel.demo.route;

import org.apache.camel.builder.RouteBuilder;

public class MqToKafkaRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer://MqToKafkaRoute?fixedRate=true&delay=0&period=10000")
                .from("activemq:demo-datana")
                .log("[MqToKafkaRoute: READ] body = ${body}")
                .to("kafka:demo-datana-kafka");
    }
}