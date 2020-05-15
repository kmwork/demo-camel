package ru.datana.camel.demo.route;

import org.apache.camel.builder.RouteBuilder;

public class S7SocketForCamelRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

//        from("timer:kostya?period=5000")
//                .to("bean:s7Bean")
//                .log("response = ${body}");

        from("direct:start")
                .to("bean:s7Bean")
                .log("response = ${body}");
    }


}
