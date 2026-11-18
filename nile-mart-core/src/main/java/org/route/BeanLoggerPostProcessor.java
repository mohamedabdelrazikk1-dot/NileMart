package org.route;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class BeanLoggerPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(
            Object bean,
            String beanName
    ) {

        System.out.println("Created bean: " + beanName);


        return bean;
    }


}
