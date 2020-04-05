package org.k9m.rental.api;

import lombok.RequiredArgsConstructor;
import org.k9m.rental.api.exception.CustomerNotFoundException;
import org.k9m.rental.api.model.Vehicle;
import org.k9m.rental.persistence.model.VehicleDTO;
import org.k9m.rental.persistence.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VehicleController implements VehiclesApi{


    @Autowired
    private final VehicleRepository vehicleRepository;

    @Override
    public ResponseEntity<Vehicle> addVehicle(@Valid final Vehicle vehicle) {
        return ResponseEntity.ok(vehicleRepository.save(VehicleDTO.fromVehicle(vehicle)).toVehicle());
    }

    @Override
    public ResponseEntity<Vehicle> getVehicle(final Long vehicleId) {
        return ResponseEntity.ok(
                vehicleRepository.findById(vehicleId)
                        .orElseThrow(() -> new CustomerNotFoundException("Vehicle not found with id: " + vehicleId)).toVehicle());
    }

    @Override
    public ResponseEntity<Void> deleteVehicle(final Long vehicleId) {
        vehicleRepository.deleteById(vehicleId);
        return ResponseEntity.noContent().build();
    }
}
