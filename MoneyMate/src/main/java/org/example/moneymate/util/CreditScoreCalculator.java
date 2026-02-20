package org.example.moneymate.util;

public class CreditScoreCalculator {
    private CreditScoreCalculator() {}

    public static int simulate(
            int currentScore,
            double currentDebt,
            double creditLimit,
            double newLoanAmount,
            boolean missedPayment,
            Double newCreditLimit
    ) {
        int score = currentScore;

        // 1. Missed payment = heavy penalty
        if (missedPayment) {
            score -= 80;
        }

        // 2. New loan impact (utilization based)
        if (newLoanAmount > 0) {
            double newDebt = currentDebt + newLoanAmount;
            double utilization = newDebt / creditLimit;

            if (utilization > 0.8) {
                score -= 50;
            } else if (utilization > 0.5) {
                score -= 25;
            } else {
                score -= 10;
            }
        }

        // 3. Credit limit change
        if (newCreditLimit != null && newCreditLimit > 0) {
            if (newCreditLimit > creditLimit) {
                score += 20;
            } else if (newCreditLimit < creditLimit) {
                score -= 15;
            }
        }

        // Clamp score
        if (score < 300) score = 300;
        if (score > 900) score = 900;

        return score;
    }
}
