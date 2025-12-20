package observerdesignpattern;

import java.util.ArrayList;
import java.util.List;

public class Gold implements ISubject{

    private int price;

    private List<IObserver> observerList = new ArrayList<>();

    public void setPrice(int price) {
        this.price = price;
        notifyAllObservers();
    }

    @Override
    public void add(IObserver observer) {
        observerList.add(observer);
    }

    @Override
    public void remove(IObserver observer) {
        observerList.remove(observer);
    }

    @Override
    public void notifyAllObservers() {
        for (IObserver observer: observerList){
            observer.update(price);
        }
    }
}
