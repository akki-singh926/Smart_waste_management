package com.smartwaste.smart_waste.repository;

import com.smartwaste.smart_waste.entity.Bin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BinRepository extends JpaRepository<Bin, Long> {
    List<Bin> findByStatus(String needsPickup);

}