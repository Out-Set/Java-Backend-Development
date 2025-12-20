package com.savan.reactive.programming.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

public class CompletableFutureDemo1 {

    public String getName() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "Savan";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("main method executed by thread: "+Thread.currentThread().getName());
        CompletableFutureDemo1 completableFutureDemo1 = new CompletableFutureDemo1();
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                return completableFutureDemo1.getName();
            }
        });
        String value = completableFuture.get(); // blocking
        // or String value = completableFuture.join(); // blocking
        System.out.println(value);

        System.out.println("done ...");
    }
}
