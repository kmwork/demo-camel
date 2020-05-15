package ru.datana.camel.demo.bean;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Body;
import org.apache.camel.Handler;
import org.apache.camel.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Component
public class S7PostProcessorBean {

    @Handler
    public String transform(@Header("request-id") String id, @Body byte[] data) throws IOException {
        log.debug("[S7PostProcessorBean] id = " + id);
        return "[S7:Transform] id = " + id + ", data = " + Arrays.toString(data);

    }

}