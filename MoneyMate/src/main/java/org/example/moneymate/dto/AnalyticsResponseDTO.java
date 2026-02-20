package org.example.moneymate.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AnalyticsResponseDTO {
    private List<MonthlySummaryDTO> monthlySummary;
    private List<CategoryBreakdownDTO> categoryBreakdown;
    private List<String> insights; // smart recommendations
}
