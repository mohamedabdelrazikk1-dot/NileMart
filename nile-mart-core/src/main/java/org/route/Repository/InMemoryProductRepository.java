package org.route.Repository;

import org.route.Model.CategoryEnum;
import org.route.Model.Product;
import org.route.config.Money;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryProductRepository implements ProductRepository{

    private final Map<String, Product> store = new ConcurrentHashMap<>();

    @Override
    public List<Product> findAll() {
            return List.copyOf(store.values());
    }

    @Override
    public Optional<Product> findById(String s) {
        return Optional.ofNullable(store.get(s));
    }


    @Override
    public Product save (Product entity) {
        store.put(entity.getSku(), entity);
        return entity;
    }
    public void loadSeedProducts() {

        save(Product.builder()
                .sku("ACC-CHG-20W")
                .name("USB-C Charger 20W")
                .category(CategoryEnum.ELECTRONICS)
                .price(Money.of("350.00"))
                .stockQuantity(15)
                .build());

        save(Product.builder()
                .sku("ACC-USB-C")
                .name("USB-C Cable 1m")
                .category(CategoryEnum.ELECTRONICS)
                .price(Money.of("120.00"))
                .stockQuantity(30)
                .build());

        save(Product.builder()
                .sku("FAS-T01")
                .name("Cotton T-Shirt")
                .category(CategoryEnum.FASHION)
                .price(Money.of("250.00"))
                .stockQuantity(20)
                .build());

        save(Product.builder()
                .sku("FAS-J01")
                .name("Denim Jeans")
                .category(CategoryEnum.FASHION)
                .price(Money.of("650.00"))
                .stockQuantity(8)
                .build());

        save(Product.builder()
                .sku("HOM-P01")
                .name("Non-stick Frying Pan 28cm")
                .category(CategoryEnum.HOME_KITCHEN)
                .price(Money.of("550.00"))
                .stockQuantity(3)
                .build());

        save(Product.builder()
                .sku("BEA-S50")
                .name("Sun Cream SPF50")
                .category(CategoryEnum.BEAUTY)
                .price(Money.of("300.00"))
                .stockQuantity(0)
                .build());

        save(Product.builder()
                .sku("BOO-J01")
                .name("Clean Code")
                .category(CategoryEnum.BOOKS)
                .price(Money.of("750.00"))
                .stockQuantity(12)
                .build());

        save(Product.builder()
                .sku("HOM-M01")
                .name("Ceramic Coffee Mug")
                .category(CategoryEnum.HOME_KITCHEN)
                .price(Money.of("150.00"))
                .stockQuantity(25)
                .build());
    }
}
