package org.example.moneymate.service;

import lombok.RequiredArgsConstructor;
import org.example.moneymate.creditengine.CreditScoringEngine;
import org.example.moneymate.dto.CreditSimulationRequest;
import org.example.moneymate.dto.CreditSimulationResponse;
import org.example.moneymate.entities.ProfileEntity;
import org.example.moneymate.util.CreditScoreCalculator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditSimulatorService {
    private final ProfileService profileService;
    private final ExpenseService expenseService; // or TransactionService
    private final CreditScoringEngine creditEngineService; // your score calculator

    public CreditSimulationResponse simulate(CreditSimulationRequest request) {

        // 1. Get current user
        ProfileEntity profile = profileService.getCurrentProfile();

        // 2. Get real financial data
        double currentDebt = expenseService.getTotalDebt(profile.getId());
        double creditLimit = creditEngineService.getCreditLimit(profile.getId());

        int currentScore = creditEngineService.calculateCreditScore(profile.getId());

        // 3. Simulate
        int predictedScore = CreditScoreCalculator.simulate(
                currentScore,
                currentDebt,
                creditLimit,
                request.getNewLoanAmount(),
                request.isMissedPayment(),
                request.getNewCreditLimit()
        );

        int impact = predictedScore - currentScore;

        String message;
        if (impact > 0) {
            message = "This action is likely to improve your credit health.";
        } else if (impact < 0) {
            message = "This action may negatively impact your credit health.";
        } else {
            message = "This action is unlikely to change your credit score.";
        }

        return CreditSimulationResponse.builder()
                .currentScore(currentScore)
                .predictedScore(predictedScore)
                .impact(impact)
                .message(message)
                .build();
    }
}
