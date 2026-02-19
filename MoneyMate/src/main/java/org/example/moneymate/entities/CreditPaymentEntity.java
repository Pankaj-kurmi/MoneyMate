package org.example.moneymate.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "tbl_credit_payments")
public class CreditPaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dueDate;
    private LocalDate paidDate;

    private Double amount;

    private boolean missed; // true if paidDate > dueDate or null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_account_id")
    private CreditAccountEntity creditAccount;
}

