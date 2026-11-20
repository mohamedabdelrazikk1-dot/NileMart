package org.route.Controller;

import lombok.RequiredArgsConstructor;
import org.route.Cart.CartRegistry;
import org.route.Cart.CartView;
import org.route.Cart.ShoppingCart;
import org.route.CheckOut.Order;
import org.route.CheckOut.Quote;
import org.route.Model.*;
import org.route.Service.CheckOutService;
import org.route.Service.ProductService;
import org.route.common.StoreException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.Validate;

import java.util.List;

@Controller
@RequiredArgsConstructor

public class CartController {
    private final CartRegistry cartRegistry;
    private final ProductService productService;
    private final CheckOutService checkOutService;
    @GetMapping("/carts")
    public String CartsList(Model model) {
        List<CartView> carts = cartRegistry.view();
        model.addAttribute("carts", carts);
        return "carts/list";
    }
    @GetMapping("/carts/new")
    public String newCart(Model model){
        NewCartForm form = new NewCartForm();
        model.addAttribute("form" , form);
        return "carts/new";
    }
    @PostMapping("/carts")
    public String addCart(@ModelAttribute ("form") NewCartForm newCartForm, RedirectAttributes redirectAttributes){

        Customer customer = new Customer(
                newCartForm.getFullName(),
                newCartForm.getPhone(),
                newCartForm.getEmail()
        );
        ShoppingCart shoppingCart= cartRegistry.open(customer);
        redirectAttributes.addFlashAttribute("message",
                "cart" + shoppingCart.getCartNumber() +" created "+ " for  " +  newCartForm.getFullName()
        );
        return "redirect:/carts/"+shoppingCart.getCartNumber();

    }
    @GetMapping("/carts/{number}")
    public String showCart(@PathVariable("number") Long number, Model model){
      CartView  cart =cartRegistry.getCart(number).view();
      List<Product> products = productService.search((product)->product.getStockQuantity()>0);
      model.addAttribute("products", products);
      model.addAttribute("cart", cart);
      return "carts/view";
    }
    @PostMapping("/carts/items")
    public String addProduct (@ModelAttribute("form")  AddItemForm  addItemForm, RedirectAttributes redirectAttributes){
if (addItemForm.getCartNumber()==null) {
    throw new StoreException("Chose a cart : ");
}
if (addItemForm.getSku()==null||addItemForm.getSku().isBlank()) {
    throw new StoreException("Chose a Product : ");
}
if (addItemForm.getQuantity()==null) {
    throw new StoreException("Enter a quantity : ");
}
 cartRegistry.getCart(addItemForm.getCartNumber()).addItem(addItemForm.getSku(), addItemForm.getQuantity());
 Product product=productService.findBySku(addItemForm.getSku());

  redirectAttributes.addFlashAttribute(
          "message",
          "Added " + addItemForm.getQuantity() +" x " + product.getName() + " to cart " + addItemForm.getCartNumber()
  );
return "redirect:/carts/"+addItemForm.getCartNumber();
    }



}
