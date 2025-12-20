package com.savan.reactive.programming.observerdesignpattern;

public interface ISubject {

    void add(IObserver observer);
    void remove(IObserver observer);
    void notifyAllObservers();
}
