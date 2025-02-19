package com.example.foodapp;

import androidx.annotation.NonNull;

public class Dish {
    private double calories;
    private double proteins;
    private double fat;
    private double carbs;

    public Dish() {
        // Default constructor required for Gson
    }

    public double getCalories() {
        return calories;
    }

    public double getProteins() {
        return proteins;
    }

    public double getFat() {
        return fat;
    }

    public double getCarbs() {
        return carbs;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public void setProteins(double proteins) {
        this.proteins = proteins;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    @NonNull
    @Override
    public String toString() {
        return "Calories: " + calories + "\n" +
                "Proteins: " + proteins + "\n" +
                "Fat: " + fat + "\n" +
                "Carbs: " + carbs;
    }
}
