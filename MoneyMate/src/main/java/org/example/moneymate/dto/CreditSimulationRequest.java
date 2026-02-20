package org.example.moneymate.dto;

import lombok.Data;

@Data
public class CreditSimulationRequest {
    // What-if inputs only
    private double newLoanAmount;   // e.g. 100000
    private boolean missedPayment;  // true/false
    private Double newCreditLimit;  // nullable if not changing
}
