package com.kigalimotari.cooperative.repository;

import com.kigalimotari.cooperative.model.Ride;
import com.kigalimotari.cooperative.model.RideStatus;
import com.kigalimotari.cooperative.model.Rider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface RideRepository extends JpaRepository<Ride, UUID> {

    // B2 – DERIVED
    List<Ride> findByStatusOrderByRideDateAsc(RideStatus status);

    // B3 – DERIVED
    List<Ride> findByRideDateBetweenOrderByRideDateAsc(LocalDate start, LocalDate end);

    // D1 – DERIVED (existsBy, And, Not)
    boolean existsByRiderAndRideDateAndStatusNot(Rider rider, LocalDate rideDate, RideStatus status);

    // Bonus dashboard – DERIVED countBy
    long countByRideDate(LocalDate rideDate);

    long countByStatus(RideStatus status);

    // F1 – JPQL (GROUP BY, COUNT)
    @Query("SELECT e.status, COUNT(e) FROM Ride e GROUP BY e.status")
    List<Object[]> countRidesByStatus();

    // F2 – JPQL UPDATE
    @Modifying
    @Query("UPDATE Ride e SET e.status = com.kigalimotari.cooperative.model.RideStatus.CANCELLED "
            + "WHERE e.rider.id = :riderId AND e.rideDate = :date "
            + "AND e.status <> com.kigalimotari.cooperative.model.RideStatus.DROPPED_OFF")
    int cancelRidesForRiderOnDate(@Param("riderId") UUID riderId, @Param("date") LocalDate date);
}
