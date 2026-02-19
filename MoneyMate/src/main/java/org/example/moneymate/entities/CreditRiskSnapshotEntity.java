package org.example.moneymate.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "tbl_credit_risk_snapshot")
public class CreditRiskSnapshotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int score;
    private String riskLevel; // LOW, MEDIUM, HIGH

    private double utilization; // 0.0 - 1.0
    private int missedPaymentsLast6Months;

    private LocalDate calculatedAt;

    @OneToOne
    @JoinColumn(name = "profile_id")
    private ProfileEntity profile;
}
