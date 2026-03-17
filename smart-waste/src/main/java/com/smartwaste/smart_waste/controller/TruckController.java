package com.smartwaste.smart_waste.controller;

import com.smartwaste.smart_waste.entity.Truck;
import com.smartwaste.smart_waste.service.TruckService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/trucks")
@CrossOrigin(origins = "http://localhost:5173")
public class TruckController {

    private final TruckService truckService;

    public TruckController(TruckService truckService){
        this.truckService = truckService;
    }

    @PostMapping
    public Truck createTruck(@RequestBody Truck truck){
        return truckService.createTruck(truck);
    }

    @GetMapping
    public List<Truck> getTrucks(){
        return truckService.getAllTrucks();
    }
}