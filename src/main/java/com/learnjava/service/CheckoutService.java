package com.learnjava.service;

import com.learnjava.domain.checkout.Cart;
import com.learnjava.domain.checkout.CartItem;
import com.learnjava.domain.checkout.CheckoutResponse;
import com.learnjava.domain.checkout.CheckoutStatus;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;
import static com.learnjava.util.LoggerUtil.log;
import static java.util.stream.Collectors.summingDouble;

public class CheckoutService {

    private PriceValidatorService priceValidatorService;

    public CheckoutService(PriceValidatorService priceValidatorService) {
        this.priceValidatorService = priceValidatorService;
    }

    public CheckoutResponse checkout(Cart cart, boolean isParallel) {
        startTimer();

        Stream<CartItem> priceValidationStream = cart.getCartItemList().stream();

        if (isParallel)
            priceValidationStream.parallel();

        List<CartItem> priceValidationList = priceValidationStream.map(cartItem -> {
                    boolean isPriceInvalid = priceValidatorService.isCartItemInvalid(cartItem);
                    cartItem.setExpired(isPriceInvalid);
                    return cartItem;
                })
                .filter(CartItem::isExpired)
                .collect(Collectors.toList());

        timeTaken();

        if (priceValidationList.size() > 0) {
            return new CheckoutResponse(CheckoutStatus.FAILURE, priceValidationList);
        }

        double finalPrice = calculateFinalPriceWithReduce(cart);
        log("Checkout Complete and the final price is " + finalPrice);

        return new CheckoutResponse(CheckoutStatus.SUCCESS, finalPrice);
    }

    private double calculateFinalPrice(Cart cart) {
        return cart.getCartItemList()
                .parallelStream()
                .map(cartItem -> cartItem.getQuantity() * cartItem.getRate())
                .collect(summingDouble(Double::doubleValue));
    }

    private double calculateFinalPriceWithReduce(Cart cart) {
        return cart.getCartItemList()
                .parallelStream()
                .map(cartItem -> cartItem.getQuantity() * cartItem.getRate())
                .reduce(0.0, Double::sum);
                //.reduce(0.0, (x, y) -> x + y);
    }
}
