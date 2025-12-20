package com.savan.reactive.programming.observerdesignpattern;

public class GoldTrackerApp implements IObserver {

    private Gold gold;  // subject

    private int goldPrice;

    public GoldTrackerApp(Gold gold) {
        this.gold = gold;
        gold.add(this);
    }

    @Override
    public void update(int price) {
        this.goldPrice = price;
        displayLatestGoldPrice();
    }

    private void displayLatestGoldPrice() {
        System.out.println("the latest gold price is "+ this.goldPrice);
    }

    public int getGoldPrice() {
        return goldPrice;
    }
}
