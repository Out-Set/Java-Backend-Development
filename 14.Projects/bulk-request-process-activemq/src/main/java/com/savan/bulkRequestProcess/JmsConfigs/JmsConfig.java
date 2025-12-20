package com.savan.bulkRequestProcess.JmsConfigs;

import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JmsConfig {

    @Value("${spring.activemq.broker-url}")
    private String BROKER_URL;

    @Value("${spring.activemq.user}")
    private String USERNAME;

    @Value("${spring.activemq.password}")
    private String PASSWORD;

    @Bean
    public ConnectionFactory connectionFactory() {
        // Create and configure the PooledConnectionFactory
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        
        // Set broker-URL, username and password
        connectionFactory.setBrokerURL(BROKER_URL);
        connectionFactory.setUserName(USERNAME);
        connectionFactory.setPassword(PASSWORD);
        connectionFactory.setMaxThreadPoolSize(5);
        
        return connectionFactory;
    }

    @Bean
    public Connection connection(ConnectionFactory connectionFactory) throws JMSException {
        // Create and return a single connection that can be reused
        return connectionFactory.createConnection();
    }
}
