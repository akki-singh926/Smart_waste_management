package com.smartwaste.smart_waste.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CollectionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long binId;

    private Long truckId;

    private LocalDateTime collectedAt;

    public CollectionLog(){}

    public Long getId() {
        return id;
    }

    public Long getBinId() {
        return binId;
    }

    public void setBinId(Long binId) {
        this.binId = binId;
    }

    public Long getTruckId() {
        return truckId;
    }

    public void setTruckId(Long truckId) {
        this.truckId = truckId;
    }

    public LocalDateTime getCollectedAt() {
        return collectedAt;
    }

    public void setCollectedAt(LocalDateTime collectedAt) {
        this.collectedAt = collectedAt;
    }
}