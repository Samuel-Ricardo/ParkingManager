package com.parking.manager.controllers;

import com.parking.manager.AppProperties;
import com.parking.manager.MyBean;
import com.parking.manager.dto.ParkingSpotDTO;
import com.parking.manager.models.ParkingSpotModel;
import com.parking.manager.services.ParkingSpotService;
import com.parking.manager.services.impl.ParkingSpotServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
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
@Scope("singleton")
@PropertySource("classpath:custom.properties")
public class ParkingSpotController {
    @Autowired
//    @Qualifier("parkingSpotServiceImpl")
    private ParkingSpotService service;

    @Autowired
    private MyBean myBean;

//    @Autowired
//    private LazyBean lazyBean;

    @Autowired
    private AppProperties appProperties;

    @Value("${app.name}")
    private String appName;

    @Value("${app.port}")
    private String appPort;

    @Value("${app.host}")
    private String appHost;

    @Value("${message}")
    private String message;

    public ParkingSpotController() {
        System.out.println("ParkingSpotController created!!!");
    }


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
    //    @RequestMapping(value = "/parking-spot", method = RequestMethod.GET)
    public ResponseEntity<Page<ParkingSpotModel>> getAll(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {

//        System.out.println("App Name: " + appName);
//        System.out.println("App Port: " + appPort);
//        System.out.println("App Host: " + appHost);
//        myBean.method();
        System.out.println("App Name: " + appProperties.getName());
        System.out.println("App Port: " + appProperties.getPort());
        System.out.println("App Host: " + appProperties.getHost());

        System.out.println(message);

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
