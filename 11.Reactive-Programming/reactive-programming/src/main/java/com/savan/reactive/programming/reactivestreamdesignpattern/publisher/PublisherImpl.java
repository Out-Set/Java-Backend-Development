package com.savan.reactive.programming.reactivestreamdesignpattern.publisher;

import com.savan.reactive.programming.reactivestreamdesignpattern.subscriber.SubscriptionImpl;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class PublisherImpl implements Publisher<String> {

    @Override
    public void subscribe(Subscriber<? super String> subscriber) {
        Subscription subscription = new SubscriptionImpl(subscriber);
        subscriber.onSubscribe(subscription);
    }
}
