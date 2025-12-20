package Work.Thread;

public class ThreadDemo {
    public static void main(String[] args) {

        Runnable thread1 = () -> {
            // Body of thread
            for(int i=1; i<=10; i++) {
                System.out.println("Value of i is " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Thread t1= new Thread(thread1);
        t1.setName("Sawan");
        t1.start();



        Runnable thread2 = () -> {

            for(int i=1; i<=10; i++){
                System.out.println("2 X " + i + " = " + 2*i);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        Thread t2 = new Thread(thread2);
        t2.start();
    }
}
