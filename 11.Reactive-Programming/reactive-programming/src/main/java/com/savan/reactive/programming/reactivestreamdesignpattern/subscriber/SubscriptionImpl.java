package com.savan.reactive.programming.reactivestreamdesignpattern.subscriber;

import com.github.javafaker.Faker;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class SubscriptionImpl implements Subscription {

    private Subscriber subscriber;

    private Faker faker = Faker.instance();

    private int maxData = 10;
    private int count = 0;

    private boolean isCanceled = false;

    public SubscriptionImpl(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void request(long n) {
        if (isCanceled) return;
        System.out.println("subscriber has requested "+n+" data");
        for (int i=0;i<n && count<maxData;i++){
            subscriber.onNext(faker.name().fullName());
            count++;
        }
        if(count == maxData){
            System.out.println("publisher has emitted all data ...");
            subscriber.onComplete();
        }
    }

    @Override
    public void cancel() {
        isCanceled = true;
        System.out.println("subscriber has canceled the subscription ...");
    }
}
