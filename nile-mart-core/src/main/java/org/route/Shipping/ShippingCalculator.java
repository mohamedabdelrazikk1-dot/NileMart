package org.route.Shipping;

import org.route.Model.Governorate;

import java.math.BigDecimal;

public interface ShippingCalculator {

    public BigDecimal calculate(Governorate governorate);
}
