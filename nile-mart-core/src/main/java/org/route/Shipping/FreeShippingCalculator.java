package org.route.Shipping;

import org.route.Model.Governorate;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
@Component("freeShipping")
public class FreeShippingCalculator implements ShippingCalculator , BeanNameAware {
    @Override
    public void setBeanName(String name) {
        System.out.println("FreeShippingCalculator setBeanName: " + name);
    }

    @Override
    public BigDecimal calculate(Governorate governorate) {

        return BigDecimal.ZERO;
    }
}
