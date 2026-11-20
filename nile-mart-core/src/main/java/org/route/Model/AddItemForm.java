package org.route.Model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter @Setter
public class AddItemForm {


    private Long cartNumber;

  private    String sku;
  private  Integer quantity;
}
