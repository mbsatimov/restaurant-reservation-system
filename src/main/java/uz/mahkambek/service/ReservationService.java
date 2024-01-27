package uz.mahkambek.service;

import uz.mahkambek.model.Meal;
import uz.mahkambek.model.Reservation;

import java.util.List;
import java.util.stream.Collectors;

import static uz.mahkambek.db.Database.reservations;

public class ReservationService {

    public String addReservation(Reservation reservation) {
        int counter = 0;
        for (Reservation r : reservations) {
            if (r.getDate().equals(reservation.getDate())) {
                counter++;
            }

            if (r.getDate().equals(reservation.getDate()) && r.getTime().equals(reservation.getTime())) {
                return "This time is already reserved";
            }
        }
        if (counter >= 20) {
            return "Sorry😢, all tables in %s are full on this day".formatted(reservation.getDate());
        }

        reservations.add(reservation);
        return "Reservation added, successfully";
    }

    public void showAllReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations yet");
            return;
        }
        for (Reservation r : reservations) {
            System.out.printf("""
                    Name: %s
                    Date and Time: %s %s
                    Number of people: %d
                    Meals: %s \n
                    """, r.getUser(), r.getDate(), r.getTime(), r.getNumberOfMeals(), String.join(", ", r.getMealList()
                    .stream()
                    .map(Meal::getName)
                    .toList()));
        }
    }

    public void showUserReservations(String user) {
        List<Reservation> userReservations = getUserReservations(user);

        int counter = 1;
        for (Reservation r : userReservations) {
            System.out.printf("""
                    %d) Date and Time: %s %s
                        Number of people: %d
                        Meals: %s \n
                    """,counter++, r.getDate(), r.getTime(), r.getNumberOfMeals(), String.join(", ", r.getMealList()
                    .stream()
                    .map(Meal::getName)
                    .toList()));
        }

    }

    public void editReservation(int id, Reservation reservation) {
        reservations.set(id - 1, reservation);
    }

    public void deleteReservation(int id, String user) {
        List<Reservation> userReservations = getUserReservations(user);
        Reservation reservation = userReservations.get(id - 1);
        reservations.remove(reservation);
    }


    public List<Reservation> getUserReservations(String user) {
        return reservations
                .stream()
                .filter(r -> r.getUser().equals(user))
                .collect(Collectors.toList());
    }
}
