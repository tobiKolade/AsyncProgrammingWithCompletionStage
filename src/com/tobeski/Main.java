package com.tobeski;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) {
        // write your code here
        Runnable task = () -> System.out.println("Hello world!");

        ExecutorService service = Executors.newSingleThreadExecutor();

        //Future<?> future = service.submit(task);
        CompletableFuture<Void> completableFuture =
            CompletableFuture.runAsync(task, service);
        //service.submit(task);
     }
}
