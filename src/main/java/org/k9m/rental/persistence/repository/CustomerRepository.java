package org.k9m.rental.persistence.repository;

import org.k9m.rental.persistence.model.CustomerDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerDTO, Integer> {}