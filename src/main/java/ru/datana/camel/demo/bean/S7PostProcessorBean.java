package ru.datana.camel.demo.bean;

import org.apache.camel.Body;
import org.apache.camel.Handler;

import java.io.IOException;
import java.util.Arrays;

public class S7PostProcessorBean {

    @Handler
    public String transform(@Body byte[] data) throws IOException {
        return "[S7:Transform]" + Arrays.toString(data);

    }

}