package org.route;



import org.route.Cart.CartRegistry;
import org.route.Cart.ShoppingCart;
import org.route.CheckOut.OrderRegistry;
import org.route.Model.CategoryEnum;
import org.route.Model.Customer;
import org.route.Model.Governorate;
import org.route.Service.CheckOutService;
import org.route.Service.ProductService;
import org.route.common.StoreException;
import org.route.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        var context = new AnnotationConfigWebApplicationContext();
        context.register(AppConfig.class);
        try (context; WebServer server = new WebServer(context, 8081)) {
            server.start();                        // Tomcat starts, the container is refreshed
           new StoreConsole(context.getBean(ProductService.class),context.getBean(CartRegistry.class),context.getBean(CheckOutService.class)).Start();   ;// returns on option 0
        }   // closes the server, then the container: @PreDestroy methods run
    }
}
