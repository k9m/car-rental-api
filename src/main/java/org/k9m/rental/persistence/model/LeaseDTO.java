package org.k9m.rental.persistence.model;

import lombok.Data;
import org.k9m.rental.api.model.Lease;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity(name = "lease")
public class LeaseDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;
    private Integer durationMonths;
    private Integer mileagePerYear;
    private Double interestRate;
    private Double leaseRate;

    @OneToOne(cascade = CascadeType.REFRESH)
    private CustomerDTO customer;

    @OneToOne(cascade = CascadeType.REFRESH)
    private VehicleDTO vehicle;


    public Lease toLease(){
        return new Lease()
                .id(id)
                .startDate(startDate)
                .durationMonths(durationMonths)
                .mileagePerYear(mileagePerYear)
                .interestRate(interestRate)
                .leaseRate(leaseRate)
                .customer(customer.toCustomer())
                .vehicle(vehicle.toVehicle());
    }

}
