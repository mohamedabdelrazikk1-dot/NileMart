package org.route.Shipping;

import org.route.Model.Governorate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ShippingQuoteService
{
    private final ShippingCalculator zoneShippingCalculator;
    private final ShippingCalculator freeShippingCalculator;
    private final BigDecimal threshold;


    public ShippingQuoteService(ZoneShippingCalculator zoneShippingCalculator, @Qualifier("freeShipping")  ShippingCalculator freeShippingCalculator, @Value("${store.free-shipping-threshold}") BigDecimal threshold){
        this.zoneShippingCalculator = zoneShippingCalculator;

        this.freeShippingCalculator = freeShippingCalculator;
        this.threshold = threshold;
    }
    public BigDecimal quote(Governorate g, BigDecimal subTotal){
        if(subTotal.compareTo(threshold) >= 0&&g.getZone().isFreeShipping()){
           return freeShippingCalculator.calculate(g);
        }
        else{
         return    zoneShippingCalculator.calculate(g);
        }
    }

}
