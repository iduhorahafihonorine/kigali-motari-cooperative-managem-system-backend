package com.kigalimotari.cooperative.controller;

import com.kigalimotari.cooperative.model.Rider;
import com.kigalimotari.cooperative.service.RiderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/riders")
public class RiderController {

    private final RiderService riderService;

    public RiderController(RiderService riderService) {
        this.riderService = riderService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Rider rider) {
        String message = riderService.saveRider(rider);
        if (message.equals("Rider saved successfully")) {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Rider>> getAll() {
        return new ResponseEntity<>(riderService.getAllRiders(), HttpStatus.OK);
    }

    // C1
    @GetMapping("/by-zone")
    public ResponseEntity<List<Rider>> byZone(@RequestParam String name) {
        return new ResponseEntity<>(riderService.findByZoneName(name), HttpStatus.OK);
    }

    // D3
    @PutMapping("/{riderId}/zones/{zoneId}")
    public ResponseEntity<?> addZone(@PathVariable UUID riderId, @PathVariable UUID zoneId) {
        String message = riderService.addZone(riderId, zoneId);
        if (message.equals("Zone added to the rider")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        if (message.equals("The rider already has that zone")) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    // D4
    @PatchMapping("/{riderId}/motorcycle")
    public ResponseEntity<?> changeMotorcycle(@PathVariable UUID riderId,
                                              @RequestParam int fleetNumber) {
        String message = riderService.changeMotorcycle(riderId, fleetNumber);
        if (message.equals("Motorcycle changed successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        if (message.equals("That motorcycle is taken by another rider")) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Rider> rider = riderService.getRiderById(id);
        if (rider.isPresent()) {
            return new ResponseEntity<>(rider.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("The rider with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Rider rider) {
        String message = riderService.updateRider(id, rider);
        if (message.equals("Rider updated successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        String message = riderService.deleteRider(id);
        if (message.equals("Rider deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}
