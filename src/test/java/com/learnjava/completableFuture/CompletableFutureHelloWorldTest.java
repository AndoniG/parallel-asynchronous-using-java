package com.learnjava.completableFuture;

import com.learnjava.service.HelloWorldService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

class CompletableFutureHelloWorldTest {

    HelloWorldService hws;
    CompletableFutureHelloWorld cfhw;

    @BeforeEach
    void setUp() {
        hws = new HelloWorldService();
        cfhw = new CompletableFutureHelloWorld(hws);
    }

    @Test
    void helloWorld() {
        // given

        // when
        CompletableFuture<String> completableFuture =  cfhw.helloWorld();

        //then
        completableFuture
                .thenAccept(result -> {
                    assertEquals("HELLO WORLD", result);
                }).join();
    }


    @Test
    void helloWorld_withSize(){
        // given

        // when
        CompletableFuture<String> completableFuture = cfhw.helloWorld_withSize();

        // then
        completableFuture.thenAccept(result -> {
            assertEquals("11 - HELLO WORLD", result);
        }).join();
    }
}