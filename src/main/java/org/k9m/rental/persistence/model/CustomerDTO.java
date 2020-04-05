package org.k9m.rental.persistence.model;

import lombok.Data;
import org.k9m.rental.api.model.Customer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity(name = "customer")
public class CustomerDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String street;
    private Integer houseNumber;
    private String zipcode;
    private String place;
    private String email;
    private String phoneNumber;
    
    
    public Customer toCustomer(){
        return new Customer()
                .id(id)
                .name(name)
                .street(street)
                .houseNumber(houseNumber)
                .zipcode(zipcode)
                .place(place)
                .email(email)
                .phoneNumber(phoneNumber);  
    }

    public static CustomerDTO fromCustomer(final Customer customer){
        return new CustomerDTO()
                .setId(customer.getId())
                .setName(customer.getName())
                .setStreet(customer.getStreet())
                .setHouseNumber(customer.getHouseNumber())
                .setZipcode(customer.getZipcode())
                .setPlace(customer.getPlace())
                .setEmail(customer.getEmail())
                .setPhoneNumber(customer.getPhoneNumber());
    }



}
