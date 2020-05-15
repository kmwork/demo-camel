package ru.datana.camel.demo.bean;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Handler;
import org.apache.camel.Header;
import ru.datana.camel.demo.service.RestClient;

@Slf4j
public class RestBean {

    @Handler
    public String sensRequest(@Header("request-id") long id) throws Exception {
        log.debug("[RestBean] id = " + id);
        return new RestClient().getRestResponse(id);
    }

}