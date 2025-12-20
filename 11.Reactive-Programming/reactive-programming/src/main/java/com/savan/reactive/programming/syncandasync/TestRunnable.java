package com.savan.reactive.programming.syncandasync;

public class TestRunnable {
    public static void main(String[] args) {

        Thread thread = new Thread(new RunnableTask());
        thread.start();

        for (int i=0; i<10; i++) {
            System.out.println("doing some task by main method.. , ran by thread - " + Thread.currentThread().getName());
        }
    }
}

class RunnableTask implements Runnable {

    @Override
    public void run() {
        for (int i=0; i<10; i++){
            System.out.println("doing some task by runnable.. , ran by thread - "+Thread.currentThread().getName());
        }
    }
}
