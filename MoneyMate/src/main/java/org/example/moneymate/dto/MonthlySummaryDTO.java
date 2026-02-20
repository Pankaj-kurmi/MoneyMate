package org.example.moneymate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data

public class MonthlySummaryDTO {
    private String month;     // e.g. 2026-02
    private BigDecimal income;
    private BigDecimal expense;


    // Constructor used by JPQL (Hibernate passes Object for the first field)
    public MonthlySummaryDTO(Object month, BigDecimal income, BigDecimal expense) {
        this.month = month != null ? month.toString() : null;
        this.income = income;
        this.expense = expense;
    }
}
