package org.route.CheckOut;

import jakarta.annotation.PreDestroy;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.aspectj.runtime.internal.CFlowCounter;
import org.route.Service.CheckOutService;
import org.route.Service.ProductService;
import org.route.common.NotFoundException;
import org.route.config.Check;
import org.route.config.Money;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Component

public class OrderRegistry {
 private final  java.time.Clock clock ;
 private  final AtomicInteger counter = new AtomicInteger(0);
 private final List<Order> orders=new CopyOnWriteArrayList<>();
 public OrderRegistry(ProductService productService, java.time.Clock clock) {

     this.clock = clock ;
 }

 public String nextNumber(){
   return   String.format("NM-%s-%05d",
             LocalDateTime.now(clock).format(DateTimeFormatter.BASIC_ISO_DATE),
             counter.incrementAndGet());
 }
 public void save(Order order){

     orders.add(order);
 }
 public    Optional<Order> findByOrderNumber(String orderNumber) {
        return orders.stream()
                .filter(order -> order.orderNumber().equals(orderNumber))
                .findFirst();
    }
public List<Order> findAll(){
     return  orders.reversed();
}
 public BigDecimal revenue(){
 return     orders.stream()
             .map(Order::total)
             .reduce(BigDecimal.ZERO,BigDecimal::add);
 }
 @PreDestroy
    public void preDestroy(){
     System.out.println("End of day :  " + orders.size() +"  orders" + "  revenue:    "+ Money.format(revenue()));
 }



}
