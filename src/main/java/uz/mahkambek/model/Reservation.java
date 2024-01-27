package uz.mahkambek.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Reservation {
    private String user;
    private LocalDate date;
    private LocalTime time;
    private int numberOfMeals;
    private List<Meal> mealList;

    public Reservation() {
    }

    public Reservation(String user, LocalDate date, LocalTime time, int numberOfMeals, List<Meal> mealList) {
        this.user = user;
        this.date = date;
        this.time = time;
        this.numberOfMeals = numberOfMeals;
        this.mealList = mealList;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public List<Meal> getMealList() {
        return mealList;
    }

    public void setMealList(List<Meal> mealList) {
        this.mealList = mealList;
    }

    public int getNumberOfMeals() {
        return numberOfMeals;
    }

    public void setNumberOfMeals(int numberOfMeals) {
        this.numberOfMeals = numberOfMeals;
    }
}
