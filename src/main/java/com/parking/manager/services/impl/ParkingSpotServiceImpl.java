package com.parking.manager.services.impl;

import com.parking.manager.dto.ParkingSpotDTO;
import com.parking.manager.models.ParkingSpotModel;
import com.parking.manager.repositories.ParkingSpotRepository;
import com.parking.manager.services.ParkingSpotService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ParkingSpotServiceImpl implements ParkingSpotService {

    private final ParkingSpotRepository repository;

    public ParkingSpotServiceImpl(ParkingSpotRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ParkingSpotModel save(ParkingSpotModel spot) {
        return repository.save(spot);
    }

     public boolean existsByLicensePlate(String licensePlate) {
         return repository.existsByLicensePlate(licensePlate);
     }

     public boolean existsByParkingSpotNumber(String parkingSpotNumber) {
        return repository.existsByParkingSpotNumber(parkingSpotNumber);
     }

     public boolean existsByApartmentAndBlock(String apartment, String block) {
        return repository.existsByApartmentAndBlock(apartment, block);
     }

     @Transactional
     public boolean alreadyExists (ParkingSpotDTO DTO) {
        return this.existsByLicensePlate(DTO.licensePlate()) && this.existsByParkingSpotNumber(DTO.parkingSpotNumber()) && this.existsByApartmentAndBlock(DTO.apartment(), DTO.block());
     }

     public Page<ParkingSpotModel> findAll(Pageable pageable) {
        return repository.findAll(pageable);
     }

     public Optional<ParkingSpotModel> findById(UUID id) {
        return repository.findById(id);
     }

     @Transactional
     public void delete(ParkingSpotModel spot) {
        repository.delete(spot);
     }

}
