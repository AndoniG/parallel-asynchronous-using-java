package com.learnjava.completableFuture;

import com.learnjava.service.HelloWorldService;

import java.util.concurrent.CompletableFuture;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;

public class CompletableFutureHelloWorld {

    private HelloWorldService hws;

    public CompletableFutureHelloWorld(HelloWorldService hws){
        this.hws = hws;
    }

    public CompletableFuture<String> helloWorld() {
        return CompletableFuture.supplyAsync(hws::helloWorld)
                .thenApply(String::toUpperCase);
    }

    public CompletableFuture<String> helloWorld_withSize(){
        return CompletableFuture.supplyAsync(hws::helloWorld)
                .thenApply(String::toUpperCase)
                .thenApply(result -> result.length() + " - " + result);
    }

    public String helloWorld_approach1(){
        startTimer();

        String hello = hws.hello();
        String world = hws.world();

        timeTaken();
        return hello + world;
    }

    public String helloWorld_approach2(){
        startTimer();
       CompletableFuture<String> hello = CompletableFuture.supplyAsync(()-> hws.hello());
       CompletableFuture<String> world = CompletableFuture.supplyAsync(()-> hws.world());

       String helloWorld =  hello.thenCombine(world, (h,w) -> h+w)
               .thenApply(String::toUpperCase)
               .join();

       timeTaken();

       return helloWorld;
    }


    public static void main(String[] args) {

    }
}
