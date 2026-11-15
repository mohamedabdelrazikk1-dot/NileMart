package org.route.Repository;

import org.route.Model.Product;

import java.util.Optional;
@org.springframework.stereotype.Repository
public interface ProductRepository extends Repository<Product,String> {
    default Optional<Product> findBySku(String sku){
      return   findById(sku);
    }
}
