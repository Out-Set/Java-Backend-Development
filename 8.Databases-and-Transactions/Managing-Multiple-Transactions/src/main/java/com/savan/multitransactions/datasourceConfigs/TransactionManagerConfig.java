package com.savan.multitransactions.datasourceConfigs;

import jakarta.transaction.SystemException;

import jakarta.transaction.UserTransaction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;

@Configuration
@EnableTransactionManagement
public class TransactionManagerConfig {

    @Bean(initMethod = "init", destroyMethod = "close")
    public UserTransactionManager userTransactionManager() throws SystemException {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setTransactionTimeout(30000);
        userTransactionManager.setForceShutdown(true);
        return userTransactionManager;
    }

    @Bean
    public UserTransaction userTransaction() throws Throwable {
        UserTransactionImp userTransactionImp = new UserTransactionImp();
        userTransactionImp.setTransactionTimeout(30000);
        return userTransactionImp;
    }

    @Bean
    public JtaTransactionManager transactionManager() throws Throwable {
        JtaTransactionManager jtaTransactionManager = new JtaTransactionManager();
        jtaTransactionManager.setUserTransaction(userTransaction());
        jtaTransactionManager.setTransactionManager(userTransactionManager());
        return jtaTransactionManager;
    }
}
