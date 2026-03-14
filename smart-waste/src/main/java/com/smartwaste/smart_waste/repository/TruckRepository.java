package com.smartwaste.smart_waste.repository;

import com.smartwaste.smart_waste.entity.Truck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TruckRepository extends JpaRepository<Truck, Long> {
}