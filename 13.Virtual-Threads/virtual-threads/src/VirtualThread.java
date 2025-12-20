import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class VirtualThread {

    static AtomicInteger threadCount = new AtomicInteger();

    public static void handleUserRequest() {
        int count = threadCount.incrementAndGet();
        System.out.println("stating thread-"+count+" : "+Thread.currentThread());

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

        System.out.println("ending thread-"+count+" : "+Thread.currentThread());
    }

    public static void main(String[] args) throws InterruptedException {

        int threadCount = 1000;
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        long startTime = System.currentTimeMillis();
        for(int i=1; i<=1000; i++) {
            Thread.startVirtualThread(() -> {
                handleUserRequest();
                countDownLatch.countDown();
            });
        }
        long endTime = System.currentTimeMillis();
        countDownLatch.await();

        System.out.println("threadCount: "+threadCount+", created in - "+(endTime-startTime)+" millisecounds");
    }
}
