package com.savan.reactive.programming.reactivestreamdesignpattern;

import com.savan.reactive.programming.reactivestreamdesignpattern.publisher.PublisherImpl;
import com.savan.reactive.programming.reactivestreamdesignpattern.subscriber.SubscriberImpl;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public class App {

    public static void main(String[] args) {

        Publisher publisher = new PublisherImpl();
        SubscriberImpl subscriber = new SubscriberImpl();
        publisher.subscribe(subscriber);
        subscriber.getSubscription().request(2);
        subscriber.getSubscription().request(2);
        subscriber.getSubscription().cancel();
        subscriber.getSubscription().request(2);
        subscriber.getSubscription().request(2);

    }
}
