package com.learnjava.completableFuture;

import com.learnjava.domain.Product;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;
import static org.junit.jupiter.api.Assertions.*;

class ProductServiceUsingCompletableFutureTest {

    private ProductInfoService pis;
    private ReviewService rs;
    ProductServiceUsingCompletableFuture psucf;


    @BeforeEach
    void setUp() {
        pis = new ProductInfoService();
        rs = new ReviewService();
        psucf = new ProductServiceUsingCompletableFuture(pis, rs);
    }

    @Test
    void retrieveProductDetails() {
        // given
        String productId = "ABC123";

        // when
        Product product = psucf.retrieveProductDetails(productId);

        // then
        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        assertNotNull(product.getReview());
    }

    @Test
    void retrieveProductDetails_approach2() {
        // given
        startTimer();
        String productId = "ABC123";

        // when
        CompletableFuture<Product> result = psucf.retrieveProductDetails_approach2(productId);
        result.thenAccept((product -> {
                    assertNotNull(product);
                    assertTrue(product.getProductInfo().getProductOptions().size() > 0);
                    assertNotNull(product.getReview());
                }))
                .join();

        // then
        timeTaken();
    }
}