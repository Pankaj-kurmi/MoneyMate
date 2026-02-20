package org.example.moneymate.service;

import lombok.RequiredArgsConstructor;
import org.example.moneymate.dto.AnalyticsResponseDTO;
import org.example.moneymate.dto.CategoryBreakdownDTO;
import org.example.moneymate.dto.MonthlySummaryDTO;
import org.example.moneymate.entities.ProfileEntity;
import org.example.moneymate.repositry.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsService {
    private final ProfileService profileService;
    private final ExpenseRepository expenseRepository;

    public AnalyticsResponseDTO getAnalyticsForCurrentUser() {

        ProfileEntity profile = profileService.getCurrentProfile();

        List<MonthlySummaryDTO> monthlySummary =
                expenseRepository.getMonthlySummary(profile.getId());

        List<CategoryBreakdownDTO> categoryBreakdown =
                expenseRepository.getCategoryBreakdown(profile.getId());

        List<String> insights = generateInsights(monthlySummary, categoryBreakdown);

        return AnalyticsResponseDTO.builder()
                .monthlySummary(monthlySummary)
                .categoryBreakdown(categoryBreakdown)
                .insights(insights)
                .build();
    }

    // Simple rule-based insights (can be upgraded to ML later)
    private List<String> generateInsights(List<MonthlySummaryDTO> monthly,
                                          List<CategoryBreakdownDTO> categories) {

        List<String> insights = new ArrayList<>();

        if (monthly.size() >= 2) {
            MonthlySummaryDTO last = monthly.get(monthly.size() - 1);
            MonthlySummaryDTO prev = monthly.get(monthly.size() - 2);

            // Compare expenses
            if (last.getExpense().compareTo(prev.getExpense()) > 0) {
                insights.add("Your expenses increased compared to last month. Consider reviewing your spending.");
            } else {
                insights.add("Good job! Your expenses decreased compared to last month.");
            }

            // Compare income vs expense
            if (last.getIncome().compareTo(last.getExpense()) > 0) {
                insights.add("You are saving money this month. Keep it up!");
            } else {
                insights.add("Your expenses are higher than your income this month. Try to cut down on non-essential spending.");
            }
        }

        if (!categories.isEmpty()) {
            CategoryBreakdownDTO top = categories.get(0);
            insights.add("Your highest spending category is '" + top.getCategoryName()
                    + "'. You may want to optimize this area.");
        }

        if (insights.isEmpty()) {
            insights.add("Not enough data yet to generate insights. Keep using the app!");
        }

        return insights;
    }
}
