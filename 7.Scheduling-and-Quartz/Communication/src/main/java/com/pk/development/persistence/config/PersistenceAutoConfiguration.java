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
import org.springframework.context.annotation.Primary;
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
@EnableJpaRepositories( basePackages = "com.pk.integration",
	entityManagerFactoryRef = "integrationEntityManagerFactory", transactionManagerRef = "integrationTransactionManager")
public class PersistenceAutoConfiguration implements PersistenceConfigDescriptor {

	@Override
	public String getDatabaseName() {
		return "Integration";
	}
	
	@Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    //@ConfigurationProperties("app.datasource.configuration")
    public DataSource integrationDataSource() {
        return dataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }
    
    @Bean
    @Primary
    @ConfigurationProperties("spring.jpa")
    public JpaProperties integerationJpaProperties() {
        return new JpaProperties();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean integrationEntityManagerFactory(DataSource integrationDataSource,
    		JpaProperties integerationJpaProperties) throws SQLException {
        EntityManagerFactoryBuilder builder = createEntityManagerFactoryBuilder(integerationJpaProperties);
        return builder.dataSource(integrationDataSource).packages("com.pk.integration")
        		.persistenceUnit("integrationPersistenceUnit").build();
    }

    private EntityManagerFactoryBuilder createEntityManagerFactoryBuilder(JpaProperties integrationJpaProperties) {
    	HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
    	jpaVendorAdapter.setGenerateDdl(integrationJpaProperties.isGenerateDdl());
    	jpaVendorAdapter.setShowSql(integrationJpaProperties.isShowSql());
    	integrationJpaProperties.getProperties().put("hibernate.implicit_naming_strategy", HibernateImplicitNamingStratergy.class.getName());
    	integrationJpaProperties.getProperties().put("hibernate.physical_naming_strategy", HibernateNamingStrategy.class.getName());
        
        log.debug(" Integration JPA properties: {}", integrationJpaProperties.getProperties());
        return new EntityManagerFactoryBuilder(jpaVendorAdapter, integrationJpaProperties.getProperties(), null);
    }  
    
    @Bean
    @Primary
    public PlatformTransactionManager integrationTransactionManager(@Qualifier("integrationEntityManagerFactory") EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }
	
}