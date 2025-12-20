package com.pk.development.persistence.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories( basePackages = "com.pk.collectflow",
	entityManagerFactoryRef = "collectflowEntityManagerFactory", transactionManagerRef = "collectflowTransactionManager")
public class PersistenceConfigurationCollectFlow implements PersistenceConfigDescriptor {
	
	@Override
	public String getDatabaseName() {
		return "CollectFlow";
	} 
	
	@Bean
    @ConfigurationProperties("spring.collectflow.datasource")
    public DataSourceProperties collectflowDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    //@ConfigurationProperties("app.datasource.configuration")
    public DataSource collectflowDataSource() {
        return collectflowDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }
    
    @Bean
    @ConfigurationProperties("spring.collectflow.jpa")
    public JpaProperties collectflowJpaProperties() {
        return new JpaProperties();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean collectflowEntityManagerFactory(@Qualifier("collectflowDataSource")DataSource collectflowDataSource,
    		@Qualifier("collectflowJpaProperties")JpaProperties collectflowJpaProperties) throws SQLException {
        EntityManagerFactoryBuilder builder = createEntityManagerFactoryBuilder(collectflowJpaProperties);
        return builder.dataSource(collectflowDataSource).packages("com.pk.collectflow")
        		.persistenceUnit("collectflowPersistenceUnit").build();
    }

    private EntityManagerFactoryBuilder createEntityManagerFactoryBuilder(JpaProperties collectflowJpaProperties) {
    	HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
    	jpaVendorAdapter.setGenerateDdl(collectflowJpaProperties.isGenerateDdl());
    	jpaVendorAdapter.setShowSql(collectflowJpaProperties.isShowSql());
    	collectflowJpaProperties.getProperties().put("hibernate.implicit_naming_strategy", HibernateImplicitNamingStratergy.class.getName());
    	collectflowJpaProperties.getProperties().put("hibernate.physical_naming_strategy", HibernateNamingStrategy.class.getName());
        
        log.info("Collectflow JPA properties: {}", collectflowJpaProperties.getProperties());
        return new EntityManagerFactoryBuilder(jpaVendorAdapter, collectflowJpaProperties.getProperties(), null);
    }  

    @Bean
    public PlatformTransactionManager collectflowTransactionManager(@Qualifier("collectflowEntityManagerFactory") EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }
	
}