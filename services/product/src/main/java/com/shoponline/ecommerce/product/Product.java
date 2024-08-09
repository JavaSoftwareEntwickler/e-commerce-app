package com.shoponline.ecommerce.product;

import com.shoponline.ecommerce.category.Category;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Product {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String description;

    @Column(name = "available_quantity")
    private double availableQuantity;

    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;




}
