package org.route.Notification;

import org.route.Model.Customer;


public interface NotificationSender {
    public void send(Customer customer,String message);
    default boolean supports(Customer customer){
        return true;
    }
}
