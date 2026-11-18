package org.route.CheckOut;

import lombok.Builder;
import org.route.Model.Customer;
import org.route.Model.Governorate;
import org.route.common.StoreException;
import org.route.config.Money;

import java.math.BigDecimal;
import java.util.List;
@Builder
public record Order(

        String orderNumber,
        Customer customer,
        Governorate governorate,
        List<OrderLine> lines,
        BigDecimal subTotal,
        BigDecimal vat,
        BigDecimal shippingFee,
        BigDecimal total

) {
    public Order ( String orderNumber,Customer customer, Governorate governorate ,List<OrderLine> lines ,BigDecimal subTotal ,BigDecimal vat,BigDecimal shippingFee ,BigDecimal total) {
        if (orderNumber == null) {
            throw new StoreException("Order number cannot be null");
        }
        this.orderNumber = orderNumber;
        this.customer = customer;
        this.governorate = governorate;
        this.lines= List.copyOf(lines);
        this.subTotal = Money.scale(subTotal);
        this.vat = Money.scale(vat);
        this.shippingFee = Money.scale(shippingFee);
        this.total = Money.scale(total);


    }
}
