package observerdesignpattern;

public interface ISubject {

    void add(IObserver observer);
    void remove(IObserver observer);
    void notifyAllObservers();
}
