package ru.datana.camel.demo.service;

import com.github.s7connector.api.DaveArea;
import com.github.s7connector.api.S7Connector;
import com.github.s7connector.api.factory.S7ConnectorFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
public class S7Api {

    private static String HOST = "172.30.143.30";

    public byte[] getDataFromS7() throws IOException {
        log.info("[S7Api] Подключение к Сименсу : " + HOST);
        S7Connector connector =
                S7ConnectorFactory
                        .buildTCPConnector()
                        .withHost(HOST)
                        .withRack(0) //optional
                        .withSlot(0) //optional
                        .build();


        byte[] result = connector.read(DaveArea.DB, 3, 2, 0);
        log.info("[S7Api] Получены данные: " + Arrays.toString(result));
        connector.close();

        return result;
    }
}
