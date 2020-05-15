package ru.datana.camel.demo.route;

import org.apache.camel.builder.RouteBuilder;
import ru.datana.camel.demo.bean.RestBean;
import ru.datana.camel.demo.bean.RestPostTransformerBean;

/**
 * Роутер для REST -> JMS
 */
public class RestToMQRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("timer://RestToMQRoute?fixedRate=true&delay=0&period=10000")
                .setHeader("request-id", () -> System.nanoTime())
                .bean(RestBean.class)
                .bean(RestPostTransformerBean.class)
                .log("[RestToMQRoute: Send-ActiveMQ]: ${body}")
                .to("activemq:demo-datana");

    }

}