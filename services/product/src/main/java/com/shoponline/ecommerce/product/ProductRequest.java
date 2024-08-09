package com.shoponline.ecommerce.product;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRequest(
    Integer id,
    @NotNull(message = "Product name is required")
    String name,
    @NotNull(message = "Product description is required")
    String description,
    @NotNull(message = "Available Quantity should bu positive")
    double availableQuantity,
    @NotNull(message = "Price Quantity should bu positive")
    BigDecimal price,
    @NotNull(message = "Product category is required")
    Integer categoryId
) {
}
