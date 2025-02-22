package com.example.habit_tracker.controllers;

import com.example.habit_tracker.models.Habit;
import com.example.habit_tracker.services.HabitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/habits")
public class HabitController {

    @Autowired
    private HabitService habitService;

    @GetMapping
    public String listHabits(Model model) {
        model.addAttribute("habits", habitService.getAllHabits());
        return "habits/index";
    }

    @GetMapping("/new")
    public String newHabitForm(Model model) {
        model.addAttribute("habit", new Habit());
        return "habits/new";
    }

    @PostMapping
    public String createHabit(@ModelAttribute Habit habit) {
        habitService.createHabit(habit);
        return "redirect:/habits";
    }

    @GetMapping("/{id}/edit")
    public String editHabitForm(@PathVariable Long id, Model model) {
        Habit habit = habitService.getHabitById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid habit Id:" + id));
        model.addAttribute("habit", habit);
        return "habits/edit";
    }

    @PostMapping("/{id}")
    public String updateHabit(@PathVariable Long id, @ModelAttribute Habit habit) {
        habit.setId(id);
        habitService.updateHabit(habit);
        return "redirect:/habits";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHabit(@PathVariable Long id) {
        try {
            habitService.deleteHabit(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error deleting habit");
        }
    }

    @PostMapping("/{id}/complete")
    public String completeHabit(@PathVariable Long id) {
        habitService.markHabitAsCompleted(id);
        return "redirect:/habits";
    }
}