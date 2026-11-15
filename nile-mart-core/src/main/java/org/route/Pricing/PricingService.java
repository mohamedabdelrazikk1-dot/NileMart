package org.route.Pricing;

import org.route.Model.Product;

import java.math.BigDecimal;

public interface PricingService {

    BigDecimal getPrice(Product product, int qty);
}
