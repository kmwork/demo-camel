package ru.datana.niokr.integrationadapterrest;

import org.apache.camel.*;
import org.apache.camel.impl.DefaultCamelContext;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@Configuration
public class IntegrationAdapterRestApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(IntegrationAdapterRestApplication.class, args);

        CamelContext camelContext = new DefaultCamelContext();
        camelContext.start();
        ProducerTemplate template = camelContext.createProducerTemplate();
        Map<String, Object> requestHeaders = new HashMap<>();
        requestHeaders.put(Exchange.HTTP_METHOD, "GET");
        String requestEndpointUri = "http://httpbin.org/get";
        String reqestBody = "";
        String body = template.requestBodyAndHeaders(requestEndpointUri, reqestBody, requestHeaders, String.class);
        System.out.println(body);
    }
}

