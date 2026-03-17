package com.smartwaste.smart_waste.repository;

import com.smartwaste.smart_waste.entity.CollectionLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CollectionLogRepository extends JpaRepository<CollectionLog, Long> {

    List<CollectionLog> findByCollectedAtAfter(LocalDateTime time);
}