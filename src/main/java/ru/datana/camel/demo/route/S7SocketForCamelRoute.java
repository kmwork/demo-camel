package ru.datana.camel.demo.route;

import org.apache.camel.builder.RouteBuilder;
import ru.datana.camel.demo.bean.S7Bean;
import ru.datana.camel.demo.bean.S7PostProcessorBean;

public class S7SocketForCamelRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

//        from("timer:kostya?period=5000")
//                .to("bean:s7Bean")
//                .log("response = ${body}");

        from("direct:start")
                .setHeader("request-id", () -> System.nanoTime())
                .bean(S7Bean.class)
                .bean(S7PostProcessorBean.class)
                .log("[Send-ActiveMQ]: ${body}")
                .to("activemq:demo-datana");
    }


}
