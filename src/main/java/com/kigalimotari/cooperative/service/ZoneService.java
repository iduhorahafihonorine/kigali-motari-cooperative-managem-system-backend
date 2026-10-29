package com.kigalimotari.cooperative.service;

import com.kigalimotari.cooperative.model.Zone;
import com.kigalimotari.cooperative.repository.ZoneRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ZoneService {

    private final ZoneRepository zoneRepository;

    public ZoneService(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    public String saveZone(Zone zone) {
        zone.setId(null);
        zoneRepository.save(zone);
        return "Zone saved successfully";
    }

    public List<Zone> getAllZones() {
        return zoneRepository.findAll();
    }

    public Optional<Zone> getZoneById(UUID id) {
        return zoneRepository.findById(id);
    }

    public String updateZone(UUID id, Zone updated) {
        Optional<Zone> existing = zoneRepository.findById(id);
        if (existing.isEmpty()) {
            return "The zone with that id does not exist";
        }
        Zone zone = existing.get();
        zone.setName(updated.getName());
        zoneRepository.save(zone);
        return "Zone updated successfully";
    }

    public String deleteZone(UUID id) {
        if (!zoneRepository.existsById(id)) {
            return "The zone with that id does not exist";
        }
        zoneRepository.deleteById(id);
        return "Zone deleted successfully";
    }

    // C3
    public List<Zone> findUnusedZones() {
        return zoneRepository.findUnusedZones();
    }
}
