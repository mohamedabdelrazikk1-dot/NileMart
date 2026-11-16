package org.route.Cart;

import org.route.Model.Customer;

import java.math.BigDecimal;
import java.util.List;

public record CartView(
        long number,
        Customer customer,
        List<LineView> lines,
        int itemCount,
        BigDecimal subtotal,
        boolean empty
) {

}