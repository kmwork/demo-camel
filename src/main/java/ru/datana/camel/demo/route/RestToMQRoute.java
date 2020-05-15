package ru.datana.camel.demo.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import ru.datana.camel.demo.bean.RestBean;
import ru.datana.camel.demo.bean.S7PostProcessorBean;

@Component
public class RestToMQRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("timer://bar?fixedRate=true&delay=0&period=10000")
                .setHeader("request-id", () -> System.nanoTime())
                .bean(RestBean.class)
                .bean(S7PostProcessorBean.class)
                .log("[Send-ActiveMQ]: ${body}")
                .to("activemq:demo-datana");

    }

}