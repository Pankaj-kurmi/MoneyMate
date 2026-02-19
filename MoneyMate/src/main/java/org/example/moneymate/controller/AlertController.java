package org.example.moneymate.controller;

import lombok.RequiredArgsConstructor;
import org.example.moneymate.dto.FinancialAlertDTO;
import org.example.moneymate.entities.ProfileEntity;
import org.example.moneymate.service.AlertService;
import org.example.moneymate.service.ProfileService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alerts")
@RequiredArgsConstructor
public class AlertController {
    private final AlertService alertService;
    private final ProfileService profileService;
    @PostMapping("/generate")
    public String generateNow() {
        ProfileEntity profile = profileService.getCurrentProfile();
        alertService.generateAlertsForUser(profile);
        return "Alerts generated";
    }


    @GetMapping
    public List<FinancialAlertDTO> getMyAlerts() {
        ProfileEntity profile = profileService.getCurrentProfile();

        return alertService.getMyAlerts(profile.getId())
                .stream()
                .map(a -> new FinancialAlertDTO(
                        a.getId(),
                        a.getType(),
                        a.getTitle(),
                        a.getMessage(),
                        a.isReadFlag(),
                        a.getCreatedAt()
                ))
                .toList();
    }

    @PostMapping("/{id}/read")
    public String markAsRead(@PathVariable Long id) {
        alertService.markAsRead(id);
        return "Marked as read";
    }
}
