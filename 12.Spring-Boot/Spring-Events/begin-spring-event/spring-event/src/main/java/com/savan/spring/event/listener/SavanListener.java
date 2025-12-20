package com.savan.spring.event.listener;

import com.savan.spring.event.event.BigBangTheoryEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SavanListener {

    public void watchBigBang(String episodeNo) {
        System.out.println("Savan started watching BBT, episode-no: "+episodeNo);
    }

    @EventListener
    public void savanListenToEvent(BigBangTheoryEvent event) {
        watchBigBang(event.getEpisodeNo());
    }
}
