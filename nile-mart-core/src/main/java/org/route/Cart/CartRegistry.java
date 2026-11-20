package org.route.Cart;

import org.route.Model.Customer;
import org.route.common.NotFoundException;
import org.route.config.Check;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class CartRegistry {
    private  final ObjectProvider<ShoppingCart> shoppingCartProvider;
  private final  Map<Long, ShoppingCart> carts = new ConcurrentHashMap<>();
  private final AtomicLong counter = new AtomicLong();

    public CartRegistry(ObjectProvider<ShoppingCart> shoppingCartProvider) {
        this.shoppingCartProvider = shoppingCartProvider;
    }

    public ShoppingCart open(Customer customer){
        ShoppingCart shoppingCart = shoppingCartProvider.getObject();
        long number = counter.incrementAndGet();
         shoppingCart.open(number,customer);
         carts.put(number,shoppingCart);
         System.out.println("Shopping cart opened with number " + number);

return shoppingCart;
    }
    public List<CartView> view(){
     return carts.values()
             .stream()
             .map(ShoppingCart::view)
             .sorted((a,b)-> Long.compare(a.number(),b.number()))
             .toList();
    }
    public ShoppingCart getCart(Long cartId){
       return Check.found(
               Optional.ofNullable(carts.get(cartId)),
               ()-> new NotFoundException(
                       "ShoppingCart not found with id: "+cartId
        )

       );
    }
}
