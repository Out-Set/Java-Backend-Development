package com.savan.spring.event.listener;

import com.savan.spring.event.event.BigBangTheoryEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class JustinListener {

    public void watchComedyCircus(String episodeNo) {
        System.out.println("Justin started watching Comedy Circus, episode-no: "+episodeNo);
    }

    @EventListener
    public void justinListenToEvent(BigBangTheoryEvent event) {
        watchComedyCircus(event.getEpisodeNo());
    }
}
