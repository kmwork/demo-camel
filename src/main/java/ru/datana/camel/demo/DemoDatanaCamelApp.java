package ru.datana.camel.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.datana.camel.demo.config.DatanaCamelConfig;
import ru.datana.camel.demo.route.FileRoute;
import ru.datana.camel.demo.route.MqToKafkaRoute;
import ru.datana.camel.demo.route.RestToMQRoute;
import ru.datana.camel.demo.route.S7SocketForCamelRoute;

@Slf4j
@SpringBootApplication
@Import(DatanaCamelConfig.class)
public class DemoDatanaCamelApp implements CommandLineRunner {
    private static final long SLEEP_MS = 30 * 1000;


    @Autowired
    private CamelContext camelContext;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(DemoDatanaCamelApp.class, args);

    }

    private void doSleep() {
        log.info("[SLEEP] start on " + SLEEP_MS + " ms");
        try {
            Thread.sleep(SLEEP_MS);
        } catch (InterruptedException e) {
            //not do
        }
        log.info("[SLEEP] done");
    }


    private void doService(String prefixLog, RouteBuilder routeBuilder) {
        try {
            log.info(prefixLog + " start");
            camelContext.addRoutes(routeBuilder);
            doSleep();
        } catch (Exception e) {
            log.error(prefixLog + " error", e);
        } finally {
            log.info(prefixLog + " done");
        }
    }

    @Override
    public void run(String... args) throws Exception {

        //не работает Siemens c удалённой JMS -- админы разнесли в разные VPN
        //пример как пытается и не падает сервис
        //работа с Контроллерами Сименса
        doService("[Step:S7]", new S7SocketForCamelRoute());


        //чтение RESTfull Сервиса и публикацию в очередь ActiveMQ
        doService("[Step:Rest]", new RestToMQRoute());

        //работа с ActiveMQ (JMS Брокер) - чтение из ActiveMQ и публикация в Кафку
        doService("[Step:ActiveMQ]", new MqToKafkaRoute());

        // работа с файлами
        doService("[Step:File]", new FileRoute());

        //грубый выход из приложения
        System.exit(0);
    }
}