package com.savan.reactive.programming.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureDemo2 {

    public String getName() {
        System.out.println("getName() executed by thread: "+Thread.currentThread().getName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "Savan";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("main method executed by thread: "+Thread.currentThread().getName());
        CompletableFutureDemo2 completableFutureDemo2 = new CompletableFutureDemo2();
        CompletableFuture.supplyAsync(() -> completableFutureDemo2.getName())
                .thenApply(str -> str.toUpperCase())
                .thenAccept(name -> System.out.println(name));

        System.out.println("done ...");

        // holding the main thread so that we can get the result of CompletableFuture
        // this is also we are blocking but to see the effect of CompletableFuture
        Thread.sleep(5000);
    }
}
