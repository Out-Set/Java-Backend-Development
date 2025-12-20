// Simple join() method
// Join() Method Without Parameter

public class JoinMethodExample extends Thread{
    JoinMethodExample(String str){
        super(str);
        start();
    }

    public void run(){
        try {
            System.out.println("Hello from thread " + getName());
            Thread.sleep(1000);
            System.out.println("Bye from thread " + getName());
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

// Since, First thread uses join() method, all the other threads must wait before it(i.e. First) get finished.

    public static void main(String[] args) {
        try {
            new JoinMethodExample("First").join();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        new JoinMethodExample("Second");
        new JoinMethodExample("Third");
    }
}
