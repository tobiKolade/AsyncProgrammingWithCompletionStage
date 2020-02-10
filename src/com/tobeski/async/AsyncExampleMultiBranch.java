package com.tobeski.async;

import com.tobeski.async.model.Email;
import com.tobeski.async.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class AsyncExampleMultiBranch {

    public static void main(String[] args) {

        ExecutorService executor1 = Executors.newSingleThreadExecutor();

        Supplier<List<Long>> supplyIDs = () -> {
            sleep(200);
            return Arrays.asList(1L, 2L, 3L);
        };

        Function<List<Long>, CompletableFuture<List<User>>> fetchUsers = ids -> {
            sleep(250);
            Supplier<List<User>> userSupplier =
                    () -> {
                        return ids.stream().map(User::new).collect(Collectors.toList());
                    };
            return CompletableFuture.supplyAsync(userSupplier);
        };

        Function<List<Long>, CompletableFuture<List<Email>>> fetchEmails = ids -> {
            sleep(350);
            Supplier<List<Email>> emailSupplier =
                    () -> {
                        return ids.stream().map(Email::new).collect(Collectors.toList());
                    };
            return CompletableFuture.supplyAsync(emailSupplier);
        };

        Consumer<List<User>> displayer = users -> users.forEach(System.out::println);


        CompletableFuture<List<Long>> completableFuture = CompletableFuture.supplyAsync(supplyIDs);
        CompletableFuture<List<User>> userFuture = completableFuture.thenCompose(fetchUsers);
        CompletableFuture<List<Email>> emailFuture = completableFuture.thenCompose(fetchEmails);

        userFuture.thenAcceptBoth(
                emailFuture,
                ((users, emails) -> {
                    System.out.println(users.size() + " - " + emails.size());
                }));

        sleep(1_000);
        executor1.shutdown();
    }

    private static void sleep(int timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {

        }
    }
}
