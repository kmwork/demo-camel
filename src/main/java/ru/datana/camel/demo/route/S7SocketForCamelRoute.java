package ru.datana.camel.demo.route;

import org.apache.camel.builder.RouteBuilder;
import ru.datana.camel.demo.bean.S7Bean;

public class S7SocketForCamelRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("timer:kostya?period=5000")
                .bean(S7Bean.class);

        from("direct:start")
                .to("bean:s7Bean")
                .log("response = ${body}");
    }


}
