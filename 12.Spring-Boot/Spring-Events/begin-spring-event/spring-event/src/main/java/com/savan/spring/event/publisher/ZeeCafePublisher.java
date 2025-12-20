package com.savan.spring.event.publisher;

import com.savan.spring.event.event.BigBangTheoryEvent;
import com.savan.spring.event.event.ComedyCircusEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ZeeCafePublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void streamBigBangTheory(String episodeNo) {
        System.out.println("Zee Cafe : Starting BBT: "+episodeNo);
        applicationEventPublisher.publishEvent(new BigBangTheoryEvent(episodeNo));
    }

    public void streamComedyCircus(String episodeNo) {
        System.out.println("Zee Cafe : Starting Comedy Circus: "+episodeNo);
        applicationEventPublisher.publishEvent(new ComedyCircusEvent(episodeNo));
    }
}
