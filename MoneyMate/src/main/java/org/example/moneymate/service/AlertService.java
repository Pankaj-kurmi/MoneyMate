package org.example.moneymate.service;

import lombok.RequiredArgsConstructor;
import org.example.moneymate.Alertengine.AlertRuleEngine;
import org.example.moneymate.entities.CreditRiskSnapshotEntity;
import org.example.moneymate.entities.FinancialAlertEntity;
import org.example.moneymate.entities.ProfileEntity;
import org.example.moneymate.repositry.CreditRiskSnapshotRepository;
import org.example.moneymate.repositry.FinancialAlertRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertService {
    private final FinancialAlertRepository alertRepo;
    private final AlertRuleEngine ruleEngine;
    private final CreditRiskSnapshotRepository creditSnapRepo;
    private final FinancialHealthService financialHealthService;
    private final ExpenseService expenseService;
    private final IncomeService incomeService;

    @Transactional
    public void generateAlertsForUser(ProfileEntity profile) {

        double totalExpense = expenseService.getTotalForCurrentMonth(profile.getId());
        double totalIncome = incomeService.getTotalForCurrentMonth(profile.getId());
        double spendingRatio = totalIncome > 0 ? (totalExpense / totalIncome) : 1.0;

        CreditRiskSnapshotEntity creditSnap = creditSnapRepo.findByProfileId(profile.getId())
                .orElseThrow(); // or auto-create like before

        int healthScore = financialHealthService.calculateForCurrentUser().getScore();

        var suggestions = ruleEngine.evaluate(spendingRatio, creditSnap, healthScore);

        for (var s : suggestions) {
            FinancialAlertEntity alert = new FinancialAlertEntity();
            alert.setProfile(profile);
            alert.setType(s.type);
            alert.setTitle(s.title);
            alert.setMessage(s.message);
            alert.setReadFlag(false);
            alert.setCreatedAt(LocalDateTime.now());

            alertRepo.save(alert);
        }
    }

    public List<FinancialAlertEntity> getMyAlerts(Long profileId) {
        return alertRepo.findByProfileIdOrderByCreatedAtDesc(profileId);
    }

    @Transactional
    public void markAsRead(Long alertId) {
        FinancialAlertEntity alert = alertRepo.findById(alertId).orElseThrow();
        alert.setReadFlag(true);
        alertRepo.save(alert);
    }
}
