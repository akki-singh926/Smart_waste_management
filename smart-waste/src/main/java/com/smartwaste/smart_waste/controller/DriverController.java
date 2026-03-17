package com.smartwaste.smart_waste.controller;

import com.smartwaste.smart_waste.entity.Bin;
import com.smartwaste.smart_waste.entity.Route;
import com.smartwaste.smart_waste.service.BinService;
import com.smartwaste.smart_waste.service.DriverService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/driver")
@CrossOrigin(origins = "http://localhost:5173")
public class DriverController {

    private final DriverService driverService;
    private final BinService binService;

    public DriverController(DriverService driverService,BinService binService){
        this.driverService = driverService;
        this.binService=binService;
    }

    @GetMapping("/routes/{truckId}")
    public List<Route> getRoutes(@PathVariable Long truckId){
        return driverService.getDriverRoutes(truckId);
    }
    @PutMapping("/collect/{id}")
    public Bin collectBin(@PathVariable Long id){
        return binService.collectBin(id);
    }
}