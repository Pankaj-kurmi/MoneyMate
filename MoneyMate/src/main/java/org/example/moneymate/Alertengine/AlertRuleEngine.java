package org.example.moneymate.Alertengine;

import org.example.moneymate.entities.CreditRiskSnapshotEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AlertRuleEngine {
    public static class AlertSuggestion {
        public String type;   // INFO, WARNING, CRITICAL
        public String title;
        public String message;

        public AlertSuggestion(String type, String title, String message) {
            this.type = type;
            this.title = title;
            this.message = message;
        }
    }

    public List<AlertSuggestion> evaluate(double spendingRatio, CreditRiskSnapshotEntity creditSnap, int healthScore) {
        List<AlertSuggestion> alerts = new ArrayList<>();

        // 1. High spending alert
        if (spendingRatio > 0.8) {
            alerts.add(new AlertSuggestion(
                    "WARNING",
                    "High Spending Detected",
                    "You are spending more than 80% of your income. Try reducing discretionary expenses."
            ));
        }

        // 2. High credit utilization
        if (creditSnap.getUtilization() > 0.8) {
            alerts.add(new AlertSuggestion(
                    "CRITICAL",
                    "High Credit Utilization",
                    "Your credit utilization is above 80%. Pay down balances to improve your credit score."
            ));
        }

        // 3. Missed payments
        if (creditSnap.getMissedPaymentsLast6Months() > 0) {
            alerts.add(new AlertSuggestion(
                    "WARNING",
                    "Missed Payments Detected",
                    "You have missed payments recently. Set reminders and pay on time to avoid score drops."
            ));
        }

        // 4. Low financial health score
        if (healthScore < 40) {
            alerts.add(new AlertSuggestion(
                    "CRITICAL",
                    "Poor Financial Health",
                    "Your financial health score is low. Focus on reducing debt and controlling spending."
            ));
        } else if (healthScore < 60) {
            alerts.add(new AlertSuggestion(
                    "INFO",
                    "Improve Your Financial Health",
                    "You can improve your score by lowering debt and keeping spending under control."
            ));
        }

        return alerts;
    }
}
