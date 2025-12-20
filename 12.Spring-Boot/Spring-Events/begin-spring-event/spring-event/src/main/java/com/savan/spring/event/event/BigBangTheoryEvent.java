package com.savan.spring.event.event;

public class BigBangTheoryEvent {

    private String episodeNo;

    public BigBangTheoryEvent(String episodeNo) {
        this.episodeNo = episodeNo;
    }

    public String getEpisodeNo() {
        return episodeNo;
    }
}
