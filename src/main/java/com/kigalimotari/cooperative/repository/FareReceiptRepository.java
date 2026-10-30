package com.kigalimotari.cooperative.repository;

import com.kigalimotari.cooperative.model.FareReceipt;
import com.kigalimotari.cooperative.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface FareReceiptRepository extends JpaRepository<FareReceipt, UUID> {

    // E2 – JPQL (path navigation, ORDER BY)
    @Query("SELECT d FROM FareReceipt d WHERE d.ride.passenger.phone = :phone ORDER BY d.paidDate DESC")
    List<FareReceipt> findByPassengerPhone(@Param("phone") String phone);

    // E3 – DERIVED
    boolean existsByRide(Ride ride);
}
