package com.savan.reactive.programming.reactivestreamdesignpattern.subscriber;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class SubscriberImpl implements Subscriber<String> {

    private Subscription subscription;

    @Override
    public void onSubscribe(Subscription subscription) {
        System.out.println("subscription successful");
        this.subscription = subscription;
    }

    @Override
    public void onNext(String s) {
        System.out.println("onNext() called ...");
        System.out.println("data received "+ s);
    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onComplete() {
        System.out.println("onComplete() called ...");
    }

    public Subscription getSubscription() {
        return subscription;
    }
}
