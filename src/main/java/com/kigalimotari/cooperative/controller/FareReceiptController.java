package com.kigalimotari.cooperative.controller;

import com.kigalimotari.cooperative.model.FareReceipt;
import com.kigalimotari.cooperative.service.FareReceiptService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/fare-receipts")
public class FareReceiptController {

    private final FareReceiptService fareReceiptService;

    public FareReceiptController(FareReceiptService fareReceiptService) {
        this.fareReceiptService = fareReceiptService;
    }

    // E1
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestParam UUID rideId, @RequestBody FareReceipt body) {
        String message = fareReceiptService.saveFareReceipt(rideId, body);
        if (message.equals("Fare receipt saved successfully")) {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
        if (message.equals("The ride with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    // E2
    @GetMapping("/passenger/{phone}")
    public ResponseEntity<List<FareReceipt>> byPassenger(@PathVariable String phone) {
        return new ResponseEntity<>(fareReceiptService.findByPassengerPhone(phone), HttpStatus.OK);
    }

    // E3
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        String message = fareReceiptService.deleteFareReceipt(id);
        if (message.equals("Fare receipt deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}
