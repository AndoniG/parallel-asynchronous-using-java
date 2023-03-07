package com.learnjava.completableFuture;

import com.learnjava.service.HelloWorldService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompletableFutureHelloWorldExceptionTest {

    @Mock
    HelloWorldService helloWorldService = mock(HelloWorldService.class);

    @InjectMocks
    CompletableFutureHelloWorldException cfhwe;

    @Test
    void helloWorldMultipleAsync_handle_allOk() {
        // given
        when(helloWorldService.hello()).thenCallRealMethod();
        when(helloWorldService.world()).thenCallRealMethod();

        // when
        String result = cfhwe.helloWorldMultipleAsync();

        // then
        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE!", result);
    }

    @Test
    void helloWorldMultipleAsync_handle_oneException() {
        // given
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception Occurred"));
        when(helloWorldService.world()).thenCallRealMethod();

        // when
        String result = cfhwe.helloWorldMultipleAsync();

        // then
        assertEquals(" WORLD! HI COMPLETABLEFUTURE!", result);
    }

    @Test
    void helloWorldMultipleAsync_handle_twoExceptions() {
        // given
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception Occurred"));
        when(helloWorldService.world()).thenThrow(new RuntimeException("Exception Occurred"));

        // when
        String result = cfhwe.helloWorldMultipleAsync();

        // then
        assertEquals(" HI COMPLETABLEFUTURE!", result);
    }

    @Test
    void helloWorldMultipleAsync_exceptionally_allOk() {
        // given
        when(helloWorldService.hello()).thenCallRealMethod();
        when(helloWorldService.world()).thenCallRealMethod();

        // when
        String result = cfhwe.helloWorldMultipleAsync_withExceptionally();

        // then
        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE!", result);
    }

    @Test
    void helloWorldMultipleAsync_exceptionally_oneException() {
        // given
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception Occurred"));
        when(helloWorldService.world()).thenCallRealMethod();

        // when
        String result = cfhwe.helloWorldMultipleAsync_withExceptionally();

        // then
        assertEquals(" WORLD! HI COMPLETABLEFUTURE!", result);
    }

    @Test
    void helloWorldMultipleAsync_exceptionally_twoExceptions() {
        // given
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception Occurred"));
        when(helloWorldService.world()).thenThrow(new RuntimeException("Exception Occurred"));

        // when
        String result = cfhwe.helloWorldMultipleAsync_withExceptionally();

        // then
        assertEquals(" HI COMPLETABLEFUTURE!", result);
    }

    @Test
    void helloWorldMultipleAsync_whenComplete_allOk() {
        // given
        when(helloWorldService.hello()).thenCallRealMethod();
        when(helloWorldService.world()).thenCallRealMethod();

        // when
        String result = cfhwe.helloWorldMultipleAsync_withWhenComplete();

        // then
        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE!", result);
    }

    @Test
    void helloWorldMultipleAsync_whenComplete_oneException() {
        // given
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception Occurred"));
        when(helloWorldService.world()).thenCallRealMethod();

        // when
        String result = cfhwe.helloWorldMultipleAsync_withWhenComplete();

        // then
        assertEquals(" HI COMPLETABLEFUTURE!", result);
    }

    @Test
    void helloWorldMultipleAsync_whenComplete_twoExceptions() {
        // given
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception Occurred"));
        when(helloWorldService.world()).thenThrow(new RuntimeException("Exception Occurred"));

        // when
        String result = cfhwe.helloWorldMultipleAsync_withWhenComplete();

        // then
        assertEquals(" HI COMPLETABLEFUTURE!", result);
    }
}