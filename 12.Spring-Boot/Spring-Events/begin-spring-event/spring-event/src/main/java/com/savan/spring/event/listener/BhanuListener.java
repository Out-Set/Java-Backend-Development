package com.savan.spring.event.listener;

import com.savan.spring.event.event.BigBangTheoryEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BhanuListener {

    public void watchBigBang(String episodeNo) {
        System.out.println("Bhanu started watching BBT, episode-no: "+episodeNo);
    }

    @EventListener
    public void bhanuListenToEvent(BigBangTheoryEvent event) {
        watchBigBang(event.getEpisodeNo());
    }
}
