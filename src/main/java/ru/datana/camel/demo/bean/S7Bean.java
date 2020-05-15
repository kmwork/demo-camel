package ru.datana.camel.demo.bean;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Body;
import org.apache.camel.Handler;
import ru.datana.camel.demo.service.S7Api;

import java.io.IOException;
@Slf4j
public class S7Bean {

    @Handler
    public byte[] getData(@Body String id) throws IOException {
        return new S7Api().getDataFromS7();
    }

}