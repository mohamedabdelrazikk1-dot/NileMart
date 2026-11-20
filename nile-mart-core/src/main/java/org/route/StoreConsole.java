package org.route;

import lombok.AllArgsConstructor;
import org.route.Cart.CartRegistry;
import org.route.Cart.CartView;
import org.route.Cart.ShoppingCart;
import org.route.Model.Customer;
import org.route.Model.Governorate;
import org.route.Service.CheckOutService;
import org.route.Service.ProductService;
import org.route.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class StoreConsole {

    Scanner scanner = new Scanner(System.in);
 private final    ProductService productService ;
  private final CartRegistry cartRegistry;
private final     CheckOutService checkOutService ;
    public StoreConsole(ProductService productService, CartRegistry cartRegistry, CheckOutService checkOutService) {
        this.productService = productService;
        this.cartRegistry = cartRegistry;
        this.checkOutService = checkOutService;
    }


    boolean start = true;

  void Start() {
      while (start) {

          System.out.println("========= NILEMART ==========");
          System.out.println(
                  "1. Show products\n" +
                          "2. Create a cart\n" +
                          "3. Add a product to a cart\n" +
                          "4. Show a cart\n" +
                          "5. Check out a cart\n" +
                          "0. Exit"
          );

          System.out.print("Choose an option: ");
          int choose = scanner.nextInt();
          scanner.nextLine();

          switch (choose) {

              case 1:
                  System.out.println(productService.findAll());
                  break;

              case 2:
                  System.out.print("Enter Customer full name: ");
                  String fullName = scanner.nextLine();

                  System.out.print("Enter Customer phone number: ");
                  String phoneNumber = scanner.nextLine();

                  System.out.print(
                          "Enter Customer email, email not required: "
                  );
                  String email = scanner.nextLine();

                  Customer customer = new Customer(
                          fullName,
                          phoneNumber,
                          email
                  );
                    cartRegistry.open(customer);


                  break;

              case 3:

                  System.out.print("Enter Cart number: ");
                  long addCartNumber = scanner.nextLong();
                  scanner.nextLine();

                  ShoppingCart addCart = cartRegistry.getCart(addCartNumber);

                  if (addCart == null) {
                      System.out.println("Cart not found.");
                      break;
                  }

                  System.out.print("Enter Product SKU: ");
                  String sku = scanner.nextLine();

                  System.out.print("Enter Product Quantity: ");
                  int quantity = scanner.nextInt();
                  scanner.nextLine();

                  addCart.addItem(sku, quantity);

                  System.out.println("Product added successfully.");

                  break;

              case 4:

                  System.out.print("Enter Cart number: ");
                  long viewCartNumber = scanner.nextLong();
                  scanner.nextLine();

                 ShoppingCart viewCart = cartRegistry.getCart(viewCartNumber);


                  if (viewCart == null) {
                      System.out.println("Cart not found.");
                      break;
                  }

                  System.out.println();

                  break;

              case 5:

                  System.out.print("Enter Cart number: ");
                  long checkoutCartNumber = scanner.nextLong();
                  scanner.nextLine();

                  ShoppingCart checkoutCart =
                          cartRegistry.getCart(checkoutCartNumber);

                  if (checkoutCart == null) {
                      System.out.println("Cart not found.");
                      break;
                  }

                  System.out.println("Choose Governorate:");

                  Governorate[] governorates =
                          Governorate.values();

                  for (int i = 0; i < governorates.length; i++) {
                      System.out.println(
                              (i + 1) + ". " + governorates[i]
                      );
                  }

                  int governorateChoice = scanner.nextInt();
                  scanner.nextLine();

                  Governorate governorate =
                          governorates[governorateChoice - 1];

                  checkOutService.placeOrder(
                          checkoutCart,
                          governorate
                  );

                  System.out.println(
                          "Order created successfully."
                  );

                  break;

              case 0:
                  start = false;
                  break;

              default:
                  System.out.println("Invalid option.");
          }
      }
  }}



