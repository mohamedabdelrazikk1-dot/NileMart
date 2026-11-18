package org.route.Model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
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
    @Override
    public String toString() {
        return "\nProduct{" +
                "\n  name='" + name + '\'' +
                "\n  sku='" + sku + '\'' +
                "\n  price=" + price +
                "\n  category=" + category +
                "\n  stockQuantity=" + stockQuantity +
                "\n  isActive=" + isActive +
                "\n}";
    }


   }
