package com.smartwaste.smart_waste.controller;

import com.smartwaste.smart_waste.entity.Route;
import com.smartwaste.smart_waste.service.RouteService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/routes")
@CrossOrigin(origins = "http://localhost:5173")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService){
        this.routeService = routeService;
    }

    @PostMapping("/optimize")
    public Route generateRoute(){
        return routeService.generateRoute();
    }
}