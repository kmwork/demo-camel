package ru.datana.camel.demo.bean;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Body;
import org.apache.camel.Handler;
import org.apache.camel.Header;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RestPostTransformerBean {

    @Handler
    public String sensRequest(@Header("request-id") long id, @Body String message) throws Exception {
        log.debug("[RestPostTransformerBean] id = " + id);
        return "*** " + message + "***";
    }

}