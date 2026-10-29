package com.kigalimotari.cooperative.repository;

import com.kigalimotari.cooperative.model.Ride;
import com.kigalimotari.cooperative.model.RideStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface RideRepository extends JpaRepository<Ride, UUID> {

    // B2 – DERIVED
    List<Ride> findByStatusOrderByRideDateAsc(RideStatus status);

    // B3 – DERIVED
    List<Ride> findByRideDateBetweenOrderByRideDateAsc(LocalDate start, LocalDate end);
}
