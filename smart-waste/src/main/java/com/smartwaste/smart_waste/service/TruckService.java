package com.smartwaste.smart_waste.service;

import com.smartwaste.smart_waste.entity.Truck;
import com.smartwaste.smart_waste.entity.User;
import com.smartwaste.smart_waste.repository.TruckRepository;
import com.smartwaste.smart_waste.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TruckService {

    private final TruckRepository truckRepository;
    private final UserRepository userRepository;

    public TruckService(TruckRepository truckRepository,
                        UserRepository userRepository){
        this.truckRepository = truckRepository;
        this.userRepository = userRepository;
    }

    // ✅ Create Truck with validation
    public Truck createTruck(Truck truck){

        // Validate driver
        if(truck.getDriverId() != null){

            User driver = userRepository.findById(truck.getDriverId())
                    .orElseThrow(() -> new RuntimeException("Driver not found"));

            if(!driver.getRole().name().equals("DRIVER")){
                throw new RuntimeException("Assigned user is not a driver");
            }
        }

        // Default load
        if(truck.getCurrentLoad() == 0){
            truck.setCurrentLoad(0);
        }

        return truckRepository.save(truck);
    }

    // ✅ Get all trucks
    public List<Truck> getAllTrucks(){
        return truckRepository.findAll();
    }

    // ✅ Assign driver to existing truck
    public Truck assignDriver(Long truckId, Long driverId){

        Truck truck = truckRepository.findById(truckId)
                .orElseThrow(() -> new RuntimeException("Truck not found"));

        User driver = userRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        if(!driver.getRole().name().equals("DRIVER")){
            throw new RuntimeException("User is not a driver");
        }

        truck.setDriverId(driverId);

        return truckRepository.save(truck);
    }
}