package org.k9m.rental.api.service;

import org.junit.Before;
import org.junit.Test;
import org.k9m.rental.api.model.CreateLease;
import org.k9m.rental.persistence.model.VehicleDTO;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LeaseCalculatorTest {

    private LeaseCalculator leaseCalculator;
    private static final VehicleDTO vehicle = new VehicleDTO()
            .setNettPrice(63000.00);

    @Before
    public void before(){
        leaseCalculator = new LeaseCalculator();
    }

    @Test
    public void calculateLeaseRate() {
        CreateLease createLease = new CreateLease()
                .customerId(1L)
                .vehicleId(1L)
                .mileagePerYear(45000)
                .durationMonths(60)
                .interestRate(4.5);

        assertThat("Wrong interest rate", leaseCalculator.calculateLeaseRate(createLease, vehicle), is(239.82));
    }
}