package com.parking.manager.services;

import com.parking.manager.models.ParkingSpotModel;
import com.parking.manager.repositories.ParkingSpotRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ParkingSpotService {

    final ParkingSpotRepository repository;

    public ParkingSpotService(ParkingSpotRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ParkingSpotModel save(ParkingSpotModel spot) {
        return repository.save(spot);
    }

     public boolean existsByLicensePlate(String licensePlate) {
         return repository.existsByLicensePlate(licensePlate);
     }

}
