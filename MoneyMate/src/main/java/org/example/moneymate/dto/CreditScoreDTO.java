package org.example.moneymate.dto;

public class CreditScoreDTO {
    private int score;
    private String riskLevel;
    private double utilization;
    private int missedPaymentsLast6Months;

    public CreditScoreDTO(int score, String riskLevel, double utilization, int missedPaymentsLast6Months) {
        this.score = score;
        this.riskLevel = riskLevel;
        this.utilization = utilization;
        this.missedPaymentsLast6Months = missedPaymentsLast6Months;
    }

    public int getScore() {
        return score;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public double getUtilization() {
        return utilization;
    }

    public int getMissedPaymentsLast6Months() {
        return missedPaymentsLast6Months;
    }
}
