package ru.datana.camel.demo.bean;

import org.apache.camel.Handler;
import org.apache.camel.Header;
import ru.datana.camel.demo.service.RestClient;

public class RestBean {

    @Handler
    public String sensRequest(@Header("request-id") long id) throws Exception {
        return new RestClient().getRestResponse(id);
    }

}