package org.k9m.rental.persistence.model;

import lombok.Data;
import org.k9m.rental.api.model.Vehicle;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity(name = "vehicle")
public class VehicleDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String make;
    private String model;
    private String version;
    private Integer doors;
    private Double grossPrice;
    private Double nettPrice;
    private Integer hp;


    public Vehicle toVehicle(){
        return new Vehicle()
                .id(id)
                .make(make)
                .model(model)
                .version(version)
                .doors(doors)
                .grossPrice(grossPrice)
                .nettPrice(nettPrice)
                .hp(hp);
    }


    public static VehicleDTO fromVehicle(final Vehicle vehicle) {
        return new VehicleDTO()
                .setId(vehicle.getId())
                .setMake(vehicle.getMake())
                .setModel(vehicle.getModel())
                .setVersion(vehicle.getVersion())
                .setDoors(vehicle.getDoors())
                .setGrossPrice(vehicle.getGrossPrice())
                .setNettPrice(vehicle.getNettPrice())
                .setHp(vehicle.getHp());
    }

}
