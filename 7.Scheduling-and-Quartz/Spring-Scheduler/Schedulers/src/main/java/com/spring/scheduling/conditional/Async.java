package com.spring.scheduling.conditional;

import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

// Parallel behavior in scheduled tasks.
// Only possible with asynchronous tasks(Not dependent on each other).
// i.e. fixedRate(Works with asynchronous tasks)
// Now, asynchronous task will be invoked each second, even if the previous task isn’t done.
@Component
@EnableAsync
public class Async {

    @org.springframework.scheduling.annotation.Async
//    @Scheduled(fixedRate = 1000)
    public void scheduleFixedRateTaskAsync() throws InterruptedException {
        System.out.println("Fixed rate task async - " + System.currentTimeMillis() / 1000);
        Thread.sleep(2000);
    }
}
