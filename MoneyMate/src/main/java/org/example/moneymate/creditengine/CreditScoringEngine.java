package org.example.moneymate.creditengine;

import org.example.moneymate.entities.ProfileEntity;
import org.example.moneymate.repositry.ProfileRepositry;
import org.example.moneymate.service.ExpenseService;
import org.springframework.stereotype.Component;

@Component
public class CreditScoringEngine {
    private final ProfileRepositry profileRepositry;
    private final ExpenseService expenseService;

    public CreditScoringEngine(ProfileRepositry profileRepositry, ExpenseService expenseService) {
        this.profileRepositry = profileRepositry;
        this.expenseService = expenseService;
    }

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

    // Get credit limit for user
    public double getCreditLimit(Long profileId) {
        ProfileEntity profile = profileRepositry.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        // Fallback if null
        if (profile.getCreditLimit() == null || profile.getCreditLimit() <= 0) {
            return 300000; // default limit (example)
        }

        return profile.getCreditLimit();
    }

    // Example: calculate current credit score (simple rule-based)
    public int calculateCreditScore(Long profileId) {

        double debt = expenseService.getTotalDebt(profileId);
        double limit = getCreditLimit(profileId);

        // Base score
        int score = 700;

        // Utilization impact
        double utilization = debt / limit;

        if (utilization > 0.8) {
            score -= 80;
        } else if (utilization > 0.5) {
            score -= 40;
        } else if (utilization > 0.3) {
            score -= 20;
        } else {
            score += 10;
        }

        // Clamp
        if (score < 300) score = 300;
        if (score > 900) score = 900;

        return score;
    }
}
