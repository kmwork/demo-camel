package ru.datana.camel.demo.service;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;

import java.util.HashMap;
import java.util.Map;

public class RestClient {
    public String getRestResponse(long id) throws Exception {

        /*Get RequestBody from Rest Controller */

        String requestEndpointUri = "http://172.29.40.42:8080/rest/getData";
        Map<String, Object> requestHeaders = new HashMap<>();
        requestHeaders.put(Exchange.HTTP_METHOD, "POST");
        requestHeaders.put(Exchange.CONTENT_TYPE, "application/json");
        String requestBody = "{ \"request_id\": \"" + id + "\" }";
        CamelContext camelContext = new DefaultCamelContext();
        camelContext.start();
        ProducerTemplate template = camelContext.createProducerTemplate();
        String res = template.requestBodyAndHeaders(requestEndpointUri, requestBody, requestHeaders, String.class);
        return res;


    }

}