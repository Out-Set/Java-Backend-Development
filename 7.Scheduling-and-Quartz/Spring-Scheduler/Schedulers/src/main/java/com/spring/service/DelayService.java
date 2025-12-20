package com.spring.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
public class DelayService {

    private long delay = 1000;

    public void tickWIthDelay() {
        final long now = System.currentTimeMillis() / 1000;
        System.out.println("Schedule tasks with dynamic delay - " + now);
        System.out.println("Dynamic Scheduling, With Delay: " + delay);
    }
}
