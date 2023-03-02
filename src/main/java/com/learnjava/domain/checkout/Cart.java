package com.learnjava.domain.checkout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data // Generating Getter, Setters, toString
@NoArgsConstructor // Constructor without arguments
@AllArgsConstructor // Constructor with all arguments
// Allows to create the object like this: ProductInfo.builder().productId(productId).productOptions(productOptions).build();
@Builder
public class Cart {

    private Integer cardId;
    private List<CartItem> cartItemList;

}
