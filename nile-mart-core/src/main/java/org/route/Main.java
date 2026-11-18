package org.route;



import org.route.Cart.ShoppingCart;
import org.route.Model.Customer;
import org.route.Model.Governorate;
import org.route.Service.CheckOutService;
import org.route.Service.ProductService;
import org.route.common.StoreException;
import org.route.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    void main() {

        Map<Long, ShoppingCart> carts = new HashMap<>();

        try (
                AnnotationConfigApplicationContext context =
                        new AnnotationConfigApplicationContext(AppConfig.class);
                Scanner scanner = new Scanner(System.in)
        ) {

            ProductService productService =
                    context.getBean(ProductService.class);

            ShoppingCart shoppingCart =
                    context.getBean(ShoppingCart.class);

            CheckOutService checkOutService =
                    context.getBean(CheckOutService.class);

            boolean start = true;

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

                        System.out.print("Enter Cart number: ");
                        long cartNumber = scanner.nextLong();
                        scanner.nextLine();

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

                        shoppingCart.open(cartNumber, customer);

                        carts.put(cartNumber, shoppingCart);

                        System.out.println(
                                "Cart created successfully: " + cartNumber
                        );

                        break;

                    case 3:

                        System.out.print("Enter Cart number: ");
                        long addCartNumber = scanner.nextLong();
                        scanner.nextLine();

                        ShoppingCart addCart = carts.get(addCartNumber);

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

                        ShoppingCart viewCart = carts.get(viewCartNumber);

                        if (viewCart == null) {
                            System.out.println("Cart not found.");
                            break;
                        }

                        System.out.println(shoppingCart.view());

                        break;

                    case 5:

                        System.out.print("Enter Cart number: ");
                        long checkoutCartNumber = scanner.nextLong();
                        scanner.nextLine();

                        ShoppingCart checkoutCart =
                                carts.get(checkoutCartNumber);

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

        } catch (Exception e) {
            throw new StoreException(e.getMessage());
        }
    }
}
