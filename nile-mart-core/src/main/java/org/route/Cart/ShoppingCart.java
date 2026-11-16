package org.route.Cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.route.Model.Customer;
import org.route.Model.Product;
import org.route.Service.ProductService;
import org.route.common.OutOFStockException;
import org.route.common.StoreException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
@Scope("prototype")
@RequiredArgsConstructor
public class ShoppingCart {
    @Getter
    private  long cartNumber;
    @Getter
    private Customer customer;
    private final LinkedHashMap<String, Integer> lines = new LinkedHashMap<>();

    private final ProductService productService;
    private final int maxQuantity;
    public ShoppingCart(ProductService productService,@Value("${store.max-quantity-per-product}") int maxQuantity,long cartNumber) {
        this.productService = productService;
        this.maxQuantity = maxQuantity;
    }


    public void open(long number, Customer customer) {
        if(this.cartNumber !=0) {
            throw new IllegalStateException("Customer already open");
        }
        if(customer == null) {
            throw new StoreException("Customer cannot be null");
        }
        this.customer = customer;
        this.cartNumber=number;
    }
 public void addItem(String sku,  int quantity) {
        if(quantity < 1) {
            throw new StoreException("Quantity cannot be less than 1");
        }
    Product product =  productService.findBySku(sku);
       if(product.getStockQuantity()==0){
           throw new OutOFStockException("Stock Cannot Be Empty");
       }
       int newQuantity = lines.getOrDefault(sku,0)+quantity;
       if(newQuantity>maxQuantity) {
           throw new StoreException("Sorry , You can add at most  "+maxQuantity+" items of " + sku);
       }
       if(newQuantity>product.getStockQuantity()) {
           throw new OutOFStockException("Sorry , Only :" + product.getStockQuantity()+" items of " + sku);
       }
       lines.put(sku,newQuantity);
}

public BigDecimal subTotal(){
     return    lines.entrySet().
                stream().
                map(entry -> productService.getPrice(entry.getKey(), entry.getValue()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
}
public Map<String , Integer > lines(){
       return Collections.unmodifiableMap(lines);
}
public Boolean isEmpty(){
        return lines.isEmpty();
}
public void Clear(){
    lines.clear();
}
public CartView view(){
    List<LineView> lineViews =
            lines.entrySet()
                    .stream()
                    .map(
                          entry -> {
                              String sku = entry.getKey();
                              int  quantity = entry.getValue();
                              String name = productService.findBySku(sku).getName();
                              BigDecimal price = productService.getPrice(sku, quantity);
                       return new LineView(
                               sku,
                               name,
                               quantity,
                               price
                       );
                          }
                    ).toList();
     int itemCount= lines.values()
             .stream()
             .mapToInt(Integer::intValue)
             .sum();
     BigDecimal subtotal = subTotal();
     boolean empty = isEmpty();

return    new CartView(
           cartNumber,
           customer,
           List.copyOf(lineViews),
           itemCount,
           subtotal,
           empty
   );
}

}
