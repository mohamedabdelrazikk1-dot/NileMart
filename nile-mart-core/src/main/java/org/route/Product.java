package org.route;

import lombok.*;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Product {
    private String name;
    @EqualsAndHashCode.Include
    private String sku;
    private BigDecimal price;
    private CategoryEnum category;
    @Setter
    private int stockQuantity;
    private boolean isActive;
   }
