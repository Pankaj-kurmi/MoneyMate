package org.example.moneymate.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "tbl_credit_accounts")
public class CreditAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // e.g. HDFC Card, Bike Loan

    @Enumerated(EnumType.STRING)
    private CreditAccountType type; // CREDIT_CARD, LOAN

    private Double creditLimit;     // null for loans
    private Double currentBalance;  // for cards
    private Double emiAmount;       // for loans

    private LocalDate openedDate;
    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private ProfileEntity profile;
}
