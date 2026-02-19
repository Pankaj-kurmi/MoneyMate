package org.example.moneymate.dto;

import java.time.LocalDate;

public class CreditScoreTrendDTO {
    private LocalDate date;
    private int score;

    public CreditScoreTrendDTO(LocalDate date, int score) {
        this.date = date;
        this.score = score;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getScore() {
        return score;
    }
}
