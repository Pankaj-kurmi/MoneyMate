package org.example.moneymate.controller;


import lombok.RequiredArgsConstructor;
import org.example.moneymate.entities.CreditAccountEntity;
import org.example.moneymate.entities.CreditPaymentEntity;
import org.example.moneymate.repositry.CreditAccountRepository;
import org.example.moneymate.repositry.CreditPaymentRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/credit/payments")
@RequiredArgsConstructor
public class CreditPaymentController {
    private final CreditPaymentRepository paymentRepo;
    private final CreditAccountRepository accountRepo;

    @PostMapping
    public CreditPaymentEntity addPayment(@RequestBody CreditPaymentEntity payment) {
        CreditAccountEntity account = accountRepo.findById(payment.getCreditAccount().getId())
                .orElseThrow();
        payment.setCreditAccount(account);
        return paymentRepo.save(payment);
    }
}
