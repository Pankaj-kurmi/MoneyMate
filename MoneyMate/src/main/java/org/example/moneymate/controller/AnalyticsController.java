package org.example.moneymate.controller;


import lombok.RequiredArgsConstructor;
import org.example.moneymate.dto.AnalyticsResponseDTO;
import org.example.moneymate.service.AnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    @GetMapping
    public ResponseEntity<AnalyticsResponseDTO> getAnalytics() {
        return ResponseEntity.ok(analyticsService.getAnalyticsForCurrentUser());
    }
}
