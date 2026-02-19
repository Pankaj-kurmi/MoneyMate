package org.example.moneymate.Scheduler;

import lombok.RequiredArgsConstructor;
import org.example.moneymate.entities.ProfileEntity;
import org.example.moneymate.repositry.ProfileRepositry;
import org.example.moneymate.service.AlertService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertScheduler {
    private final ProfileRepositry profileRepositry;
    private final AlertService alertService;

    // Run every day at 9 AM
    @Scheduled(cron = "0 0 9 * * ?")
    public void generateDailyAlerts() {
        List<ProfileEntity> profiles = profileRepositry.findAll();
        for (ProfileEntity profile : profiles) {
            alertService.generateAlertsForUser(profile);
        }
    }
}
