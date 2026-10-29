package com.kigalimotari.cooperative.controller;

import com.kigalimotari.cooperative.model.Motorcycle;
import com.kigalimotari.cooperative.service.MotorcycleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/motorcycles")
public class MotorcycleController {

    private final MotorcycleService motorcycleService;

    public MotorcycleController(MotorcycleService motorcycleService) {
        this.motorcycleService = motorcycleService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Motorcycle motorcycle) {
        String message = motorcycleService.saveMotorcycle(motorcycle);
        if (message.equals("Motorcycle saved successfully")) {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Motorcycle>> getAll() {
        return new ResponseEntity<>(motorcycleService.getAllMotorcycles(), HttpStatus.OK);
    }

    // C2
    @GetMapping("/free")
    public ResponseEntity<List<Motorcycle>> free() {
        return new ResponseEntity<>(motorcycleService.findFreeMotorcycles(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Motorcycle> motorcycle = motorcycleService.getMotorcycleById(id);
        if (motorcycle.isPresent()) {
            return new ResponseEntity<>(motorcycle.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("The motorcycle with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Motorcycle motorcycle) {
        String message = motorcycleService.updateMotorcycle(id, motorcycle);
        if (message.equals("Motorcycle updated successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        String message = motorcycleService.deleteMotorcycle(id);
        if (message.equals("Motorcycle deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}
