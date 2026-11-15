package org.route;

import org.route.Pricing.PromotionalPricingService;
import org.route.Pricing.StandardPricingService;
import org.route.Repository.InMemoryProductRepository;
import org.route.Service.ProductService;

import java.math.BigDecimal;

public class ObjectFactory {

    public static ProductService createProductServiceObject() {
        return new ProductService(new InMemoryProductRepository(),
                new StandardPricingService());
    }
    public static ProductService createProductServiceObjectWithPromotionalPrice() {
        return new ProductService(new InMemoryProductRepository(),
                new PromotionalPricingService(BigDecimal.valueOf(10)));
    }
}
