package ru.datana.camel.demo.bean;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Handler;
import org.apache.camel.Header;
import ru.datana.camel.demo.service.S7Api;

import java.io.IOException;
@Slf4j
public class S7Bean {

    @Handler
    public byte[] getData(@Header("request-id") long id) throws IOException {
        log.debug("[S7Bean] id = " + id);
        return new S7Api().getDataFromS7();
    }

}