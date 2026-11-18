package org.route.Model;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.stream.Collectors;

@Getter
public enum Zone {

    GREATER_CAIRO(new BigDecimal(50),true),
    ALEX_DELTA(new BigDecimal(65),true),
    CANAL(new BigDecimal(70),true),
    UPPER_EGYPT(new BigDecimal(85),true),
    REMOTE(new BigDecimal(120),false);


    private final BigDecimal fee;
    private final boolean freeShipping;


    Zone(BigDecimal fee, boolean freeShipping) {
        this.fee = fee;
        this.freeShipping = freeShipping;
    }
 public EnumSet<Governorate> allGovernments(){
  return   Arrays.stream(Governorate.values()).filter(Governorate->
          this==Governorate.getZone()).collect(Collectors.toCollection(
          ()->EnumSet.noneOf(Governorate.class)
  ));

 }
}
