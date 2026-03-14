package com.smartwaste.smart_waste.repository;

import com.smartwaste.smart_waste.entity.Bin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BinRepository extends JpaRepository<Bin, Long> {
}