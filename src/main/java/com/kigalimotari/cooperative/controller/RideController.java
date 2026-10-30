package com.kigalimotari.cooperative.controller;

import com.kigalimotari.cooperative.model.Ride;
import com.kigalimotari.cooperative.model.RideStatus;
import com.kigalimotari.cooperative.service.RideService;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/rides")
public class RideController {

    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    // D1 modifies A2 save
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Ride ride) {
        String message = rideService.saveRide(ride);
        if (message.equals("Ride saved successfully")) {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
        if (message.equals("Ride date cannot be in the past")) {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        if (message.equals("Rider already has a ride on that date")) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Ride>> getAll() {
        return new ResponseEntity<>(rideService.getAllRides(), HttpStatus.OK);
    }

    // B2
    @GetMapping("/by-status")
    public ResponseEntity<List<Ride>> byStatus(@RequestParam RideStatus status) {
        return new ResponseEntity<>(rideService.findByStatus(status), HttpStatus.OK);
    }

    // B3 – parse Strings into LocalDate in the controller
    @GetMapping("/between")
    public ResponseEntity<List<Ride>> between(@RequestParam String start, @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return new ResponseEntity<>(rideService.findBetween(startDate, endDate), HttpStatus.OK);
    }

    // F1
    @GetMapping("/stats/by-status")
    public ResponseEntity<List<Object[]>> statsByStatus() {
        return new ResponseEntity<>(rideService.ridesPerStatus(), HttpStatus.OK);
    }

    // F2
    @PatchMapping("/cancel-day")
    public ResponseEntity<String> cancelDay(@RequestParam UUID riderId, @RequestParam String date) {
        LocalDate rideDate = LocalDate.parse(date);
        String message = rideService.cancelDay(riderId, rideDate);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // D2
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable UUID id, @RequestParam RideStatus status) {
        String message = rideService.updateStatus(id, status);
        if (message.equals("Ride status updated successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        if (message.equals("The ride with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Ride> ride = rideService.getRideById(id);
        if (ride.isPresent()) {
            return new ResponseEntity<>(ride.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("The ride with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Ride ride) {
        String message = rideService.updateRide(id, ride);
        if (message.equals("Ride updated successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        String message = rideService.deleteRide(id);
        if (message.equals("Ride deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        if (message.equals("That ride has fare receipts, delete them first")) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}
