package org.example.moneymate.dto;

import java.time.LocalDateTime;

public class FinancialAlertDTO {
    private Long id;
    private String type;
    private String title;
    private String message;
    private boolean read;
    private LocalDateTime createdAt;

    public FinancialAlertDTO(Long id, String type, String title, String message, boolean read, LocalDateTime createdAt) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.message = message;
        this.read = read;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public String getType() { return type; }
    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public boolean isRead() { return read; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
