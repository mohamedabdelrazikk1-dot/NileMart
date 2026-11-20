package org.route.Controller;

import lombok.RequiredArgsConstructor;
import org.route.Cart.CartRegistry;
import org.route.Cart.CartView;
import org.route.Cart.ShoppingCart;
import org.route.CheckOut.Order;
import org.route.CheckOut.Quote;
import org.route.Model.Governorate;
import org.route.Service.CheckOutService;
import org.route.common.StoreException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class CheckOutController {
    private final CheckOutService checkOutService;
    private final CartRegistry cartRegistry;
    @GetMapping("/carts/{number}/checkout")
    public String checkout(Model model, @PathVariable("number") Long number, @RequestParam(required = false,name = "governorate") Governorate governorate) {

        CartView cart= cartRegistry.getCart(number).view();
        ShoppingCart shoppingCart= cartRegistry.getCart(number);
        Governorate [] governorates= Governorate.values();
        Quote quote = governorate == null
                ? null
                : checkOutService.quote(shoppingCart, governorate);

        model.addAttribute("governorates", governorates);
        model.addAttribute("cart", cart);
        model.addAttribute("selected", governorate);
        model.addAttribute("quote", quote);

        return "checkout/form";
    }
    @PostMapping("/carts/{number}/checkout")
    public String placeOrder(@PathVariable("number") long number ,@RequestParam(required = false,name = "governorate") Governorate governorate, RedirectAttributes redirectAttributes){

        if (governorate == null) {
            throw new StoreException("Chose a Governorate : ");
        }
        ShoppingCart shoppingCart= cartRegistry.getCart(number);
        Order order =checkOutService.placeOrder(shoppingCart,governorate);
        redirectAttributes.addFlashAttribute(
                "message",
                "Order "+ order.orderNumber() +" confirmed"
        );
        return "redirect:/orders/"+order.orderNumber();

    }
}
