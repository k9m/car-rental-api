package org.k9m.rental.persistence.repository;

import org.k9m.rental.persistence.model.LeaseDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaseRepository extends JpaRepository<LeaseDTO, Long> {}