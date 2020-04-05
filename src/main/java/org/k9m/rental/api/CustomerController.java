package org.k9m.rental.api;

import lombok.RequiredArgsConstructor;
import org.k9m.rental.api.exception.CustomerNotFoundException;
import org.k9m.rental.api.model.Customer;
import org.k9m.rental.persistence.model.CustomerDTO;
import org.k9m.rental.persistence.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerController implements CustomersApi{

    @Autowired
    private final CustomerRepository customerRepository;

    @Override
    public ResponseEntity<Customer> addCustomer(@Valid final Customer customer) {
        return ResponseEntity.ok(customerRepository.save(CustomerDTO.fromCustomer(customer)).toCustomer());
    }

    @Override
    public ResponseEntity<Customer> getCustomer(final Long customerId) {
        return ResponseEntity.ok(
                customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + customerId)).toCustomer());
    }

    @Override
    public ResponseEntity<Void> deleteCustomer(final Long customerId) {
        customerRepository.deleteById(customerId);
        return ResponseEntity.noContent().build();
    }
}
