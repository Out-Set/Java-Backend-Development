package com.savan.spring.event.event;

public class ComedyCircusEvent {

    private String episodeNo;

    public ComedyCircusEvent(String episodeNo) {
        this.episodeNo = episodeNo;
    }

    public String getEpisodeNo() {
        return episodeNo;
    }
}
