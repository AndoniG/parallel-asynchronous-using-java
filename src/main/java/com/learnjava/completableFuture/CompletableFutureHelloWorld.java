package com.learnjava.completableFuture;

import com.learnjava.service.HelloWorldService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.learnjava.util.CommonUtil.*;
import static com.learnjava.util.LoggerUtil.log;

public class CompletableFutureHelloWorld {

    private HelloWorldService hws;

    public CompletableFutureHelloWorld(HelloWorldService hws) {
        this.hws = hws;
    }

    public CompletableFuture<String> helloWorld() {
        return CompletableFuture.supplyAsync(hws::helloWorld)
                .thenApply(String::toUpperCase);
    }

    public CompletableFuture<String> helloWorld_withSize() {
        return CompletableFuture.supplyAsync(hws::helloWorld)
                .thenApply(String::toUpperCase)
                .thenApply(result -> result.length() + " - " + result);
    }

    public String helloWorld_approach1() {
        startTimer();

        String hello = hws.hello();
        String world = hws.world();

        timeTaken();
        return hello + world;
    }

    public String helloWorld_approach2() {
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> hws.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> hws.world());

        String helloWorld = hello.thenCombine(world, (h, w) -> h + w)
                .thenApply(String::toUpperCase)
                .join();

        timeTaken();

        return helloWorld;
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
                .thenCombine(world, (h, w) -> {
                    log("thenCombine h/w");
                    return h + w;
                })
                .thenCombine(hiCompletableFuture, (previous, current) -> {
                    log("thenCombine previous/current");
                    return previous + current;
                })
                .thenApply(s -> {
                    log("thenApply");
                    return s.toUpperCase();

                })
                .join();

        timeTaken();

        return helloWorld;
    }

    public String helloWorld_4_async_calls() {
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(hws::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(hws::world);
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " HI CompletableFuture!";
        });
        // Add the 4th CompletableFuture that returns a String "  Bye!"
        CompletableFuture<String> bye = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Bye!";
        });

        String hw = hello
                .thenCombine(world, (h, w) -> h + w) // (first,second)
                .thenCombine(hiCompletableFuture, (previous, current) -> previous + current)
                .thenCombine(bye, (previous, current) -> previous + current)
                .thenApply(String::toUpperCase)
                .join();

        timeTaken();

        return hw;
    }

    public CompletableFuture<String> helloWorld_thenCompose(){
        return CompletableFuture.supplyAsync(hws::hello)
                .thenCompose(hws::worldFuture);
    }

    // This allows to keep the faster response
    public String anyOf(){
        // db
        CompletableFuture<String> db = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            log("Response from DB");
            return "Hello World";
        });

        // rest
        CompletableFuture<String> rest = CompletableFuture.supplyAsync(() -> {
            delay(800);
            log("Response from REST");
            return "Hello World";
        });


        // soap
        CompletableFuture<String> soap = CompletableFuture.supplyAsync(() -> {
            delay(3000);
            log("Response from SOAP");
            return "Hello World";
        });

        List<CompletableFuture<String>> cfList = List.of(db, rest, soap);

        CompletableFuture<Object> cfAnyOf = CompletableFuture.anyOf(cfList.toArray(new CompletableFuture[cfList.size()]));

        String result = (String) cfAnyOf.thenApply( v -> {
            if(v instanceof String) return v;
            return null;
        }).join();

        return result;
    }

    public String helloWorldMultipleAsync_customThreadPool() {
        startTimer();

        // User defined Thread pool using the Executor Service
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        CompletableFuture<String> hello = CompletableFuture.supplyAsync(hws::hello, executorService);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(hws::world, executorService);
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi CompletableFuture!";
        }, executorService);

        String helloWorld = hello
                .thenCombine(world, (h, w) -> {
                    log("thenCombine h/w");
                    return h + w;
                })
                .thenCombine(hiCompletableFuture, (previous, current) -> {
                        log("thenCombine previous/current");
                        return previous + current;
                })
                .thenApply(s -> {
                    log("thenApply");
                    return s.toUpperCase();

                })
                .join();

        timeTaken();

        return helloWorld;
    }


    public String helloWorldMultipleAsyncCalls_async() {
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(hws::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(hws::world);
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(5000);
            return " Hi CompletableFuture!";
        });

        String helloWorld = hello
                .thenCombineAsync(world, (h, w) -> {
                    log("thenCombine h/w");
                    return h + w;
                })
                .thenCombineAsync(hiCompletableFuture, (previous, current) -> {
                    log("thenCombine previous/current");
                    return previous + current;
                })
                .thenApplyAsync(s -> {
                    log("thenApply");
                    return s.toUpperCase();

                })
                .join();

        timeTaken();

        return helloWorld;
    }

    public static void main(String[] args) {

    }
}
