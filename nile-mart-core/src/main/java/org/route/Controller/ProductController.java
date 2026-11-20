package org.route.Controller;

import lombok.RequiredArgsConstructor;
import org.route.Cart.CartRegistry;
import org.route.Cart.CartView;
import org.route.Model.CategoryEnum;
import org.route.Model.Customer;
import org.route.Model.NewCartForm;
import org.route.Model.Product;
import org.route.Service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CartRegistry cartRegistry;

  @GetMapping("/")
    public String home(){
        return "redirect:/products";
    }

    @GetMapping("/products")
    public String list(
            @RequestParam(name = "cart" ,required = false) Long cart,
            Model model) {

       Map<CategoryEnum, List<Product>> groups = productService.groupByCategory();
        List<CartView> carts = cartRegistry.view();
        System.out.println(groups + " it found");

        model.addAttribute("groups", groups);
        model.addAttribute("carts", carts);
        model.addAttribute("selectedCart", cart);

        return "products/list";
    }



}
