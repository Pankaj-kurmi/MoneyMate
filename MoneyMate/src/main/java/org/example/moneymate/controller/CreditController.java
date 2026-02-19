package org.example.moneymate.controller;

import lombok.RequiredArgsConstructor;
import org.example.moneymate.dto.CreditScoreDTO;
import org.example.moneymate.dto.CreditScoreTrendDTO;
import org.example.moneymate.entities.CreditRiskSnapshotEntity;
import org.example.moneymate.entities.ProfileEntity;
import org.example.moneymate.repositry.CreditRiskSnapshotRepository;
import org.example.moneymate.repositry.CreditScoreHistoryRepository;
import org.example.moneymate.service.CreditIntelligenceService;
import org.example.moneymate.service.ProfileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/credit")
@RequiredArgsConstructor
public class CreditController {

    private final CreditRiskSnapshotRepository snapshotRepo;
    private final CreditScoreHistoryRepository historyRepo;
    private final ProfileService profileService;
    private final CreditIntelligenceService creditIntelligenceService;


    @GetMapping("/score")
    public CreditScoreDTO getCurrentScore() {
        ProfileEntity profile = profileService.getCurrentProfile();
        CreditRiskSnapshotEntity snap = snapshotRepo.findByProfileId(profile.getId())
                .orElseGet(() -> creditIntelligenceService.recalculate(profile));


        return new CreditScoreDTO(
                snap.getScore(),
                snap.getRiskLevel(),
                snap.getUtilization(),
                snap.getMissedPaymentsLast6Months()
        );
    }

    @GetMapping("/score/history")
    public List<CreditScoreTrendDTO> getScoreHistory() {
        ProfileEntity profile = profileService.getCurrentProfile();
        return historyRepo.findByProfileIdOrderByDateAsc(profile.getId())
                .stream()
                .map(h -> new CreditScoreTrendDTO(h.getDate(), h.getScore()))
                .toList();
    }
}
