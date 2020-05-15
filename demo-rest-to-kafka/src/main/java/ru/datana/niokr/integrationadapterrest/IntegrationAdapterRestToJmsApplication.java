package ru.datana.niokr.integrationadapterrest;

import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import java.io.File;

import org.apache.commons.io.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@Configuration
public class IntegrationAdapterRestToJmsApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(IntegrationAdapterRestToJmsApplication.class, args);

        String requestEndpointUri = "http://172.29.40.42:8080/rest/getData";
        Map<String, Object> requestHeaders = new HashMap<>();
        requestHeaders.put(Exchange.HTTP_METHOD, "POST");
        requestHeaders.put(Exchange.CONTENT_TYPE, "application/json");
        String reqestBody = readFile();
        CamelContext camelContext = new DefaultCamelContext();
        camelContext.start();
        ProducerTemplate template = camelContext.createProducerTemplate();
        //Get Json from REST and Send Json to JMS kafka
        String responseBodyJsonFromRest = template.requestBodyAndHeaders(requestEndpointUri, reqestBody, requestHeaders, String.class);

        camelContext.addRoutes(new RouteBuilder() {
            public void configure() throws Exception {
                from("timer://bar?fixedRate=true&delay=0&period=10000")
                        .setBody(constant(responseBodyJsonFromRest))
                        .log("SendToKafka")
                        .to("kafka:dima?brokers=172.29.40.42:9092");
            }

        });

    }

    public static String readFile() throws IOException {
        File file = new File("src\\main\\resources\\request-example.json");
        return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
    }
}



