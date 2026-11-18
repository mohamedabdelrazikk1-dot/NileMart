package org.route.CheckOut;

import org.route.config.Money;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TaxCalculator {
    @Value("${store.vat-rate}")
    private BigDecimal vatRate;

 public BigDecimal getVatRate(BigDecimal amount) {
     return amount.multiply(vatRate);
 }

    }
