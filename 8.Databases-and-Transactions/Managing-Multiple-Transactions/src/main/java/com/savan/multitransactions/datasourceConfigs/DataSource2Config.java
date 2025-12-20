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
@EnableJpaRepositories( basePackages = "com.savan.multitransactions.db2",
        entityManagerFactoryRef = "db2EntityManagerFactory", transactionManagerRef = "transactionManager")
public class DataSource2Config {

    @Value("${spring.db2.XADataSource}")
    private String XADataSource;

    @Bean
    @ConfigurationProperties("spring.db2.datasource")
    public DataSourceProperties db2DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource db2DataSource() throws SQLException {
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();

        ds.setUniqueResourceName("db2-resource");
        ds.setXaDataSourceClassName(XADataSource);

        System.out.println("Database-1 Details ....");
        System.out.println("Username: "+db2DataSourceProperties().getUsername());
        System.out.println("Password: "+db2DataSourceProperties().getPassword());
        System.out.println("Database-Url: "+db2DataSourceProperties().getUrl());

        Properties p = new Properties();
        p.setProperty ("user", db2DataSourceProperties().getUsername());
        p.setProperty ("password", db2DataSourceProperties().getPassword());
        p.setProperty ("URL", db2DataSourceProperties().getUrl());

        ds.setXaProperties(p);
        ds.setPoolSize(5);

        return ds;
    }

    @Bean
    @ConfigurationProperties("spring.db2.jpa")
    public JpaProperties db2JpaProperties() {
        return new JpaProperties();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean db2EntityManagerFactory(
            @Qualifier("db2DataSource") DataSource db2DataSource,
            @Qualifier("db2JpaProperties") JpaProperties db2JpaProperties) throws SQLException {

        EntityManagerFactoryBuilder builder = createEntityManagerFactoryBuilder(db2JpaProperties);
        return builder.dataSource(db2DataSource)
                .packages("com.savan.multitransactions.db2")
                .persistenceUnit("db2PersistenceUnit")
                .jta(true)
                .build();
    }

    private EntityManagerFactoryBuilder createEntityManagerFactoryBuilder(JpaProperties db2JpaProperties) {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setGenerateDdl(db2JpaProperties.isGenerateDdl());
        jpaVendorAdapter.setShowSql(db2JpaProperties.isShowSql());
        db2JpaProperties.getProperties().put("hibernate.implicit_naming_strategy", HibernateImplicitNamingStrategy.class.getName());
        db2JpaProperties.getProperties().put("hibernate.physical_naming_strategy", HibernateNamingStrategy.class.getName());

        log.info("db2 JPA properties: {}", db2JpaProperties.getProperties());
        return new EntityManagerFactoryBuilder(jpaVendorAdapter, db2JpaProperties.getProperties(), null);
    }

}