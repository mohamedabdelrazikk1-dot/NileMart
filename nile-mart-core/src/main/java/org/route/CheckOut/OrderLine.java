package org.route.CheckOut;

import java.math.BigDecimal;

public record OrderLine(
         String sku,
         String name ,
        int quantity,
         BigDecimal linePrice
)
{}




