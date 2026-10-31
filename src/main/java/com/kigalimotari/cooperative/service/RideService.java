package com.kigalimotari.cooperative.service;

import com.kigalimotari.cooperative.model.Ride;
import com.kigalimotari.cooperative.model.RideStatus;
import com.kigalimotari.cooperative.repository.FareReceiptRepository;
import com.kigalimotari.cooperative.repository.RideRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RideService {

    private final RideRepository rideRepository;
    private final FareReceiptRepository fareReceiptRepository;

    public RideService(RideRepository rideRepository, FareReceiptRepository fareReceiptRepository) {
        this.rideRepository = rideRepository;
        this.fareReceiptRepository = fareReceiptRepository;
    }

    // D1 – safe saving
    public String saveRide(Ride ride) {
        if (ride.getRideDate() != null && ride.getRideDate().isBefore(LocalDate.now())) {
            return "Ride date cannot be in the past";
        }
        if (ride.getRider() != null
                && rideRepository.existsByRiderAndRideDateAndStatusNot(
                ride.getRider(), ride.getRideDate(), RideStatus.CANCELLED)) {
            return "Rider already has a ride on that date";
        }
        ride.setId(null);
        ride.setStatus(RideStatus.REQUESTED);
        rideRepository.save(ride);
        return "Ride saved successfully";
    }

    public List<Ride> getAllRides() {
        return rideRepository.findAll();
    }

    public Optional<Ride> getRideById(UUID id) {
        return rideRepository.findById(id);
    }

    // D2 – PUT must no longer change status
    public String updateRide(UUID id, Ride updated) {
        Optional<Ride> existing = rideRepository.findById(id);
        if (existing.isEmpty()) {
            return "The ride with that id does not exist";
        }
        Ride ride = existing.get();
        ride.setRideDate(updated.getRideDate());
        ride.setPickup(updated.getPickup());
        ride.setDestination(updated.getDestination());
        ride.setPassenger(updated.getPassenger());
        ride.setRider(updated.getRider());
        rideRepository.save(ride);
        return "Ride updated successfully";
    }

    // E3 – protect ride delete when fare receipts exist
    public String deleteRide(UUID id) {
        Optional<Ride> existing = rideRepository.findById(id);
        if (existing.isEmpty()) {
            return "The ride with that id does not exist";
        }
        if (fareReceiptRepository.existsByRide(existing.get())) {
            return "That ride has fare receipts, delete them first";
        }
        rideRepository.deleteById(id);
        return "Ride deleted successfully";
    }

    // B2
    public List<Ride> findByStatus(RideStatus status) {
        return rideRepository.findByStatusOrderByRideDateAsc(status);
    }

    // B3
    public List<Ride> findBetween(LocalDate start, LocalDate end) {
        return rideRepository.findByRideDateBetweenOrderByRideDateAsc(start, end);
    }

    // D2 – status flow
    public String updateStatus(UUID id, RideStatus newStatus) {
        Optional<Ride> existing = rideRepository.findById(id);
        if (existing.isEmpty()) {
            return "The ride with that id does not exist";
        }
        Ride ride = existing.get();
        RideStatus current = ride.getStatus();
        boolean allowed = false;
        if (current == RideStatus.REQUESTED
                && (newStatus == RideStatus.PICKED_UP || newStatus == RideStatus.CANCELLED)) {
            allowed = true;
        }
        if (current == RideStatus.PICKED_UP
                && (newStatus == RideStatus.DROPPED_OFF || newStatus == RideStatus.CANCELLED)) {
            allowed = true;
        }
        if (!allowed) {
            return "Cannot change status from " + current + " to " + newStatus;
        }
        ride.setStatus(newStatus);
        rideRepository.save(ride);
        return "Ride status updated successfully";
    }

    // F1
    public List<Object[]> ridesPerStatus() {
        return rideRepository.countRidesByStatus();
    }

    // F2
    @Transactional
    public String cancelDay(UUID riderId, LocalDate date) {
        int count = rideRepository.cancelRidesForRiderOnDate(riderId, date);
        return count + " rides cancelled";
    }

    // Bonus – pagination
    public Page<Ride> findPage(Pageable pageable) {
        return rideRepository.findAll(pageable);
    }
}
