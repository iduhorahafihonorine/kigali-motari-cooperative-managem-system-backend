package com.kigalimotari.cooperative.repository;

import com.kigalimotari.cooperative.model.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface MotorcycleRepository extends JpaRepository<Motorcycle, UUID> {

    // C2 – JPQL (NOT EXISTS subquery)
    @Query("SELECT m FROM Motorcycle m WHERE NOT EXISTS (SELECT r FROM Rider r WHERE r.motorcycle = m) ORDER BY m.fleetNumber ASC")
    List<Motorcycle> findFreeMotorcycles();
}
