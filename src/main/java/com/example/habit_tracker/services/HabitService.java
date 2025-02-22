// src/main/java/com/example/habit_tracker/services/HabitService.java
package com.example.habit_tracker.services;

import com.example.habit_tracker.models.Habit;
import com.example.habit_tracker.repositories.HabitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class HabitService {
    
    @Autowired
    private HabitRepository habitRepository;

    public List<Habit> getAllHabits() {
        return habitRepository.findAll();
    }

    public Optional<Habit> getHabitById(Long id) {
        return habitRepository.findById(id);
    }

    public Habit createHabit(Habit habit) {
        return habitRepository.save(habit);
    }

    public Habit updateHabit(Habit habit) {
        return habitRepository.save(habit);
    }

    public void deleteHabit(Long id) {
        habitRepository.findById(id).ifPresent(habit -> {
            try {
                habitRepository.delete(habit);
            } catch (Exception e) {
                throw new RuntimeException("Failed to delete habit: " + e.getMessage());
            }
        });
    }

    public void markHabitAsCompleted(Long id) {
        Habit habit = habitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid habit Id:" + id));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lastCompleted = habit.getLastCompleted();

        if (lastCompleted == null) {
            habit.setCurrentStreak(1);
        } else {
            long daysBetween = ChronoUnit.DAYS.between(lastCompleted.toLocalDate(), now.toLocalDate());
            
            switch (habit.getFrequency()) {
                case "DAILY" -> {
                    if (daysBetween <= 1) {
                        habit.setCurrentStreak(habit.getCurrentStreak() + 1);
                    } else {
                        habit.setCurrentStreak(1);
                    }
                }
                    
                case "WEEKLY" -> {
                    if (daysBetween <= 7) {
                        habit.setCurrentStreak(habit.getCurrentStreak() + 1);
                    } else {
                        habit.setCurrentStreak(1);
                    }
                }
                    
                case "MONTHLY" -> {
                    if (daysBetween <= 31) {
                        habit.setCurrentStreak(habit.getCurrentStreak() + 1);
                    } else {
                        habit.setCurrentStreak(1);
                    }
                }
            }
        }

        if (habit.getCurrentStreak() > habit.getBestStreak()) {
            habit.setBestStreak(habit.getCurrentStreak());
        }

        habit.setLastCompleted(now);
        habitRepository.save(habit);
    }
}