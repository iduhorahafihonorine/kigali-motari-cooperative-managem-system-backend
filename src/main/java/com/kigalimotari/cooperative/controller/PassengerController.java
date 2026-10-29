package com.kigalimotari.cooperative.controller;

import com.kigalimotari.cooperative.model.Passenger;
import com.kigalimotari.cooperative.service.PassengerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/api/passengers")
public class PassengerController {

    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Passenger passenger) {
        String message = passengerService.savePassenger(passenger);
        if (message.equals("Passenger saved successfully")) {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Passenger>> getAll() {
        return new ResponseEntity<>(passengerService.getAllPassengers(), HttpStatus.OK);
    }

    // B1
    @GetMapping("/by-last-name")
    public ResponseEntity<List<Passenger>> byLastName(@RequestParam String lastName) {
        return new ResponseEntity<>(passengerService.findByLastName(lastName), HttpStatus.OK);
    }

    // C4
    @GetMapping("/of-rider/{riderId}")
    public ResponseEntity<?> ofRider(@PathVariable UUID riderId) {
        Optional<List<Passenger>> result = passengerService.findPassengersOfRider(riderId);
        if (result.isEmpty()) {
            return new ResponseEntity<>("The rider with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result.get(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Passenger> passenger = passengerService.getPassengerById(id);
        if (passenger.isPresent()) {
            return new ResponseEntity<>(passenger.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("The passenger with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Passenger passenger) {
        String message = passengerService.updatePassenger(id, passenger);
        if (message.equals("Passenger updated successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        String message = passengerService.deletePassenger(id);
        if (message.equals("Passenger deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}
