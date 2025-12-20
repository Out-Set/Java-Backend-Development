package com.savan.multitransactions.datasourceConfigs;

import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.savan.multitransactions.coreGenerator.HibernateImplicitNamingStrategy;
import com.savan.multitransactions.coreGenerator.HibernateNamingStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableJpaRepositories( basePackages = "com.savan.multitransactions.db1",
        entityManagerFactoryRef = "db1EntityManagerFactory", transactionManagerRef = "transactionManager")
public class DataSource1Config {

    @Value("${spring.db1.XADataSource}")
    private String XADataSource;

    @Bean
    @ConfigurationProperties("spring.db1.datasource")
    public DataSourceProperties db1DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource db1DataSource() throws SQLException {
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();

        ds.setUniqueResourceName("db1-resource");
        ds.setXaDataSourceClassName(XADataSource);

        System.out.println("Database-1 Details ....");
        System.out.println("Username: "+db1DataSourceProperties().getUsername());
        System.out.println("Password: "+db1DataSourceProperties().getPassword());
        System.out.println("Database-Url: "+db1DataSourceProperties().getUrl());

        Properties p = new Properties();
        p.setProperty ("user", db1DataSourceProperties().getUsername());
        p.setProperty ("password", db1DataSourceProperties().getPassword());
        p.setProperty ("URL", db1DataSourceProperties().getUrl());

        ds.setXaProperties(p);
        ds.setPoolSize(5);

        return ds;
    }

    @Bean
    @ConfigurationProperties("spring.db1.jpa")
    public JpaProperties db1JpaProperties() {
        return new JpaProperties();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean db1EntityManagerFactory(
            @Qualifier("db1DataSource") DataSource db1DataSource,
            @Qualifier("db1JpaProperties") JpaProperties db1JpaProperties) throws SQLException {

        EntityManagerFactoryBuilder builder = createEntityManagerFactoryBuilder(db1JpaProperties);
        return builder.dataSource(db1DataSource)
                .packages("com.savan.multitransactions.db1")
                .persistenceUnit("db1PersistenceUnit")
                .jta(true)
                .build();
    }

    private EntityManagerFactoryBuilder createEntityManagerFactoryBuilder(JpaProperties db1JpaProperties) {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setGenerateDdl(db1JpaProperties.isGenerateDdl());
        jpaVendorAdapter.setShowSql(db1JpaProperties.isShowSql());
        db1JpaProperties.getProperties().put("hibernate.implicit_naming_strategy", HibernateImplicitNamingStrategy.class.getName());
        db1JpaProperties.getProperties().put("hibernate.physical_naming_strategy", HibernateNamingStrategy.class.getName());

        log.info("Db1 JPA properties: {}", db1JpaProperties.getProperties());
        return new EntityManagerFactoryBuilder(jpaVendorAdapter, db1JpaProperties.getProperties(), null);
    }

}
