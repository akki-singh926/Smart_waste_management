package com.smartwaste.smart_waste.service;

import com.smartwaste.smart_waste.entity.Route;
import com.smartwaste.smart_waste.repository.RouteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService {

    private final RouteRepository routeRepository;

    public DriverService(RouteRepository routeRepository){
        this.routeRepository = routeRepository;
    }

    public List<Route> getDriverRoutes(Long truckId){
        return routeRepository.findByTruckId(truckId);
    }
}