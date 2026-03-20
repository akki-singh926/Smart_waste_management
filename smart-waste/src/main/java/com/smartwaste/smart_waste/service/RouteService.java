package com.smartwaste.smart_waste.service;

import com.smartwaste.smart_waste.entity.Bin;
import com.smartwaste.smart_waste.entity.Route;
import com.smartwaste.smart_waste.entity.Truck;
import com.smartwaste.smart_waste.repository.BinRepository;
import com.smartwaste.smart_waste.repository.RouteRepository;
import com.smartwaste.smart_waste.repository.TruckRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteService {

    private final BinRepository binRepository;
    private final TruckRepository truckRepository;
    private final RouteRepository routeRepository;

    public RouteService(BinRepository binRepository,
                        TruckRepository truckRepository,
                        RouteRepository routeRepository) {

        this.binRepository = binRepository;
        this.truckRepository = truckRepository;
        this.routeRepository = routeRepository;
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

        Truck truck = truckRepository.findAll().get(0);

        double currentLat = 0;
        double currentLon = 0;

        List<Bin> orderedBins = new java.util.ArrayList<>();

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

        // 1. String for Driver Display (e.g., "BIN001 -> BIN2")
        String routeString = orderedBins.stream()
                .map(Bin::getBinId)
                .collect(Collectors.joining(" -> "));

        // 2. NEW: String for Database IDs (e.g., "1 -> 3")
        String numericIdString = orderedBins.stream()
                .map(bin -> String.valueOf(bin.getId()))
                .collect(Collectors.joining(" -> "));
        String coordinateString = orderedBins.stream()
                .map(bin -> bin.getLatitude() + "," + bin.getLongitude())
                .collect(Collectors.joining(" -> "));


        Route route = new Route();
        route.setTruckId(truck.getId());
        route.setOrderedBinIds(routeString);

        // 3. NEW: Populate the field you already have in your Route entity
        route.setOrderedNumericIds(numericIdString);
        route.setOrderedCoordinates(coordinateString);

        route.setEstimatedDistance(orderedBins.size() * 2);
        route.setStatus("ASSIGNED");

        return routeRepository.save(route);
    }
}