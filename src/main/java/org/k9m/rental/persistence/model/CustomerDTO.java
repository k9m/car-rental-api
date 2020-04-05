package org.k9m.rental.persistence.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity(name = "customer")
public class CustomerDTO {

    @Id
    private Integer id;

    private String name;
    private String street;
    private Integer houseNumber;
    private String zipcode;
    private String place;
    private String email;
    private String phoneNumber;



}
