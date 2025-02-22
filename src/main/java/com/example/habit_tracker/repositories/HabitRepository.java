package com.example.habit_tracker.repositories;

import com.example.habit_tracker.models.Habit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitRepository extends JpaRepository<Habit, Long> {
    // Custom query methods can be added here
}