public class BasicThreadExample {
    public static void main(String[] args) throws Exception {
        System.out.println("Created Threads");

        // Two threads are created.
        new Mythread().start();
        new Mythread().start();

    }
}

class Mythread extends Thread {
    public void run() {
        System.out.println(getName() + " is running...");

    }
}