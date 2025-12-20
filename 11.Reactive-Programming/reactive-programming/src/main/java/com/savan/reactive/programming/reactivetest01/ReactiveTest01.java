package com.savan.reactive.programming.reactivetest01;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

public class ReactiveTest01 {
    public static void main(String[] args) {

        /*
        Flux<String> nameFlux = Flux.just("hello", "world", "good", "morning");.log();
        nameFlux.subscribe();
        01:07:37.809 [main] INFO reactor.Flux.Array.1 -- | onSubscribe([Synchronous Fuseable] FluxArray.ArraySubscription)
        01:07:37.817 [main] INFO reactor.Flux.Array.1 -- | request(unbounded)
        01:07:37.817 [main] INFO reactor.Flux.Array.1 -- | onNext(hello)
        01:07:37.818 [main] INFO reactor.Flux.Array.1 -- | onNext(world)
        01:07:37.818 [main] INFO reactor.Flux.Array.1 -- | onNext(good)
        01:07:37.818 [main] INFO reactor.Flux.Array.1 -- | onNext(morning)
        01:07:37.819 [main] INFO reactor.Flux.Array.1 -- | onComplete()
         */

        /*
        Flux<String> nameFlux = Flux.just("hello", "world", "good", "morning");
        nameFlux.subscribe(name -> System.out.println(name));
        hello
        world
        good
        morning
         */

        // Subscribing my own subscriber (MySubscriber)
        Flux<String> nameFlux = Flux.just("hello", "world", "good", "morning").log();
        nameFlux.subscribe(new MySubscriber());
        /*
        01:19:43.455 [main] INFO reactor.Flux.Array.1 -- | onSubscribe([Synchronous Fuseable] FluxArray.ArraySubscription)
        onSubscribe() method called ...
        01:19:43.458 [main] INFO reactor.Flux.Array.1 -- | request(unbounded)
        01:19:43.459 [main] INFO reactor.Flux.Array.1 -- | onNext(hello)
        onNext() method called ...
        data received: hello
        01:19:43.459 [main] INFO reactor.Flux.Array.1 -- | onNext(world)
        onNext() method called ...
        data received: world
        01:19:43.459 [main] INFO reactor.Flux.Array.1 -- | onNext(good)
        onNext() method called ...
        data received: good
        01:19:43.459 [main] INFO reactor.Flux.Array.1 -- | onNext(morning)
        onNext() method called ...
        data received: morning
        01:19:43.459 [main] INFO reactor.Flux.Array.1 -- | onComplete()
        onComplete() method called ...
         */
    }
}

class MySubscriber implements Subscriber<String> {

    @Override
    public void onSubscribe(Subscription subscription) {
        System.out.println("onSubscribe() method called ...");
        subscription.request(Long.MAX_VALUE); // unbounded(large)
    }

    @Override
    public void onNext(String data) {
        System.out.println("onNext() method called ...");
        System.out.println("data received: "+data);
    }

    @Override
    public void onError(Throwable t) {
        System.out.println("onError() method called ...");
    }

    @Override
    public void onComplete() {
        System.out.println("onComplete() method called ...");
    }
}