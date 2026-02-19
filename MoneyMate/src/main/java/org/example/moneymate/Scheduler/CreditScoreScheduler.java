package org.example.moneymate.Scheduler;

import lombok.RequiredArgsConstructor;
import org.example.moneymate.entities.ProfileEntity;
import org.example.moneymate.repositry.ProfileRepositry;
import org.example.moneymate.service.CreditIntelligenceService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditScoreScheduler {
    private final ProfileRepositry profileRepositry;
    private final CreditIntelligenceService creditIntelligenceService;

    // Runs every day at 2 AM
    @Scheduled(cron = "0 0 2 * * ?")
    public void recalculateAllCreditScores() {
        List<ProfileEntity> profiles = profileRepositry.findAll();

        for (ProfileEntity profile : profiles) {
            creditIntelligenceService.recalculate(profile);
        }
    }
}
