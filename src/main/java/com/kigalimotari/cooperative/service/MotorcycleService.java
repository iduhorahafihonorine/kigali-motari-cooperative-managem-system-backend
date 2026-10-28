package com.kigalimotari.cooperative.service;

import com.kigalimotari.cooperative.model.Motorcycle;
import com.kigalimotari.cooperative.repository.MotorcycleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MotorcycleService {

    private final MotorcycleRepository motorcycleRepository;

    public MotorcycleService(MotorcycleRepository motorcycleRepository) {
        this.motorcycleRepository = motorcycleRepository;
    }

    public String saveMotorcycle(Motorcycle motorcycle) {
        motorcycle.setId(null);
        motorcycleRepository.save(motorcycle);
        return "Motorcycle saved successfully";
    }

    public List<Motorcycle> getAllMotorcycles() {
        return motorcycleRepository.findAll();
    }

    public Optional<Motorcycle> getMotorcycleById(UUID id) {
        return motorcycleRepository.findById(id);
    }

    public String updateMotorcycle(UUID id, Motorcycle updated) {
        Optional<Motorcycle> existing = motorcycleRepository.findById(id);
        if (existing.isEmpty()) {
            return "The motorcycle with that id does not exist";
        }
        Motorcycle motorcycle = existing.get();
        motorcycle.setFleetNumber(updated.getFleetNumber());
        motorcycle.setPlate(updated.getPlate());
        motorcycleRepository.save(motorcycle);
        return "Motorcycle updated successfully";
    }

    public String deleteMotorcycle(UUID id) {
        if (!motorcycleRepository.existsById(id)) {
            return "The motorcycle with that id does not exist";
        }
        motorcycleRepository.deleteById(id);
        return "Motorcycle deleted successfully";
    }
}
