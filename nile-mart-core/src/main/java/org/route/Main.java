package org.route;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.Arrays;

public class Main {

    void main(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        ProductService service = context.getBean(ProductService.class);
        System.out.println(
                Arrays.toString(context.getBeanDefinitionNames())
        );

        context.close();

//        StandardPricingService standardPricingService = new StandardPricingService();
//        PromotionalPricingService promotionalPricingService=new PromotionalPricingService(BigDecimal.valueOf(10));
//        InMemoryProductRepository inMemoryProductRepository = new InMemoryProductRepository();
//        inMemoryProductRepository.loadSeedProducts();
//
//        ProductService p=new ProductService(inMemoryProductRepository,standardPricingService);
//        System.out.println(  p.getPrice("ACC-CHG-20W",2));
//
//        ProductService p1=new ProductService(inMemoryProductRepository,promotionalPricingService);
//        System.out.println(p1.getPrice("ACC-CHG-20W",2));
//
//        p.reduceStock("ACC-CHG-20W",15);
//        System.out.println(p.getPrice("ACC-CHG-20W",2));
//        System.out.println(  p.findAll());


//        //        var context = new ClassPathXmlApplicationContext("beans.xml");
//
//
////        StandardPricingService standard = context.getBean(StandardPricingService.class);
////        StandardPricingService standard2 = context.getBean(StandardPricingService.class);
//
////        ProductService p = context.getBean(ProductService.class);
////
////        System.out.println(p);
//
//        var context = new AnnotationConfigApplicationContext(AppConfig.class);
////        var context2 = new AnnotationConfigApplicationContext(AppConfig.class);
//
////        StandardPricingService standard = context.getBean(StandardPricingService.class);
////        StandardPricingService standard2 = context2.getBean(StandardPricingService.class);
//
////        System.out.println(standard == standard2);
////        ProductService p = context.getBean(ProductService.class);
////        System.out.println(standard);
//
//        ProductService productService = context.getBean(ProductService.class);
//
//        System.out.println(productService);
//
//        Product product = Product.builder()
//                .price(BigDecimal.valueOf(100))
//                .build();
//
//        System.out.println(productService.getPrice("PH-SAM-S25", 2));

    }
}
