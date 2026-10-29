package com.kigalimotari.cooperative.repository;

import com.kigalimotari.cooperative.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PassengerRepository extends JpaRepository<Passenger, UUID> {

    // B1 – DERIVED
    List<Passenger> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    // C4 – JPQL (DISTINCT, path navigation)
    @Query("SELECT DISTINCT e.passenger FROM Ride e WHERE e.rider.id = :riderId")
    List<Passenger> findDistinctPassengersOfRider(@Param("riderId") UUID riderId);
}
