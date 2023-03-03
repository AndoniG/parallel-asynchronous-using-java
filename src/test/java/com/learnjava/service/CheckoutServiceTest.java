package com.learnjava.service;

import com.learnjava.domain.checkout.Cart;
import com.learnjava.domain.checkout.CheckoutResponse;
import com.learnjava.domain.checkout.CheckoutStatus;
import com.learnjava.util.DataSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.concurrent.ForkJoinPool;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutServiceTest {

    PriceValidatorService priceValidatorService;
    CheckoutService checkoutService;

    @BeforeEach
    void setUp() {
        priceValidatorService = new PriceValidatorService();
        checkoutService = new CheckoutService(priceValidatorService);
    }

    @Test
    void checkout_6_items() {
        // given
        Cart cart = DataSet.createCart(6);

        // when
        CheckoutResponse checkoutResponse = checkoutService.checkout(cart, false);

        // then
        assertEquals(CheckoutStatus.SUCCESS, checkoutResponse.getCheckoutStatus());
        assertTrue(checkoutResponse.getFinalRate() > 0);
    }

    @Test
    void checkout_6_items_in_parallel() {
        // given
        Cart cart = DataSet.createCart(6);

        // when
        CheckoutResponse checkoutResponse = checkoutService.checkout(cart, true);

        // then
        assertEquals(CheckoutStatus.SUCCESS, checkoutResponse.getCheckoutStatus());
    }

    @Test
    void checkout_13_items_in_parallel() {
        // given
        Cart cart = DataSet.createCart(13);

        // when
        CheckoutResponse checkoutResponse = checkoutService.checkout(cart, true);

        // then
        assertEquals(CheckoutStatus.SUCCESS, checkoutResponse.getCheckoutStatus());
    }

    @ParameterizedTest
    @ValueSource(ints = {13, 25, 36})
    void checkout_13_and_25_items_in_parallel(int cartSize) {
        // given
        Cart cart = DataSet.createCart(cartSize);

        // when
        CheckoutResponse checkoutResponse = checkoutService.checkout(cart, true);

        // then
        assertEquals(CheckoutStatus.FAILURE, checkoutResponse.getCheckoutStatus());
    }

    @Test
    void no_of_cores() {
        // given

        // when
        System.out.println("no of cores: " + Runtime.getRuntime().availableProcessors());

        // then
    }

    @Test
    void parallelism() {
        // given

        // when
        System.out.println("no of cores: " + ForkJoinPool.getCommonPoolParallelism());

        // then
    }
}