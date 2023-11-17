package com.parking.manager.dto;

import jakarta.validation.constraints.NotBlank;

public record ParkingSpotDTO(
        @NotBlank
        String parkingSpotNumber,
        @NotBlank
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
