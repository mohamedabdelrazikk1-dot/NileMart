package org.route;

import org.route.common.Check;
import org.route.common.NotFoundException;
import org.route.common.OutOFStockException;
import org.route.common.StoreException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final PricingService pricingService;

    public ProductService(@Qualifier("inMemoryProductRepository") ProductRepository repository,
                          PricingService pricingService) {
        this.repository = repository;
        this.pricingService = pricingService;
    }

    public BigDecimal getPrice(String sku, int qty) throws StoreException {
        Product product = findBySku(sku);
        return pricingService.getPrice(product, qty);
    }

    public List<Product> findAll(){

        return repository.findAll()
                .stream()
                .sorted(Comparator.comparing(Product::getCategory)
                        .thenComparing(Product::getName)).
                toList();
    }

    public  Product findBySku(String sku){
      return   Check.found(
             repository.findBySku(sku),
             () -> new NotFoundException("Product " + sku +" not found.")
     );

    }
    public void reduceStock(String sku, int qty) throws OutOFStockException {

        Product product = this.findBySku(sku);
        if(qty<=0){
            throw new StoreException("quantity must be greater than 0.");
        }
        if(product.getStockQuantity()<qty){
            throw new OutOFStockException("Product " + sku +" is out of stock.");
        }
        product.setStockQuantity(product.getStockQuantity()-qty);
        repository.save(product);
    }

    public List<Product> search (Predicate<Product> filter){
        return repository.findAll().
                stream().
                filter(filter).
                toList();
    }
    public Map<CategoryEnum, List<Product>> groupByCategory() {
        return repository.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        () -> new EnumMap<>(CategoryEnum.class),
                        Collectors.toList()
                ));
    }

}
