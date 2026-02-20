package org.example.moneymate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CategoryBreakdownDTO {
    private String categoryName;
    private BigDecimal totalAmount;
}
