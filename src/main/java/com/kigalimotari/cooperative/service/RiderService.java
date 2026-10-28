package com.kigalimotari.cooperative.service;

import com.kigalimotari.cooperative.model.Rider;
import com.kigalimotari.cooperative.repository.RiderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RiderService {

    private final RiderRepository riderRepository;

    public RiderService(RiderRepository riderRepository) {
        this.riderRepository = riderRepository;
    }

    public String saveRider(Rider rider) {
        rider.setId(null);
        riderRepository.save(rider);
        return "Rider saved successfully";
    }

    public List<Rider> getAllRiders() {
        return riderRepository.findAll();
    }

    public Optional<Rider> getRiderById(UUID id) {
        return riderRepository.findById(id);
    }

    public String updateRider(UUID id, Rider updated) {
        Optional<Rider> existing = riderRepository.findById(id);
        if (existing.isEmpty()) {
            return "The rider with that id does not exist";
        }
        Rider rider = existing.get();
        rider.setFirstName(updated.getFirstName());
        rider.setLastName(updated.getLastName());
        rider.setLicenceNumber(updated.getLicenceNumber());
        rider.setMotorcycle(updated.getMotorcycle());
        rider.setZones(updated.getZones());
        riderRepository.save(rider);
        return "Rider updated successfully";
    }

    public String deleteRider(UUID id) {
        if (!riderRepository.existsById(id)) {
            return "The rider with that id does not exist";
        }
        riderRepository.deleteById(id);
        return "Rider deleted successfully";
    }
}
