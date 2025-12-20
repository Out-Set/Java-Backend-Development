package com.savan.reactive.programming.reactivetest03;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

public class ReactiveTest03 {
    public static void main(String[] args) {
        Flux<String> stringFlux = Flux.just("hello", "world", "good", "morning");
        stringFlux.subscribe(new WordSubscriber());
    }
}

class WordSubscriber extends BaseSubscriber<String> {

    @Override
    protected void hookOnSubscribe(Subscription subscription) {
        System.out.println("subscription successful");
        request(1);
    }

    @Override
    protected void hookOnNext(String value) {
        if(value.equals("good")) cancel();
        System.out.println("data received: "+value);
        request(1);
    }

    @Override
    protected void hookOnComplete() {
        System.out.println("completed ...");
    }
}