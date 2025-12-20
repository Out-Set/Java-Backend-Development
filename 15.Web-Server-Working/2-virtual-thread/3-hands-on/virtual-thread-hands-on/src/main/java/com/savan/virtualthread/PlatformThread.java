package com.savan.virtualthread;

public class PlatformThread {

    public static void task() {
        System.out.println("start task: "+Thread.currentThread());
    }

    public static void main(String[] args) {

        Thread platformThread = Thread.ofPlatform().unstarted(PlatformThread::task);
        platformThread.start();
    }
}
