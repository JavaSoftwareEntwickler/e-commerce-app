package com.shoponline.ecommerce.orderline;


public record OrderLineResponse(
        Integer id,
        Integer productId,
        double quantity
) {

}
