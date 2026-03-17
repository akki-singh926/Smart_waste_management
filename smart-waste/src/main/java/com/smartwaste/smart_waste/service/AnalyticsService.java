package com.smartwaste.smart_waste.service;

import com.smartwaste.smart_waste.entity.CollectionLog;
import com.smartwaste.smart_waste.repository.CollectionLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnalyticsService {

    private final CollectionLogRepository collectionLogRepository;

    public AnalyticsService(CollectionLogRepository collectionLogRepository) {
        this.collectionLogRepository = collectionLogRepository;
    }

    // Total collections today
    public long getTodayCollections() {

        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();

        List<CollectionLog> logs =
                collectionLogRepository.findByCollectedAtAfter(startOfDay);

        return logs.size();
    }

    // Total collections overall
    public long getTotalCollections() {

        return collectionLogRepository.count();
    }

}