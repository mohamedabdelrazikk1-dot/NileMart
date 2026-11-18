package org.route.Service;

import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.route.Cart.ShoppingCart;
import org.route.CheckOut.*;
import org.route.Model.Governorate;
import org.route.Model.Product;
import org.route.Notification.NotificationSender;
import org.route.Shipping.ShippingQuoteService;
import org.route.common.OutOFStockException;
import org.route.common.StoreException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckOutService {
    private final ProductService productService;
    private final ShippingQuoteService shippingQuoteService;
    private final TaxCalculator taxCalculator;
    private final OrderRegistry orderRegistry;
    private final List<NotificationSender> notificationSenders;


    public Quote quote(ShoppingCart cart, Governorate g) {
        if (cart == null) {
            throw new StoreException("cart is null");
        }

        return new Quote(
                cart.subTotal(),
                taxCalculator.getVatRate(cart.subTotal()),
                shippingQuoteService.quote(g, cart.subTotal()),
                cart.subTotal().add(taxCalculator.getVatRate(cart.subTotal())).add(shippingQuoteService.quote(g, cart.subTotal()))

        );

    }
@Synchronized
    public  Order placeOrder(ShoppingCart cart, Governorate g) {

        if (cart == null ||cart.isEmpty()) {
            throw new StoreException("cart is null");
        }

        cart.lines().entrySet()
                .forEach(entry -> {
                    if(entry.getValue() > productService.findBySku(entry.getKey()).getStockQuantity())
                        throw new OutOFStockException("Out of Stock");
                });
    Quote q=    quote(cart, g);
        cart.lines().
                entrySet().
                forEach(entry -> {
                    productService.reduceStock(entry.getKey(), entry.getValue());
                });

       Order o= Order.builder()
               .orderNumber(orderRegistry.nextNumber())
               .shippingFee(q.shippingFee())
               .total(q.total())
               .subTotal(q.subtotal())
               .vat(q.vat())
               .governorate(g)
               .customer(cart.getCustomer())
               .lines(
                       cart.view()
                               .lines()
                               .stream()
                               .map(lineView -> {
                                return    new OrderLine(
                                           lineView.sku(),
                                           lineView.name(),
                                           lineView.quantity(),
                                           lineView.linePrice()
                                   );
                               }
                               ).toList()

               )
               .build();

       orderRegistry.save(o);
       notificationSenders
               .forEach(notificationSender -> {
                   notificationSender.send(cart.getCustomer(),"Order:" + o.orderNumber() + " is created");
               });

cart.Clear();
return o;

    }

}
