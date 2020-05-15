package ru.datana.camel.demo.route;

import org.apache.camel.builder.RouteBuilder;

/**
 * Роутер перекладчик файлов
 */
public class FileRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer://FileRoute?fixedRate=true&delay=0&period=10000&repeatCount=2")
                .from("file:data/inbox?noop=true")
                .to("file:data/outbox");
    }
}
