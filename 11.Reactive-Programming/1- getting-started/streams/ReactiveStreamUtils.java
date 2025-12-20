package com.savan.reactive.programming.streams;

import reactor.core.publisher.Flux;

import java.time.Duration;

public class ReactiveStreamUtils {

    public static Flux<String> wordReactiveStream(){
        return Flux.just("hello", "world", "fun", "learning");
    }
}
