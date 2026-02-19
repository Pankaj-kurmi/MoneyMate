package org.example.moneymate.service;

import lombok.RequiredArgsConstructor;
import org.example.moneymate.dto.FinancialHealthScoreDTO;
import org.example.moneymate.entities.CreditRiskSnapshotEntity;
import org.example.moneymate.entities.ProfileEntity;
import org.example.moneymate.healthengine.FinancialHealthEngine;
import org.example.moneymate.repositry.CreditRiskSnapshotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FinancialHealthService {
    private final ExpenseService expenseService;
    private final IncomeService incomeService; // if you have it
    private final CreditRiskSnapshotRepository snapshotRepo;
    private final FinancialHealthEngine engine;
    private final ProfileService profileService;

    @Transactional(readOnly = true)
    public FinancialHealthScoreDTO calculateForCurrentUser() {
        ProfileEntity profile = profileService.getCurrentProfile();

        // 1. Get monthly income & expenses (example: current month)
        double totalExpense = expenseService.getTotalForCurrentMonth(profile.getId());
        double totalIncome = incomeService.getTotalForCurrentMonth(profile.getId());

        double spendingRatio = totalIncome > 0 ? (totalExpense / totalIncome) : 1.0;

        // 2. Get credit snapshot (auto-create if missing)
        CreditRiskSnapshotEntity snap = snapshotRepo.findByProfileId(profile.getId())
                .orElseThrow(); // or auto-recalculate like before

        double utilization = snap.getUtilization();
        String riskLevel = snap.getRiskLevel();

        // 3. Calculate score
        int score = engine.calculateScore(spendingRatio, utilization, riskLevel);
        String status = engine.healthStatus(score);

        return new FinancialHealthScoreDTO(
                score,
                status,
                spendingRatio,
                utilization,
                riskLevel
        );
    }
}
