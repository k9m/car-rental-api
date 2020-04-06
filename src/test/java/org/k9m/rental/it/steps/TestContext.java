package org.k9m.rental.it.steps;

import org.k9m.rental.api.model.CreateLease;
import org.k9m.rental.api.model.Customer;
import org.k9m.rental.api.model.Lease;
import org.k9m.rental.api.model.Vehicle;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Component
public class TestContext {

    Customer lastSavedCustomer;
    Customer lastRetrievedCustomer;

    Vehicle lastSavedVehicle;
    Vehicle lastRetrievedVehicle;

    CreateLease createLease;
    Lease lastSavedLease;

    HttpClientErrorException lastThrownException;

}
