package com.kigalimotari.cooperative.repository;

import com.kigalimotari.cooperative.model.Motorcycle;
import com.kigalimotari.cooperative.model.Rider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface RiderRepository extends JpaRepository<Rider, UUID> {

    // C1 – JPQL (JOIN on collection, LOWER)
    @Query("SELECT r FROM Rider r JOIN r.zones z WHERE LOWER(z.name) = LOWER(:name)")
    List<Rider> findByZoneNameIgnoreCase(@Param("name") String name);

    // D4 – DERIVED
    boolean existsByMotorcycle(Motorcycle motorcycle);
}
