package org.k9m.rental.api;

import lombok.RequiredArgsConstructor;
import org.k9m.rental.api.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VehicleController implements VehiclesApi{


    @Override
    public ResponseEntity<Vehicle> addVehicle(@Valid final Vehicle vehicle) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteVehicle(final Long vehicleId) {
        return null;
    }

    @Override
    public ResponseEntity<Vehicle> getVehicle(final Long vehicleId) {
        return null;
    }
}
