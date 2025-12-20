package com.pk.quartz.scheduler.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Named("beanAccessHelper")
public class BeanAccessHelper {

	@Inject
	private ApplicationContext applicationContext;

    private final Map<String, Object> beansCache = new ConcurrentHashMap<>();

    public <T> T getBean(String beanName, Class<T> requiredType) throws BeansException {
        String keyCode = beanName + "#" + requiredType.getName();
        if (beansCache.containsKey(keyCode)) {
            log.debug("Cache hit for bean '{}'.", keyCode);
        } else {
            log.debug("Cache miss for bean '{}'. Fetching from ApplicationContext.", keyCode);
        }
        return (T) beansCache.computeIfAbsent(keyCode, key -> fetchBean(beanName, requiredType));
    }


    private <T> T fetchBean(String beanName, Class<T> requiredType) {
        if (!applicationContext.containsBean(beanName)) {
            log.warn("Bean with name '{}' does not exist in the BeanFactory.", beanName);
        }
        return applicationContext.getBean(beanName, requiredType);
    }

}

