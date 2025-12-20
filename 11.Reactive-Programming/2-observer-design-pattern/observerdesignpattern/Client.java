package observerdesignpattern;

public class Client {

    public static void main(String[] args) {

        Gold goldSubject = new Gold();

        GoldTrackerApp observer1 = new GoldTrackerApp(goldSubject);
        GoldTrackerApp observer2 = new GoldTrackerApp(goldSubject);

        goldSubject.setPrice(2000);
        goldSubject.setPrice(4000);

        goldSubject.remove(observer1);
        goldSubject.setPrice(5000);
    }
}
