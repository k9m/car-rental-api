package org.k9m.rental.persistence.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class VehicleDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;
    private String version;
    private Integer doors;
    private Double grossPrice;
    private Double nettPrice;
    private Integer hp;

}
