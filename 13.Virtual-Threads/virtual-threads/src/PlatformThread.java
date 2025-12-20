import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.CountDownLatch;

public class PlatformThread {

    public static void handleUserRequest() {
        System.out.println("stating thread: "+Thread.currentThread());

        try(var socket  = new Socket("www.google.com", 80)) {
            System.out.println("connected to google ...");
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /*
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */

        System.out.println("ending thread: "+Thread.currentThread());
    }

    public static void main(String[] args) throws InterruptedException {
        int threadCount = 1000;
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        long startTime = System.currentTimeMillis();
        for(int i=1; i<=1000; i++) {
            new Thread(() -> {
                handleUserRequest();
                countDownLatch.countDown();
            }).start();
        }
        long endTime = System.currentTimeMillis();
        countDownLatch.await();

        System.out.println("threadCount: "+threadCount+", created in - "+(endTime-startTime)+" millisecounds");
    }
}
