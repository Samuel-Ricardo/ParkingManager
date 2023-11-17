package com.parking.manager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ParkingSpotDTO(
        @NotBlank
        String parkingSpotNumber,
        @NotBlank
        @Size(max = 7)
        String licensePlate,
        @NotBlank
        String brand,
        @NotBlank
        String model,
        @NotBlank
        String color,
        @NotBlank
        String responsibleName,
        @NotBlank
        String apartment,
        @NotBlank
        String block

) {}
