package org.example.moneymate.controller;

import lombok.RequiredArgsConstructor;
import org.example.moneymate.entities.CreditAccountEntity;
import org.example.moneymate.entities.ProfileEntity;
import org.example.moneymate.repositry.CreditAccountRepository;
import org.example.moneymate.service.ProfileService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/credit/accounts")
@RequiredArgsConstructor
public class CreditAccountController {
    private final CreditAccountRepository accountRepo;
    private final ProfileService profileService;

    @PostMapping
    public CreditAccountEntity createAccount(@RequestBody CreditAccountEntity account) {
        ProfileEntity profile = profileService.getCurrentProfile();
        account.setProfile(profile);
        return accountRepo.save(account);
    }

    @GetMapping
    public List<CreditAccountEntity> getMyAccounts() {
        ProfileEntity profile = profileService.getCurrentProfile();
        return accountRepo.findByProfileId(profile.getId());
    }
}
