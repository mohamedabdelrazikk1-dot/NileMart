package org.route.Controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.route.CheckOut.Order;
import org.route.CheckOut.OrderRegistry;
import org.route.common.NotFoundException;
import org.route.config.Check;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderRegistry orderRegistry;

    @GetMapping("/orders")
    public String getAllOrders(Model model){

       List<Order> orders = orderRegistry.findAll();
        BigDecimal revenue = orderRegistry.revenue();
       model.addAttribute("orders",orders);
       model.addAttribute("revenue",revenue);
return  "orders/list";
    }
    @GetMapping("/orders/{number}")
    public String getOrder(@PathVariable("number") String number, Model model){
  Order order=      Check.found(
                orderRegistry.findByOrderNumber(number),
                ()-> new NotFoundException("Order " + number + " not found.")
        );
        model.addAttribute("order",order);

        return "orders/confirmation";
    }
}
