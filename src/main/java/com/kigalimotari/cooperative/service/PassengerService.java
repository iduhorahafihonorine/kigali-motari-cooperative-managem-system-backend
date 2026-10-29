package com.kigalimotari.cooperative.service;

import com.kigalimotari.cooperative.model.Passenger;
import com.kigalimotari.cooperative.repository.PassengerRepository;
import com.kigalimotari.cooperative.repository.RiderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;
    private final RiderRepository riderRepository;

    public PassengerService(PassengerRepository passengerRepository, RiderRepository riderRepository) {
        this.passengerRepository = passengerRepository;
        this.riderRepository = riderRepository;
    }

    public String savePassenger(Passenger passenger) {
        passenger.setId(null);
        passengerRepository.save(passenger);
        return "Passenger saved successfully";
    }

    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    public Optional<Passenger> getPassengerById(UUID id) {
        return passengerRepository.findById(id);
    }

    public String updatePassenger(UUID id, Passenger updated) {
        Optional<Passenger> existing = passengerRepository.findById(id);
        if (existing.isEmpty()) {
            return "The passenger with that id does not exist";
        }
        Passenger passenger = existing.get();
        passenger.setFirstName(updated.getFirstName());
        passenger.setLastName(updated.getLastName());
        passenger.setPhone(updated.getPhone());
        passenger.setEmail(updated.getEmail());
        passengerRepository.save(passenger);
        return "Passenger updated successfully";
    }

    public String deletePassenger(UUID id) {
        if (!passengerRepository.existsById(id)) {
            return "The passenger with that id does not exist";
        }
        passengerRepository.deleteById(id);
        return "Passenger deleted successfully";
    }

    // B1
    public List<Passenger> findByLastName(String lastName) {
        return passengerRepository.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }

    // C4 – returns empty Optional when rider missing (controller maps 404)
    public Optional<List<Passenger>> findPassengersOfRider(UUID riderId) {
        if (!riderRepository.existsById(riderId)) {
            return Optional.empty();
        }
        return Optional.of(passengerRepository.findDistinctPassengersOfRider(riderId));
    }
}
