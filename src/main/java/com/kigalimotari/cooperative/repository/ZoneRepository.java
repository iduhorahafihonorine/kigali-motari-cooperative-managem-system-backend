package com.kigalimotari.cooperative.repository;

import com.kigalimotari.cooperative.model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ZoneRepository extends JpaRepository<Zone, UUID> {

    // C3 – JPQL (IS EMPTY)
    @Query("SELECT z FROM Zone z WHERE z.riders IS EMPTY")
    List<Zone> findUnusedZones();
}
