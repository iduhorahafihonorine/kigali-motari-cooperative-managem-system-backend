package com.kigalimotari.cooperative.repository;

import com.kigalimotari.cooperative.model.Rider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RiderRepository extends JpaRepository<Rider, UUID> {
}
