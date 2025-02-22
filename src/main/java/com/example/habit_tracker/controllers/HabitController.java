package com.example.habit_tracker.controllers;

import com.example.habit_tracker.models.Habit;
import com.example.habit_tracker.services.HabitService;
import org.springframework.beans.factory.annotation.Autowired;
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
}