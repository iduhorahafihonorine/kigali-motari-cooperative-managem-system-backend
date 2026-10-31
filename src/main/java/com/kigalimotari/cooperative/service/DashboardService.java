package com.kigalimotari.cooperative.service;

import com.kigalimotari.cooperative.model.RideStatus;
import com.kigalimotari.cooperative.repository.FareReceiptRepository;
import com.kigalimotari.cooperative.repository.MotorcycleRepository;
import com.kigalimotari.cooperative.repository.PassengerRepository;
import com.kigalimotari.cooperative.repository.RideRepository;
import com.kigalimotari.cooperative.repository.RiderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class DashboardService {

    private final PassengerRepository passengerRepository;
    private final RiderRepository riderRepository;
    private final MotorcycleRepository motorcycleRepository;
    private final RideRepository rideRepository;
    private final FareReceiptRepository fareReceiptRepository;

    public DashboardService(PassengerRepository passengerRepository,
                            RiderRepository riderRepository,
                            MotorcycleRepository motorcycleRepository,
                            RideRepository rideRepository,
                            FareReceiptRepository fareReceiptRepository) {
        this.passengerRepository = passengerRepository;
        this.riderRepository = riderRepository;
        this.motorcycleRepository = motorcycleRepository;
        this.rideRepository = rideRepository;
        this.fareReceiptRepository = fareReceiptRepository;
    }

    /**
     * Bonus dashboard. LinkedHashMap keeps key order:
     * passengers, riders, freeMotorcycles, ridesToday, requested, fareReceipts
     */
    public Map<String, Long> summary() {
        Map<String, Long> map = new LinkedHashMap<>();
        map.put("passengers", passengerRepository.count());
        map.put("riders", riderRepository.count());
        // reuse C2 free-motorcycles query
        map.put("freeMotorcycles", (long) motorcycleRepository.findFreeMotorcycles().size());
        map.put("ridesToday", rideRepository.countByRideDate(LocalDate.now()));
        map.put("requested", rideRepository.countByStatus(RideStatus.REQUESTED));
        map.put("fareReceipts", fareReceiptRepository.count());
        return map;
    }
}
