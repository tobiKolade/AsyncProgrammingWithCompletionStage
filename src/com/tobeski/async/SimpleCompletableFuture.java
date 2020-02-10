package com.tobeski.async;

import java.util.concurrent.CompletableFuture;

public class SimpleCompletableFuture {

    public static void main(String[] args) {

        CompletableFuture<Void> completableFuture = new CompletableFuture<>();

        Runnable task = () -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {

            }
            completableFuture.complete(null);
        };
        CompletableFuture.runAsync(task);
        Void nil = completableFuture.join();
        System.out.println("We are done");
    }
}
