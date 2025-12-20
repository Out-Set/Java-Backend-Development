package com.savan.virtualthread;

import jdk.management.VirtualThreadSchedulerMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;

public class VirtualThread {

    public static void task() {
        System.out.println("start task: "+Thread.currentThread());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("end task: "+Thread.currentThread());
    }

    public static void main(String[] args) throws InterruptedException {

        int noOfThreads = 10;

        VirtualThreadSchedulerMXBean mxBean = ManagementFactory.getPlatformMXBean(VirtualThreadSchedulerMXBean.class);
        System.out.println(mxBean);
        mxBean.setParallelism(1);

        ArrayList<Thread> virtualThreads = new ArrayList<>();
        for (int i=0; i<noOfThreads; i++) {
            Thread virtualThread = Thread.ofVirtual().unstarted(VirtualThread::task);
            virtualThreads.add(virtualThread);
        }
        for (Thread virtualThread: virtualThreads) {
            System.out.println(mxBean);
            virtualThread.start();
        }
        for (Thread virtualThread: virtualThreads) {
            virtualThread.join();
        }

        System.out.println(mxBean);
    }
}
