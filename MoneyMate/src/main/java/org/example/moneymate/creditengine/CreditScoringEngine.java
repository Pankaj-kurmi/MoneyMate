package org.example.moneymate.creditengine;

import org.springframework.stereotype.Component;

@Component
public class CreditScoringEngine {
    public int calculateScore(double utilization, int missedPayments) {
        int score = 750;

        // Missed payments penalty
        score -= missedPayments * 50;

        // Utilization impact
        if (utilization < 0.3) {
            score += 20;
        } else if (utilization > 0.8) {
            score -= 80;
        } else if (utilization > 0.5) {
            score -= 40;
        }

        // Clamp score between 300 and 900
        if (score < 300) score = 300;
        if (score > 900) score = 900;

        return score;
    }
    public String calculateRiskLevel(int score, int missedPayments, double utilization) {
        if (score < 550 || missedPayments >= 3 || utilization > 0.8) {
            return "HIGH";
        }
        if (score < 700 || utilization > 0.5) {
            return "MEDIUM";
        }
        return "LOW";
    }
}
