package ru.datana.camel.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.camel.component.activemq.ActiveMQComponent;
import org.apache.camel.component.jms.JmsConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

@Slf4j
public class DatanaCamelConfig {

    @Value("${datana.jms.brokerUrl}")
    private String brokerUrl;

    @PostConstruct
    protected void init() {
        log.debug("[JMS] init");
    }

    @Bean
    protected ActiveMQComponent activemq() {
        log.debug("[JMS] create datanaJms");
        ActiveMQComponent result = new ActiveMQComponent();
        result.setConfiguration(camelActiveMQConfig());
        return result;
    }

    private JmsConfiguration camelActiveMQConfig() {
        JmsConfiguration configuration = new JmsConfiguration();
        configuration.setConnectionFactory(pooledConnectionFactory());
        configuration.setTransacted(true);
        configuration.setConcurrentConsumers(15);
        configuration.setDeliveryPersistent(true);
        configuration.setRequestTimeout(10000);
        configuration.setCacheLevelName("CACHE_CONSUMER");
        return configuration;
    }

    private PooledConnectionFactory pooledConnectionFactory() {
        PooledConnectionFactory factory = new PooledConnectionFactory();
        factory.setMaxConnections(10);
        factory.setMaximumActiveSessionPerConnection(10);
        factory.setConnectionFactory(singleFactory());
        return factory;
    }

    private ActiveMQConnectionFactory singleFactory() {

        log.debug("[JMS] connect to url = " + brokerUrl);
        ActiveMQConnectionFactory f = new ActiveMQConnectionFactory();
        f.setBrokerURL(brokerUrl);
        return f;
    }
}
