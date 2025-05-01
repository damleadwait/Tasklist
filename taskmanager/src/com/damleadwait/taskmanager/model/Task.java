package com.damleadwait.taskmanager.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDateTime targetEndDate;

    @Transient // Not persisted in the database
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Task() {
        // Default constructor for JPA
        this.id = UUID.randomUUID().toString();
    }

    public Task(String title, String description, LocalDateTime targetEndDate) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.targetEndDate = targetEndDate;
    }

    // Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDateTime getTargetEndDate() { return targetEndDate; }

    // Setters
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setTargetEndDate(LocalDateTime targetEndDate) { this.targetEndDate = targetEndDate; }

    public String getFormattedTargetEndDate() {
        return (targetEndDate != null) ? targetEndDate.format(DATE_TIME_FORMATTER) : "";
    }

    public static LocalDateTime parseDateTime(String dateTimeString) {
        return LocalDateTime.parse(dateTimeString, DATE_TIME_FORMATTER);
    }

    @Override
    public String toString() {
        return "ID: " + id +
                ", Title: " + title +
                (description != null && !description.isEmpty() ? ", Description: " + description : "") +
                (targetEndDate != null ? ", Target End Date: " + getFormattedTargetEndDate() : "");
    }
}