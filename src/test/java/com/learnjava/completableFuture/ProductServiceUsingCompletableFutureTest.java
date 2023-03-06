package com.learnjava.completableFuture;

import com.learnjava.domain.Product;
import com.learnjava.service.InventoryService;
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
    private InventoryService is;
    ProductServiceUsingCompletableFuture psucf;


    @BeforeEach
    void setUp() {
        pis = new ProductInfoService();
        rs = new ReviewService();
        is = new InventoryService();
        psucf = new ProductServiceUsingCompletableFuture(pis, rs, is);
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

    @Test
    void retrieveProductDetailsWithInventory() {
        // given
        String productId = "ABC123";

        // when
        Product product = psucf.retrieveProductDetailsWithInventory(productId);

        // then
        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        product.getProductInfo().getProductOptions()
                        .forEach(productOption -> {
                            assertNotNull(productOption.getInventory());
                        });
        assertNotNull(product.getReview());
    }

    @Test
    void retrieveProductDetailsWithInventory_approach2() {
        // given
        String productId = "ABC123";

        // when
        Product product = psucf.retrieveProductDetailsWithInventory_approach2(productId);

        // then
        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        product.getProductInfo().getProductOptions()
                .forEach(productOption -> {
                    assertNotNull(productOption.getInventory());
                });
        assertNotNull(product.getReview());
    }
}