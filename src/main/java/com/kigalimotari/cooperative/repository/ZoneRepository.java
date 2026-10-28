package com.kigalimotari.cooperative.repository;

import com.kigalimotari.cooperative.model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ZoneRepository extends JpaRepository<Zone, UUID> {
}
