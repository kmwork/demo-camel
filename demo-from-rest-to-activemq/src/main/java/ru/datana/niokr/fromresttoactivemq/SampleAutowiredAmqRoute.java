package ru.datana.niokr.fromresttoactivemq;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public class SampleAutowiredAmqRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        /*Get RequestBody from Rest Controller */

        String requestEndpointUri = "http://172.29.40.42:8080/rest/getData";
        Map<String, Object> requestHeaders = new HashMap<>();
        requestHeaders.put(Exchange.HTTP_METHOD, "POST");
        requestHeaders.put(Exchange.CONTENT_TYPE, "application/json");
        String requestBody = readFile();
        CamelContext camelContext = new DefaultCamelContext();
        camelContext.start();
        ProducerTemplate template = camelContext.createProducerTemplate();
        //Get Json from REST and Send Json to JMS
        String responseBodyJsonFromRest = template.requestBodyAndHeaders(requestEndpointUri, requestBody, requestHeaders, String.class);
        /*Send Message to ActiveMQ */

        from("timer://bar?fixedRate=true&delay=0&period=10000")
                        .setBody(constant(responseBodyJsonFromRest))
                        .log("[Send-ActiveMQ]: ${body}")
                        .to("activemq:demo-datana");

    }


        public  String readFile() throws IOException {
        File file = new File("/home/lin/work-lanit/demo-datana-camel/demo-from-rest-to-activemq/src/main/resources/request-example.json");
        return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
    }
}