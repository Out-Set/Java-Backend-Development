package com.savan.spring.event.listener;

import com.savan.spring.event.event.BigBangTheoryEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AnujListener {

    public void watchComedyCircus(String episodeNo) {
        System.out.println("Anuj started watching Comedy Circus, episode-no: "+episodeNo);
    }

    @EventListener
    public void anujListenToEvent(BigBangTheoryEvent event) {
        watchComedyCircus(event.getEpisodeNo());
    }
}
