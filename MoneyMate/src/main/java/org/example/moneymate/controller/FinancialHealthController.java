package org.example.moneymate.controller;

import lombok.RequiredArgsConstructor;
import org.example.moneymate.dto.FinancialHealthScoreDTO;
import org.example.moneymate.service.FinancialHealthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
@RequiredArgsConstructor
public class FinancialHealthController {
    private final FinancialHealthService financialHealthService;

    @GetMapping("/score")
    public FinancialHealthScoreDTO getMyHealthScore() {
        return financialHealthService.calculateForCurrentUser();
    }
}
