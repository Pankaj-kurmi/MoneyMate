package org.example.moneymate.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryDTO {
    private Long id;
    private Long profileId;
    private String name;
    private String icon;
    private String type; // INCOME or EXPENSE
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
