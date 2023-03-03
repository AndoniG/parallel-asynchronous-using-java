package com.learnjava.completableFuture;

import com.learnjava.service.HelloWorldService;

import java.util.concurrent.CompletableFuture;
public class CompletableFutureHelloWorld {

    private HelloWorldService hws;

    public CompletableFutureHelloWorld(HelloWorldService hws){
        this.hws = hws;
    }

    public CompletableFuture<String> helloWorld() {
        return CompletableFuture.supplyAsync(hws::helloWorld)
                .thenApply(String::toUpperCase);
    }


    public static void main(String[] args) {

    }
}
