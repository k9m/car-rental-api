package org.k9m.rental.api.service;

import lombok.RequiredArgsConstructor;
import org.k9m.rental.api.exception.CustomerNotFoundException;
import org.k9m.rental.api.exception.LeaseNotFoundException;
import org.k9m.rental.api.exception.VehicleNotFoundException;
import org.k9m.rental.api.model.CreateLease;
import org.k9m.rental.api.model.Lease;
import org.k9m.rental.persistence.model.CustomerDTO;
import org.k9m.rental.persistence.model.LeaseDTO;
import org.k9m.rental.persistence.model.VehicleDTO;
import org.k9m.rental.persistence.repository.CustomerRepository;
import org.k9m.rental.persistence.repository.LeaseRepository;
import org.k9m.rental.persistence.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LeaseService {

    @Autowired
    private final CustomerRepository customerRepository;

    @Autowired
    private final VehicleRepository vehicleRepository;

    @Autowired
    private final LeaseRepository leaseRepository;

    public Lease createLease(final CreateLease createLease){
        final CustomerDTO customerDTO = customerRepository.findById(createLease.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + createLease.getCustomerId()));

        final VehicleDTO vehicleDTO = vehicleRepository.findById(createLease.getVehicleId())
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle not found with id: " + createLease.getVehicleId()));

        final double leaseRate = new LeaseCalculator().calculateLeaseRate(createLease, vehicleDTO);
        final LeaseDTO lease = new LeaseDTO()
                .setDurationMonths(createLease.getDurationMonths())
                .setMileagePerYear(createLease.getMileagePerYear())
                .setInterestRate(createLease.getInterestRate())
                .setStartDate(createLease.getStartDate())
                .setLeaseRate(leaseRate)
                .setCustomer(customerDTO)
                .setVehicle(vehicleDTO);

        return leaseRepository.save(lease).toLease();
    }

    public Lease getLease(final Long leaseId) {
        return leaseRepository.findById(leaseId).orElseThrow(() -> new LeaseNotFoundException("Lease not found with id: " + leaseId)).toLease();
    }

    public void delete(final Long leaseId) {
        leaseRepository.deleteById(leaseId);
    }
}
