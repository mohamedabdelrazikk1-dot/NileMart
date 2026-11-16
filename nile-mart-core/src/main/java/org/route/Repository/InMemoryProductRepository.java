package org.route.Repository;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.route.Model.CategoryEnum;
import org.route.Model.Product;
import org.route.Model.Product.ProductBuilder;
import org.route.common.StoreException;
import org.route.config.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
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
      Optional<Product>    p = Optional.ofNullable(store.get(s));
        System.out.println(p);
        return p;

    }


    @Override
    public Product save (Product entity) {
        store.put(entity.getSku(), entity);
        return entity;
    }
    @PostConstruct
    public void loadSeedProducts()  {


            InputStream is = InMemoryProductRepository.class.getClassLoader().getResourceAsStream("products.csv");


        if (is == null) {
            throw new StoreException("The Catalog cannot by loaded"  );
        }

        try (BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(is))) {
            String header = bufferedReader.readLine();
            String row;
            while ((row = bufferedReader.readLine()) != null) {
                String[] fields = row.split(",");
                Product product =
                        Product.builder().
                        sku(fields[0])
                        .name(fields[1]).
                        price(new BigDecimal(fields[3]))
                        .category(CategoryEnum.valueOf(fields[2]))
                        .stockQuantity(Integer.parseInt(fields[4]))
                        .build();

                store.put(product.getSku(), product);

                System.out.println("Catalog loaded: " + store.size() + "Products");
            }

        }
        catch (IOException e)
            {
                throw new StoreException("The Catalog cannot by loaded" ,e );
            }

    }
    @PreDestroy
    public void closeCatalog() {
        System.out.println("Catalog closed");
    }
}
