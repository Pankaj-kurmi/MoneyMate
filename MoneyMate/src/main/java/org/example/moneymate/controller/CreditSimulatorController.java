package org.example.moneymate.controller;

import lombok.RequiredArgsConstructor;
import org.example.moneymate.dto.CreditSimulationRequest;
import org.example.moneymate.dto.CreditSimulationResponse;
import org.example.moneymate.service.CreditSimulatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/credit-simulator")
@RequiredArgsConstructor
public class CreditSimulatorController {
    private final CreditSimulatorService creditSimulatorService;

    @PostMapping("/simulate")
    public ResponseEntity<CreditSimulationResponse> simulate(
            @RequestBody CreditSimulationRequest request
    ) {
        return ResponseEntity.ok(creditSimulatorService.simulate(request));
    }
}
