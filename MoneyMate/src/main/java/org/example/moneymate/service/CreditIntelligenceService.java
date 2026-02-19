package org.example.moneymate.service;

import lombok.RequiredArgsConstructor;
import org.example.moneymate.creditengine.CreditScoringEngine;
import org.example.moneymate.entities.*;
import org.example.moneymate.repositry.CreditAccountRepository;
import org.example.moneymate.repositry.CreditPaymentRepository;
import org.example.moneymate.repositry.CreditRiskSnapshotRepository;
import org.example.moneymate.repositry.CreditScoreHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditIntelligenceService {
    private final CreditAccountRepository accountRepo;
    private final CreditPaymentRepository paymentRepo;
    private final CreditScoreHistoryRepository historyRepo;
    private final CreditRiskSnapshotRepository snapshotRepo;
    private final CreditScoringEngine scoringEngine;

    @Transactional
    public CreditRiskSnapshotEntity recalculate(ProfileEntity profile) {

        List<CreditAccountEntity> accounts = accountRepo.findByProfileId(profile.getId());

        double totalLimit = 0;
        double totalBalance = 0;

        for (CreditAccountEntity acc : accounts) {
            if (acc.getType() == CreditAccountType.CREDIT_CARD) {
                totalLimit += acc.getCreditLimit() != null ? acc.getCreditLimit() : 0;
                totalBalance += acc.getCurrentBalance() != null ? acc.getCurrentBalance() : 0;
            }
        }

        double utilization = totalLimit > 0 ? (totalBalance / totalLimit) : 0.0;

        LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6);
        List<CreditPaymentEntity> recentPayments =
                paymentRepo.findRecentPayments(profile.getId(), sixMonthsAgo);

        int missedPayments = (int) recentPayments.stream().filter(CreditPaymentEntity::isMissed).count();

        int score = scoringEngine.calculateScore(utilization, missedPayments);
        String risk = scoringEngine.calculateRiskLevel(score, missedPayments, utilization);

        // Save history
        CreditScoreHistoryEntity history = new CreditScoreHistoryEntity();
        history.setProfile(profile);
        history.setScore(score);
        history.setDate(LocalDate.now());
        historyRepo.save(history);

        // Save snapshot
        CreditRiskSnapshotEntity snapshot = snapshotRepo.findByProfileId(profile.getId())
                .orElse(new CreditRiskSnapshotEntity());

        snapshot.setProfile(profile);
        snapshot.setScore(score);
        snapshot.setRiskLevel(risk);
        snapshot.setUtilization(utilization);
        snapshot.setMissedPaymentsLast6Months(missedPayments);
        snapshot.setCalculatedAt(LocalDate.now());

        return snapshotRepo.save(snapshot);
    }
}
