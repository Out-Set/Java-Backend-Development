public class DaemonThread extends Thread{
    public DaemonThread(String name){
        super(name);
    }

    public void run(){
        if(Thread.currentThread().isDaemon()){
            System.out.println(getName() + " is Daemon Thread");
        }
        else{
            System.out.println(getName() + " is User Thread");
        }
    }

    public static void main(String[] args) {
        DaemonThread t1 = new DaemonThread("t1");
        DaemonThread t2 = new DaemonThread("t2");
        DaemonThread t3 = new DaemonThread("t3");
        DaemonThread t4 = new DaemonThread("t4");

        // Setting user thread t1 to Daemon
        t1.setDaemon(true);

        t1.start();
        t2.start();

        // Setting user thread t3 to Daemon
        t3.setDaemon(true);
        t3.start();

        t4.start();

        // Exception as the thread t4 is already started.
        t4.setDaemon(true);
    }
}
