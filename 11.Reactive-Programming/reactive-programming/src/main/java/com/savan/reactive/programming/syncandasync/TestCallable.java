package com.savan.reactive.programming.syncandasync;

import java.util.concurrent.*;

public class TestCallable {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // This will be creating threads behind the scene for you.
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<String> returnValueFromFuture  =executorService.submit(new CallableTask()); // non blocking
        System.out.println(returnValueFromFuture.get());
        for (int i=0; i<10; i++) {
            System.out.println("doing some task by main method.. , ran by thread - " + Thread.currentThread().getName());
        }

    }
}

class CallableTask implements Callable<String> {

    @Override
    public String call() throws Exception {
        for (int i=0; i<10; i++){
            System.out.println("doing some task by callable.. , ran by thread - "+Thread.currentThread().getName());
        }
        return "done doing my task";
    }
}
