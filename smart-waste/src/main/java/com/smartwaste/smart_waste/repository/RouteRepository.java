package com.smartwaste.smart_waste.repository;

import com.smartwaste.smart_waste.entity.Bin;
import com.smartwaste.smart_waste.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Long> {
    List<Route> findByTruckId(Long truckId);
}