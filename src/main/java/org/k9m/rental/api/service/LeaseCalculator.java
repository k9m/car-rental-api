package org.k9m.rental.api.service;

import org.k9m.rental.api.model.CreateLease;
import org.k9m.rental.persistence.model.VehicleDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class LeaseCalculator {

    public double calculateLeaseRate(final CreateLease createLease, final VehicleDTO vehicleDTO){
        final double mileage = createLease.getMileagePerYear();
        final double duration = createLease.getDurationMonths();
        final double interestRate = createLease.getInterestRate();
        final double nettPrice = vehicleDTO.getNettPrice();

        final double mileagePart = (round( mileage / 12 ) * duration ) / nettPrice;
        final double interestPart = (round( interestRate / 100 ) * nettPrice) / 12;

        return round(mileagePart + interestPart, 2);
    }

    private static double round(final double nr){
        return round(nr, 6);
    }

    private static double round(final double nr, final int scale){
        BigDecimal bd = new BigDecimal(nr).setScale(scale, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
