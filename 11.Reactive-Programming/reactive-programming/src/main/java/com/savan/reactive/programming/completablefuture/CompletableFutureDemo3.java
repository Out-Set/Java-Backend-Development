package com.savan.reactive.programming.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureDemo3 {

    public String getFirstName() {
        System.out.println("getFirstName() executed by thread: "+Thread.currentThread().getName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "Savan";
    }

    public String getLastName() {
        System.out.println("getLastName() executed by thread: "+Thread.currentThread().getName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "Prajapati";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        System.out.println("main method executed by thread: "+Thread.currentThread().getName());
        long startTime = System.currentTimeMillis();
        CompletableFutureDemo3 completableFutureDemo3 = new CompletableFutureDemo3();
        CompletableFuture<String> completableFutureFirstName = CompletableFuture.supplyAsync(() -> completableFutureDemo3.getFirstName());
        CompletableFuture<String> completableFutureLastName = CompletableFuture.supplyAsync(() -> completableFutureDemo3.getLastName());

//        String s1 = completableFutureFirstName.get();
//        String s2 = completableFutureLastName.get();
//        System.out.println(s1+" "+s2);

        String finalOutput = completableFutureFirstName.thenCombine(completableFutureLastName, (str1, str2) -> str1+" "+str2).join();
        System.out.println(finalOutput);

        System.out.println("time take to execute these tasks: "+(System.currentTimeMillis()-startTime));
    }
}
