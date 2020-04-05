package org.k9m.rental.persistence.repository;

import org.k9m.rental.persistence.model.VehicleDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<VehicleDTO, Long> {}