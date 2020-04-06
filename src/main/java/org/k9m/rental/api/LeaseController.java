package org.k9m.rental.api;

import lombok.RequiredArgsConstructor;
import org.k9m.rental.api.model.CreateLease;
import org.k9m.rental.api.model.Lease;
import org.k9m.rental.api.service.LeaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LeaseController implements LeaseApi{

    @Autowired
    private final LeaseService leaseService;

    @Override
    public ResponseEntity<Lease> createLease(@Valid final CreateLease createLease) {
        return ResponseEntity.ok(leaseService.createLease(createLease));
    }

    @Override
    public ResponseEntity<Lease> getLease(final Long leaseId) {
        return ResponseEntity.ok(leaseService.getLease(leaseId));
    }
}
