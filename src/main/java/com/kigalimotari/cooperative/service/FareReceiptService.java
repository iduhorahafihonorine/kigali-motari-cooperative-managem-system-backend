package com.kigalimotari.cooperative.service;

import com.kigalimotari.cooperative.model.FareReceipt;
import com.kigalimotari.cooperative.model.Ride;
import com.kigalimotari.cooperative.model.RideStatus;
import com.kigalimotari.cooperative.repository.FareReceiptRepository;
import com.kigalimotari.cooperative.repository.RideRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FareReceiptService {

    private final FareReceiptRepository fareReceiptRepository;
    private final RideRepository rideRepository;

    public FareReceiptService(FareReceiptRepository fareReceiptRepository,
                              RideRepository rideRepository) {
        this.fareReceiptRepository = fareReceiptRepository;
        this.rideRepository = rideRepository;
    }

    // E1
    public String saveFareReceipt(UUID rideId, FareReceipt body) {
        Optional<Ride> rideOpt = rideRepository.findById(rideId);
        if (rideOpt.isEmpty()) {
            return "The ride with that id does not exist";
        }
        Ride ride = rideOpt.get();
        if (ride.getStatus() != RideStatus.DROPPED_OFF) {
            return "Fare receipts are only for DROPPED_OFF rides";
        }
        body.setId(null);
        body.setPaidDate(LocalDate.now());
        body.setRide(ride);
        fareReceiptRepository.save(body);
        return "Fare receipt saved successfully";
    }

    // E2
    public List<FareReceipt> findByPassengerPhone(String phone) {
        return fareReceiptRepository.findByPassengerPhone(phone);
    }

    // E3
    public String deleteFareReceipt(UUID id) {
        if (!fareReceiptRepository.existsById(id)) {
            return "The fare receipt with that id does not exist";
        }
        fareReceiptRepository.deleteById(id);
        return "Fare receipt deleted successfully";
    }
}
