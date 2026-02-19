package org.example.moneymate.dto;

import lombok.*;

@Data
@Getter
@Setter
@RequiredArgsConstructor

public class FinancialHealthScoreDTO {
    private int score;
    private String status;
    private double spendingRatio;
    private double debtUtilization;
    private String creditRisk;

    public FinancialHealthScoreDTO(int score, String status, double spendingRatio, double debtUtilization, String creditRisk) {
        this.score = score;
        this.status = status;
        this.spendingRatio = spendingRatio;
        this.debtUtilization = debtUtilization;
        this.creditRisk = creditRisk;
    }

    public int getScore() { return score; }
    public String getStatus() { return status; }
    public double getSpendingRatio() { return spendingRatio; }
    public double getDebtUtilization() { return debtUtilization; }
    public String getCreditRisk() { return creditRisk; }
}
