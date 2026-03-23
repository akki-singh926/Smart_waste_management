package com.smartwaste.smart_waste.service;

import com.smartwaste.smart_waste.entity.Bin;
import com.smartwaste.smart_waste.entity.Route;
import com.smartwaste.smart_waste.entity.Truck;
import com.smartwaste.smart_waste.entity.User;
import com.smartwaste.smart_waste.repository.BinRepository;
import com.smartwaste.smart_waste.repository.RouteRepository;
import com.smartwaste.smart_waste.repository.TruckRepository;
import com.smartwaste.smart_waste.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteService {

    private final BinRepository binRepository;
    private final TruckRepository truckRepository;
    private final RouteRepository routeRepository;
    private final UserRepository userRepository;
    private final EmailService emailService; // ✅ inject this

    public RouteService(BinRepository binRepository,
                        TruckRepository truckRepository,
                        RouteRepository routeRepository,
                        UserRepository userRepository,
                        EmailService emailService) { // ✅ FIXED constructor

        this.binRepository = binRepository;
        this.truckRepository = truckRepository;
        this.routeRepository = routeRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    private double calculateDistance(double lat1, double lon1,
                                     double lat2, double lon2) {

        double latDiff = lat1 - lat2;
        double lonDiff = lon1 - lon2;

        return Math.sqrt(latDiff * latDiff + lonDiff * lonDiff);
    }

    public Route generateRoute(){

        List<Bin> bins = binRepository.findByStatus("NEEDS_PICKUP");

        if(bins.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.OK,
                    "No bins require pickup"
            );
        }

        // ✅ Select least loaded truck
        Truck truck = truckRepository.findAllByOrderByCurrentLoadAsc().get(0);

        // ✅ Update load (dummy for now)
        truck.setCurrentLoad(truck.getCurrentLoad() + 50);
        truckRepository.save(truck);

        Route route = new Route();
        route.setTruckId(truck.getId());
        route.setDriverId(truck.getDriverId());

        double currentLat = 0;
        double currentLon = 0;

        List<Bin> orderedBins = new ArrayList<>();

        while(!bins.isEmpty()){
            Bin nearest = null;
            double minDistance = Double.MAX_VALUE;

            for(Bin bin : bins){
                double distance = calculateDistance(
                        currentLat,
                        currentLon,
                        bin.getLatitude(),
                        bin.getLongitude()
                );

                if(distance < minDistance){
                    minDistance = distance;
                    nearest = bin;
                }
            }

            orderedBins.add(nearest);
            currentLat = nearest.getLatitude();
            currentLon = nearest.getLongitude();
            bins.remove(nearest);
        }

        // ✅ Human-readable route
        String routeString = orderedBins.stream()
                .map(Bin::getBinId)
                .collect(Collectors.joining(" -> "));

        // ✅ Numeric IDs
        String numericIdString = orderedBins.stream()
                .map(bin -> String.valueOf(bin.getId()))
                .collect(Collectors.joining(" -> "));

        // ✅ Coordinates
        String coordinateString = orderedBins.stream()
                .map(bin -> bin.getLatitude() + "," + bin.getLongitude())
                .collect(Collectors.joining(" -> "));

        route.setOrderedBinIds(routeString);
        route.setOrderedNumericIds(numericIdString);
        route.setOrderedCoordinates(coordinateString);
        route.setEstimatedDistance(orderedBins.size() * 2);
        route.setStatus("ASSIGNED");

        // ✅ Fetch driver safely
        User driver = userRepository.findById(truck.getDriverId())
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        // ✅ Send email
        emailService.sendEmail(
                driver.getEmail(),
                "Route Assigned",
                "Hello,\n\nYou have been assigned a new route:\n\n" + routeString
        );

        return routeRepository.save(route);
    }
}