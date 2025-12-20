package com.savan.reactive.programming.completablefuture;

import java.util.concurrent.*;

public class FutureDemo1 {

    public String getFirstName() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "Savan";
    }

    public String getLastName() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "Prajapati";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        /*
        long startTime = System.currentTimeMillis();
        FutureDemo1 futureDemo1 = new FutureDemo1();
        String firstName = futureDemo1.getFirstName();
        String lastName  = futureDemo1.getLastName();
        String completeName = firstName+" "+lastName;
        System.out.println(completeName);
        System.out.println("time take to execute these tasks: "+(System.currentTimeMillis()-startTime));
        */

        System.out.println("main method executed by thread: "+Thread.currentThread().getName());
        long startTime = System.currentTimeMillis();
        FutureDemo1 futureDemo1 = new FutureDemo1();

        Callable<String> firstName = new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("getFirstName() executed by thread: "+Thread.currentThread().getName());
                return futureDemo1.getFirstName();
            }
        };
        Callable<String> lastName = new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("getLastName() executed by thread: "+Thread.currentThread().getName());
                return futureDemo1.getLastName();
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<String> firstNameFuture = executorService.submit(firstName);
        Future<String> lastNameFuture = executorService.submit(lastName);

        String completeName = firstNameFuture.get()+" "+lastNameFuture.get();
        System.out.println(completeName);
        System.out.println("time take to execute these tasks: "+(System.currentTimeMillis()-startTime));
    }
}
