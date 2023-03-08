package com.learnjava.completableFuture;

import com.learnjava.service.HelloWorldService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;
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
        CompletableFuture<String> completableFuture = cfhw.helloWorld();

        //then
        completableFuture
                .thenAccept(result -> {
                    assertEquals("HELLO WORLD", result);
                }).join();
    }


    @Test
    void helloWorld_withSize() {
        // given

        // when
        CompletableFuture<String> completableFuture = cfhw.helloWorld_withSize();

        // then
        completableFuture.thenAccept(result -> {
            assertEquals("11 - HELLO WORLD", result);
        }).join();
    }

    @Test
    void helloWorld_approach1() {
        // given

        // when
        String result = cfhw.helloWorld_approach1();

        // then
        assertEquals("hello world!", result);
    }

    @Test
    void helloWorld_approach2() {
        // given

        // when
        String result = cfhw.helloWorld_approach2();

        // then
        assertEquals("HELLO WORLD!", result);
    }

    @Test
    void helloWorldMultipleAsync() {
        // given

        // when
        String result = cfhw.helloWorldMultipleAsync();

        // then
        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE!", result);
    }

    @Test
    void helloWorld_4_async_calls() {
        // given

        // when
        String result = cfhw.helloWorld_4_async_calls();

        // then
        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE! BYE!", result);
    }

    @Test
    void helloWorld_thenCompose() {
        // given
        startTimer();

        // when
        CompletableFuture<String> completableFuture = cfhw.helloWorld_thenCompose();

        // then
        completableFuture.thenAccept(result -> {
            assertEquals("hello world!", result);
        }).join();

        timeTaken();
    }

    @Test
    void helloWorldMultipleAsync_customThreadPool() {
        // given

        // when
        String result = cfhw.helloWorldMultipleAsync_customThreadPool();

        // then
        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE!", result);
    }

    @Test
    void helloWorldMultipleAsyncCalls_async() {
        // given

        // when
        String result = cfhw.helloWorldMultipleAsyncCalls_async();

        // then
        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE!", result);
    }

    @Test
    void anyOf() {
        // given

        // when
        String result = cfhw.anyOf();

        // then
        assertEquals("Hello World", result);
    }
}