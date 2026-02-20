package org.example.moneymate.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreditSimulationResponse {

    private int currentScore;
    private int predictedScore;
    private int impact;
    private String message;
}
