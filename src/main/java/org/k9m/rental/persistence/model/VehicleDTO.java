package org.k9m.rental.persistence.model;

import lombok.Data;

import javax.persistence.Id;

@Data
public class VehicleDTO {

    @Id
    private Integer id;

    private String model;
    private String version;
    private Integer doors;
    private Double grossPrice;
    private Double nettPrice;
    private Integer hp;

}
