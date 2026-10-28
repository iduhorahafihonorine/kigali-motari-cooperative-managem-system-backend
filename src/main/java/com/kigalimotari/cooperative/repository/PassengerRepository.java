package com.kigalimotari.cooperative.repository;

import com.kigalimotari.cooperative.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PassengerRepository extends JpaRepository<Passenger, UUID> {
}
