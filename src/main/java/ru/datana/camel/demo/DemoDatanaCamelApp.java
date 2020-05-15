package ru.datana.camel.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.SimpleRegistry;
import ru.datana.camel.demo.bean.RestBean;
import ru.datana.camel.demo.bean.S7Bean;
import ru.datana.camel.demo.bean.S7PostProcessorBean;
import ru.datana.camel.demo.route.FileRoute;
import ru.datana.camel.demo.route.MqToKafkaRoute;
import ru.datana.camel.demo.route.RestToMQRoute;
import ru.datana.camel.demo.route.S7SocketForCamelRoute;

@Slf4j
public class DemoDatanaCamelApp {
    private static final long SLEEP_MS = 30 * 1000;

    public static void main(String[] args) throws Exception {
        SimpleRegistry registry = new SimpleRegistry();
        registry.bind("s7Bean", S7Bean.class);
        registry.bind("s7PostProcessorBean", S7PostProcessorBean.class);
        registry.bind("restBean", RestBean.class);

        doS7(registry);
        doRest(registry);
        doMQ(registry);
        doFile(registry);

    }

    private static void doSleep() {
        log.info("[SLEEP] start on " + SLEEP_MS + " ms");
        try {
            Thread.sleep(SLEEP_MS);
        } catch (InterruptedException e) {
            //not do
        }
        log.info("[SLEEP] done");
    }

    private static void doFile(SimpleRegistry registry) throws Exception {
        CamelContext restCamelContext = new DefaultCamelContext(registry);
        restCamelContext.addRoutes(new FileRoute());

        restCamelContext.start();

        doSleep();

        restCamelContext.stop();
    }

    private static void doRest(SimpleRegistry registry) throws Exception {
        try {
            log.error("[Step:Restfull-WebServices] start");
            CamelContext restCamelContext = new DefaultCamelContext(registry);
            restCamelContext.addRoutes(new RestToMQRoute());

            restCamelContext.start();

            doSleep();

            restCamelContext.stop();
        } catch (Exception e) {
            log.error("[Step:Restfull-WebService] error", e);
        } finally {
            log.error("[Step:Restfull-WebService] done");
        }
    }

    private static void doMQ(SimpleRegistry registry) {
        try {
            log.error("[Step:ActiveMQ] start");
            CamelContext mqCamelContext = new DefaultCamelContext(registry);
            mqCamelContext.addRoutes(new MqToKafkaRoute());

            mqCamelContext.start();

            doSleep();

            mqCamelContext.stop();
        } catch (Exception e) {
            log.error("[Step:ActiveMQ] error", e);
        } finally {
            log.error("[Step:ActiveMQ] done");
        }
    }

    private static void doS7(SimpleRegistry registry) {
        try {
            log.error("[Step:S7] start");
            CamelContext s7CamelContext = new DefaultCamelContext(registry);
            s7CamelContext.addRoutes(new S7SocketForCamelRoute());

            s7CamelContext.start();

            ProducerTemplate producerTemplate = s7CamelContext.createProducerTemplate();
            producerTemplate.sendBody("direct:start", "start");

            s7CamelContext.stop();

        } catch (Exception e) {
            log.error("[Step:S7] error", e);
        } finally {
            log.error("[Step:S7] done");
        }
    }
}