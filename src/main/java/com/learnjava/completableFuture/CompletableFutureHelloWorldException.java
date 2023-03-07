package com.learnjava.completableFuture;

import com.learnjava.service.HelloWorldService;

import java.util.concurrent.CompletableFuture;

import static com.learnjava.util.CommonUtil.*;
import static com.learnjava.util.LoggerUtil.log;

public class CompletableFutureHelloWorldException {
    private HelloWorldService hws;

    public CompletableFutureHelloWorldException() {
        this.hws = hws;
    }

    public String helloWorldMultipleAsync() {
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(hws::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(hws::world);
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi CompletableFuture!";
        });

        String helloWorld = hello
                // handle allows catching the exception and recover. Handle is always executed
                .handle((res, e) -> {
                    if (e != null) {
                        log("Exception is : " + e.getMessage());
                        return ""; // Recover value

                    } else return res;
                })
                .thenCombine(world, (h, w) -> h + w)
                .handle((res, e) -> {
                    if (e != null) {
                        log("Exception after world is : " + e.getMessage());
                        return ""; // Recover value

                    } else return res;
                })
                .thenCombine(hiCompletableFuture, (previous, current) -> previous + current)
                .thenApply(String::toUpperCase)
                .join();

        timeTaken();

        return helloWorld;
    }

    public String helloWorldMultipleAsync_withExceptionally() {
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(hws::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(hws::world);
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi CompletableFuture!";
        });

        String helloWorld = hello
                // handle allows catching the exception and recover. Handle is always executed
                .exceptionally((e) -> {
                    log("Exception is : " + e.getMessage());
                    return ""; // Recover value

                })
                .thenCombine(world, (h, w) -> h + w)
                .exceptionally((e) -> {
                    log("Exception after world is : " + e.getMessage());
                    return ""; // Recover value
                })
                .thenCombine(hiCompletableFuture, (previous, current) -> previous + current)
                .thenApply(String::toUpperCase)
                .join();

        timeTaken();

        return helloWorld;
    }

    public String helloWorldMultipleAsync_withWhenComplete() {
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(hws::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(hws::world);
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi CompletableFuture!";
        });

        String helloWorld = hello
                // handle allows catching the exception and recover. Handle is always executed
                .whenComplete((res, e) -> {
                    if (e != null)
                        log("Exception is : " + e.getMessage());
                })
                .thenCombine(world, (h, w) -> h + w)
                .whenComplete((res, e) -> {
                    if (e != null)
                        log("Exception after world is : " + e.getMessage());
                })
                .exceptionally((e) -> {
                    log("Exception after world is : " + e.getMessage());
                    return ""; // Recover value
                })
                .thenCombine(hiCompletableFuture, (previous, current) -> previous + current)
                .thenApply(String::toUpperCase)
                .join();

        timeTaken();

        return helloWorld;
    }
}
