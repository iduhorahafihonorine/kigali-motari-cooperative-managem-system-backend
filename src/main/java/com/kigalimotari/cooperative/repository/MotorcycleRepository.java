package com.kigalimotari.cooperative.repository;

import com.kigalimotari.cooperative.model.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MotorcycleRepository extends JpaRepository<Motorcycle, UUID> {
}
