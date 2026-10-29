package com.kigalimotari.cooperative.service;

import com.kigalimotari.cooperative.model.Ride;
import com.kigalimotari.cooperative.model.RideStatus;
import com.kigalimotari.cooperative.repository.RideRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RideService {

    private final RideRepository rideRepository;

    public RideService(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    public String saveRide(Ride ride) {
        ride.setId(null);
        rideRepository.save(ride);
        return "Ride saved successfully";
    }

    public List<Ride> getAllRides() {
        return rideRepository.findAll();
    }

    public Optional<Ride> getRideById(UUID id) {
        return rideRepository.findById(id);
    }

    public String updateRide(UUID id, Ride updated) {
        Optional<Ride> existing = rideRepository.findById(id);
        if (existing.isEmpty()) {
            return "The ride with that id does not exist";
        }
        Ride ride = existing.get();
        ride.setRideDate(updated.getRideDate());
        ride.setStatus(updated.getStatus());
        ride.setPickup(updated.getPickup());
        ride.setDestination(updated.getDestination());
        ride.setPassenger(updated.getPassenger());
        ride.setRider(updated.getRider());
        rideRepository.save(ride);
        return "Ride updated successfully";
    }

    public String deleteRide(UUID id) {
        if (!rideRepository.existsById(id)) {
            return "The ride with that id does not exist";
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
}
