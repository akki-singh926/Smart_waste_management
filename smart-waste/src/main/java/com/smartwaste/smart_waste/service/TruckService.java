package com.smartwaste.smart_waste.service;

import com.smartwaste.smart_waste.entity.Truck;
import com.smartwaste.smart_waste.repository.TruckRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TruckService {

    private final TruckRepository truckRepository;

    public TruckService(TruckRepository truckRepository){
        this.truckRepository = truckRepository;
    }

    public Truck createTruck(Truck truck){
        return truckRepository.save(truck);
    }

    public List<Truck> getAllTrucks(){
        return truckRepository.findAll();
    }
}