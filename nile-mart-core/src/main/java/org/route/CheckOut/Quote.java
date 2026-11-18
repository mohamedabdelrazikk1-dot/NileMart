package org.route.CheckOut;

import java.math.BigDecimal;

public record Quote(
        BigDecimal subtotal,
        BigDecimal vat,
        BigDecimal shippingFee,
        BigDecimal total
) {}
