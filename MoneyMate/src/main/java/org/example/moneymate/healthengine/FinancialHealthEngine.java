package org.example.moneymate.healthengine;

import org.springframework.stereotype.Component;

@Component
public class FinancialHealthEngine {
    public int calculateScore(double spendingRatio, double utilization, String creditRisk) {
        int score = 0;

        // Spending habits (0–40)
        if (spendingRatio <= 0.5) score += 40;
        else if (spendingRatio <= 0.7) score += 25;
        else if (spendingRatio <= 0.9) score += 10;

        // Debt / utilization (0–30)
        if (utilization < 0.3) score += 30;
        else if (utilization < 0.5) score += 20;
        else if (utilization < 0.8) score += 10;

        // Credit behavior (0–30)
        if ("LOW".equalsIgnoreCase(creditRisk)) score += 30;
        else if ("MEDIUM".equalsIgnoreCase(creditRisk)) score += 15;

        return Math.max(0, Math.min(100, score));
    }

    public String healthStatus(int score) {
        if (score >= 80) return "EXCELLENT";
        if (score >= 60) return "GOOD";
        if (score >= 40) return "FAIR";
        return "POOR";
    }
}
