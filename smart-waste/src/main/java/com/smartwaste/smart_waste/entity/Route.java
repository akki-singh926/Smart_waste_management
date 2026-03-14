package com.smartwaste.smart_waste.entity;

import jakarta.persistence.*;

@Entity
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long truckId;

    private String orderedBinIds;

    private double estimatedDistance;

    private String status;

    public Route(){}

    public Long getId() {
        return id;
    }

    public Long getTruckId() {
        return truckId;
    }

    public void setTruckId(Long truckId) {
        this.truckId = truckId;
    }

    public String getOrderedBinIds() {
        return orderedBinIds;
    }

    public void setOrderedBinIds(String orderedBinIds) {
        this.orderedBinIds = orderedBinIds;
    }

    public double getEstimatedDistance() {
        return estimatedDistance;
    }

    public void setEstimatedDistance(double estimatedDistance) {
        this.estimatedDistance = estimatedDistance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}