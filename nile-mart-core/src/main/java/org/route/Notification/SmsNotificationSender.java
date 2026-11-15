package org.route.Notification;

import org.route.Model.Customer;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class SmsNotificationSender extends AbstractNotificationSender {
    @Override
    protected String channel() {
        return "Sms";
    }

    @Override
    protected String address(Customer customer) {
        return customer.phone();
    }

    @Override
    public boolean supports(Customer customer) {
        return super.supports(customer);
    }
}
