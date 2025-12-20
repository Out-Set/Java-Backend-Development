package com.savan.quartz.scheduler.serviceimpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BeanAccessHelper {

	@Autowired
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

