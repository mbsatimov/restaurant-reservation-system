package uz.mahkambek.db;

import uz.mahkambek.model.Meal;
import uz.mahkambek.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Database {
    public final static List<Meal> meals = new ArrayList<>();
    static {
        Meal meal = new Meal("pizza", 10, "ham, cheese, tomato");
        meals.add(meal);
        meal = new Meal("burger", 5, "ham, cheese, tomato");
        meals.add(meal);
    }
    public static final List<Reservation> reservations = new ArrayList<>();
    static {
        Reservation reservation = new Reservation("Mahkambek", LocalDate.now(), LocalTime.now(), 2, new ArrayList<>());
        reservations.add(reservation);
    }
}
