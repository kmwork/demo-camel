package ru.datana.camel.demo.route;

import org.apache.camel.builder.RouteBuilder;

public class FileRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("file:data/inbox?noop=true")
                .to("file:data/outbox");
    }
}
