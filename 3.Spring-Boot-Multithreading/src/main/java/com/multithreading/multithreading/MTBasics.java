package com.multithreading.multithreading;

public class MTBasics {
    public static void main(String[] args) {

        // Create thread, by extending thread class
        MTBasics o1 = new MTBasics();
        MyThread1 thread1 = o1.new MyThread1();
        thread1.start();
        // thread.start();
        // thread.run();
        // thread.run();


        // Create thread, by implementing a runnable interface
        MTBasics o2 = new MTBasics();
        Thread thread2 = new Thread(o2.new MyThread2());
        thread2.start();

        // *Not Recommended
        // By both, extending thread class and implementing a runnable interface
        MTBasics o3 = new MTBasics();
        MyThread3 thread3 = o3.new MyThread3();
        thread3.start();
    }

    // Create thread, by extending thread class
    private class MyThread1 extends Thread {
        @Override
        public void run(){
            System.out.println("In  thread :" + currentThread().getName());
        }
    }

    // Create thread, by implementing a runnable interface
    private class MyThread2 implements Runnable {
        public void run(){
            System.out.println("In  thread :" + Thread.currentThread().getName());
        }
    }

    // By both, extending thread class and implementing a runnable interface
    private class MyThread3 extends Thread implements Runnable{
        public void run(){
            System.out.println("In  thread :" + Thread.currentThread().getName());
        }
    }
}
