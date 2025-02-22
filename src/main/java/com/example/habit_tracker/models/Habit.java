package com.example.habit_tracker.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "habits")
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name = "";  // Initialize with empty string

    @Column
    private String description = "";  // Initialize with empty string

    @Column(nullable = false)
    private String frequency = "DAILY";  // Set default value

    @Column(name = "current_streak")
    private Integer currentStreak = 0;

    @Column(name = "best_streak")
    private Integer bestStreak = 0;

    @Column(name = "last_completed")
    private LocalDateTime lastCompleted;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}