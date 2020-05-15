package ru.datana.camel.demo.bean;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Handler;
import org.apache.camel.Header;
import org.springframework.stereotype.Component;
import ru.datana.camel.demo.service.RestClient;

/**
 * Бин: обращение к сервису RESTfull Веб-сервису
 */
@Slf4j
@Component
public class RestBean {

    @Handler
    public String sensRequest(@Header("request-id") long id) throws Exception {
        log.debug("[RestBean] id = " + id);
        return new RestClient().getRestResponse(id);
    }

}