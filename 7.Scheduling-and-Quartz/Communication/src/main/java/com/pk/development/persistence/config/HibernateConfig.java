package com.pk.development.persistence.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfig {

    protected final Log logger = LogFactory.getLog(getClass());
    
    @Bean
    public PhysicalNamingStrategy physical() {
        return new HibernateNamingStrategy();
    }

    @Bean
    public ImplicitNamingStrategy implicit() {
        return new HibernateImplicitNamingStratergy();
    }
}

