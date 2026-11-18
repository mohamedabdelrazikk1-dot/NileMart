package org.route.config;


import org.route.Pricing.PromotionalPricingService;
import org.route.Pricing.StandardPricingService;
import org.route.Repository.InMemoryProductRepository;
import org.route.Service.ProductService;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.math.BigDecimal;
import java.time.Clock;


@Configuration
@ComponentScan(basePackages = "org.route")
//@ImportResource("/beans.xml")
@PropertySource("/application.properties")
@EnableAspectJAutoProxy
public class AppConfig {

    @Bean
  static   PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    @Scope("prototype")
    ProductService productService(){
        return new ProductService(inMemoryProductRepository(), standardPricingService());
    }

    @Bean
    InMemoryProductRepository inMemoryProductRepository() {
        return new InMemoryProductRepository();
    }

    @Bean
    StandardPricingService standardPricingService() {
        return new StandardPricingService();
    }

    @Bean
    PromotionalPricingService promotionalPricingService() {
        return new PromotionalPricingService(BigDecimal.valueOf(10));
    }

}
