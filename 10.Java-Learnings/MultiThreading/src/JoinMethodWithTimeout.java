// Join() Method With Parameter

public class JoinMethodWithTimeout extends Thread {
    JoinMethodWithTimeout(String str){
        super(str);
    }

    public void run(){
        for(int i=1; i<=5; i++){
            try {
                Thread.sleep(300);
                System.out.println(getName() + " i=" + i);
            } catch (Exception E) {
                System.out.println("Error: " + E);
            }
        }
    }

    public static void main(String[] args) {
        JoinMethodWithTimeout th1 = new JoinMethodWithTimeout("First");
        JoinMethodWithTimeout th2 = new JoinMethodWithTimeout("Second");
        JoinMethodWithTimeout th3 = new JoinMethodWithTimeout("Third");

        th1.start();

        // thread th1 can atmost use 900 ms to make other thread wait.
        // After that all three threads are vying for CPU's time.
        // Inside run method it is sleeping for 300 ms.

        try {
            th1.join(900);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        th2.start();
        th3.start();
    }
}

