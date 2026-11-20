package org.route.Model;

import org.route.common.StoreException;

import java.security.PublicKey;

public record Customer(String fullName, String phone , String email) {
public Customer(String fullName,String phone ,String email) {
    if (fullName == null || fullName.isBlank()) {
        throw new StoreException("Customer name cannot be blank");
    }
   else{
       this.fullName = fullName;
    }
    if(email.isBlank()) {
        this.email = null;
    }
    else if(email!=null && !email.contains("@")) {
      throw new StoreException("Invalid email address , must contain a @");
    }
    else{
        this.email = email;
    }
    if (phone == null || !phone.matches("01[0125][0-9]{8}")) {
        throw new StoreException("Invalid Egyptian phone number");
    }
    else {
        this.phone = phone;
    }

}
}
