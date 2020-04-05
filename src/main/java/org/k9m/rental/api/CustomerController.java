package org.k9m.rental.api;

import lombok.RequiredArgsConstructor;
import org.k9m.rental.api.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerController implements CustomersApi{


    @Override
    public ResponseEntity<Customer> addCustomer(@Valid Customer customer) {
        return null;
    }

    @Override
    public ResponseEntity<Customer> deleteCustomer(Long customerId) {
        return null;
    }

    @Override
    public ResponseEntity<Customer> getCustomer(Long customerId) {
        return null;
    }
}
