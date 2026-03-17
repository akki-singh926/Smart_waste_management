package com.smartwaste.smart_waste.controller;

import com.smartwaste.smart_waste.service.AnalyticsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/analytics")
@CrossOrigin(origins = "http://localhost:5173")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/daily")
    public Map<String, Long> getDailyAnalytics() {

        Map<String, Long> response = new HashMap<>();

        response.put("todayCollections",
                analyticsService.getTodayCollections());

        response.put("totalCollections",
                analyticsService.getTotalCollections());

        return response;
    }
}