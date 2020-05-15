package ru.datana.camel.demo.route;

import org.apache.camel.builder.RouteBuilder;
import ru.datana.camel.demo.bean.S7Bean;
import ru.datana.camel.demo.bean.S7PostProcessorBean;

/**
 * Роутер для Сименса --> JMS
 */
public class S7SocketForCamelRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("timer://S7SocketForCamelRoute?fixedRate=true&delay=0&period=10000&repeatCount=2")
                .setHeader("request-id", () -> System.nanoTime())
                .bean(S7Bean.class)
                .bean(S7PostProcessorBean.class)
                .log("[S7SocketForCamelRoute: Send-ActiveMQ]: ${body}")
                .to("activemq:demo-datana");
    }


}
