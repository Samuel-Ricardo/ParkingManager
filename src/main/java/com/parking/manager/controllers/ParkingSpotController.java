package com.parking.manager.controllers;

import com.parking.manager.services.ParkingSpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking/spots")
public class ParkingSpotController {
    @Autowired
    private ParkingSpotService service;
}
