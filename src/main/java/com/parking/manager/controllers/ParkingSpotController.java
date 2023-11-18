package com.parking.manager.controllers;

import com.parking.manager.dto.ParkingSpotDTO;
import com.parking.manager.models.ParkingSpotModel;
import com.parking.manager.services.impl.ParkingSpotServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking/spots")
public class ParkingSpotController {
    @Autowired
    private ParkingSpotServiceImpl service;

    @PostMapping
    public ResponseEntity<Object> save(
            @RequestBody
            @Valid
            ParkingSpotDTO DTO
    ) {

        if(this.service.alreadyExists(DTO)) return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Data Already Exists");

        var spot = new ParkingSpotModel();
        BeanUtils.copyProperties(DTO, spot);
        spot.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(spot));
    }

    @GetMapping
    public ResponseEntity<Page<ParkingSpotModel>> getAll(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOne(
            @PathVariable(value = "id")
            UUID id
    ){
        Optional<ParkingSpotModel> spotO = service.findById(id);
        return spotO.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot Not Found")
                : ResponseEntity.status(HttpStatus.OK).body(spotO.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(
            @PathVariable(value = "id")
            UUID id
    ) {
        var oSpot = service.findById(id);
        if (oSpot.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found.");

        service.delete(oSpot.get());
        return  ResponseEntity.status(HttpStatus.OK).body("Parking Spot deleted successfully.");
    }

    public ResponseEntity<Object> update(
            @PathVariable(value = "id") UUID id,
            @RequestBody @Valid ParkingSpotDTO DTO
    ) {

        var oSpot = service.findById(id);
        if(oSpot.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found.");

        var model = new ParkingSpotModel();
        BeanUtils.copyProperties(DTO, model);

        model.setId(oSpot.get().getId());
        model.setRegistrationDate(oSpot.get().getRegistrationDate());

        return ResponseEntity.status(HttpStatus.OK).body(service.save(model));
    }

}
