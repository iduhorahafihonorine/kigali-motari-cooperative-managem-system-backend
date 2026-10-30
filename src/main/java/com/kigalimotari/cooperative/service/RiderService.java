package com.kigalimotari.cooperative.service;

import com.kigalimotari.cooperative.model.Motorcycle;
import com.kigalimotari.cooperative.model.Rider;
import com.kigalimotari.cooperative.model.Zone;
import com.kigalimotari.cooperative.repository.MotorcycleRepository;
import com.kigalimotari.cooperative.repository.RiderRepository;
import com.kigalimotari.cooperative.repository.ZoneRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RiderService {

    private final RiderRepository riderRepository;
    private final ZoneRepository zoneRepository;
    private final MotorcycleRepository motorcycleRepository;

    public RiderService(RiderRepository riderRepository,
                        ZoneRepository zoneRepository,
                        MotorcycleRepository motorcycleRepository) {
        this.riderRepository = riderRepository;
        this.zoneRepository = zoneRepository;
        this.motorcycleRepository = motorcycleRepository;
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

    // C1
    public List<Rider> findByZoneName(String name) {
        return riderRepository.findByZoneNameIgnoreCase(name);
    }

    // D3 – add one zone to a rider
    public String addZone(UUID riderId, UUID zoneId) {
        Optional<Rider> riderOpt = riderRepository.findById(riderId);
        if (riderOpt.isEmpty()) {
            return "The rider with that id does not exist";
        }
        Optional<Zone> zoneOpt = zoneRepository.findById(zoneId);
        if (zoneOpt.isEmpty()) {
            return "That zone does not exist";
        }
        Rider rider = riderOpt.get();
        Zone zone = zoneOpt.get();
        for (Zone z : rider.getZones()) {
            if (z.getId().equals(zoneId)) {
                return "The rider already has that zone";
            }
        }
        rider.getZones().add(zone);
        riderRepository.save(rider);
        return "Zone added to the rider";
    }

    // D4 – move rider to another motorcycle
    public String changeMotorcycle(UUID riderId, int fleetNumber) {
        Optional<Rider> riderOpt = riderRepository.findById(riderId);
        if (riderOpt.isEmpty()) {
            return "The rider with that id does not exist";
        }
        Optional<Motorcycle> motoOpt = motorcycleRepository.findByFleetNumber(fleetNumber);
        if (motoOpt.isEmpty()) {
            return "The motorcycle with that fleetNumber does not exist";
        }
        Rider rider = riderOpt.get();
        Motorcycle motorcycle = motoOpt.get();
        if (riderRepository.existsByMotorcycle(motorcycle)) {
            Motorcycle current = rider.getMotorcycle();
            if (current == null || !current.getId().equals(motorcycle.getId())) {
                return "That motorcycle is taken by another rider";
            }
        }
        rider.setMotorcycle(motorcycle);
        riderRepository.save(rider);
        return "Motorcycle changed successfully";
    }
}
