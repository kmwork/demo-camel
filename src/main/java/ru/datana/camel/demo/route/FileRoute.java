package ru.datana.camel.demo.route;

import org.apache.camel.builder.RouteBuilder;

public class FileRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer://bar?fixedRate=true&delay=0&period=10000")
                .from("file:data/inbox?noop=true")
                .to("file:data/outbox");
    }
}
