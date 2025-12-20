// Thread Example With Thread Sleep and Constructor

class SleepingThread extends Thread {
    SleepingThread(String tname) {
        super(tname);
    }

    public void run(){
        System.out.println(getName() + " started");
        for(int i=1; i<=5; i++){
            try {
                Thread.sleep(800);
            } catch (Exception e) {
                System.out.println(e);
            }

            System.out.println(getName() + " i=" + i);
        }
        System.out.println(getName() + " finished");
    }
}


public class ThreadSleep {
    public static void main(String[] args) {
        SleepingThread st1 = new SleepingThread("first");
        SleepingThread st2 = new SleepingThread("second");
        SleepingThread st3 = new SleepingThread("third");
        SleepingThread st4 = new SleepingThread("fourth");

        st1.start();
        st2.start();
        st3.start();
        st4.start();
    }
}
