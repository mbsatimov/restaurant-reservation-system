package uz.mahkambek;

import uz.mahkambek.model.Meal;
import uz.mahkambek.model.Reservation;
import uz.mahkambek.service.MealService;
import uz.mahkambek.service.MenuService;
import uz.mahkambek.service.ReservationService;
import uz.mahkambek.util.MyScan;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import static uz.mahkambek.db.Database.meals;

public class App {
    private final MealService mealService ;
    private final ReservationService reservationService;

    public App() {
        this.mealService = new MealService();
        this.reservationService = new ReservationService();
    }

    public void run() {
        System.out.println("--------------------- Welcome to Our Restaurant ---------------------");
        while(true) {
            System.out.println("""
                    Choose your role
                    1. Staff
                    2. Customer
                    3. Quit
                    """);
            String role = MyScan.STR.nextLine();

            switch (role) {
                case "1" -> {
                    stuff();
                }case "2" -> {
                    customer();
                }case "3" -> {
                    return;
                }default ->
                    System.out.println("Wrong input");

            }
        }

    }


    public  void stuff() {
        System.out.println("--------------------- Welcome to Staff ---------------------");
        while (true) {
            System.out.println("""
                    1. Show Menu
                    2. Add new meal
                    3. Show all reservations
                    4. Edit meals
                    5. exit
                    """);
            try {
                int choice = MyScan.INT_SC.nextInt();
                if(choice == 5) {
                    return;
                }
                stuffManagement(choice);
            } catch (InputMismatchException e) {
                System.out.println("Wrong input");
            }


        }

    }

    private void stuffManagement(int choice) {
        switch (choice) {
            case 1 -> {
                //show menu
                MenuService.showMenu();
                MyScan.STR.nextLine();

            }case 2 -> {
                //add new meal
                System.out.println("enter name of meal");
                String name = MyScan.STR.nextLine();
                System.out.println("enter price of meal");
                double price = MyScan.INT_SC.nextDouble();
                System.out.println("enter ingredients of meal");
                String ingredients = MyScan.STR.nextLine();
                Meal meal = new Meal(name, price, ingredients);
                String s = mealService.addMeal(meal);
                System.out.println(s);
            }case 3 -> {
                //show all reservations
                reservationService.showAllReservations();
            }case 4 -> {
                //edit meal

                System.out.println("""
                        1. Edit meal,
                        2. Delete meal,
                        3. Exit""");
                choice = MyScan.INT_SC.nextInt();
                try {
                     if (choice == 1) {
                        MenuService.showMenu();
                        System.out.println();
                        System.out.println("enter id of meal");
                        int id = MyScan.INT_SC.nextInt();
                        System.out.println("enter new name of meal");
                        String name = MyScan.STR.nextLine();
                        System.out.println("enter new price of meal");
                        double price = MyScan.INT_SC.nextDouble();
                        System.out.println("enter new ingredients of meal");
                        String ingredients = MyScan.STR.nextLine();
                        Meal meal = new Meal(name, price, ingredients);
                        if (id < 1 || id > meals.size()) {
                            System.out.println("wrong id");
                            break;
                        }
                        mealService.updateMeal(id, meal);
                        System.out.println("meal updated");
                         MyScan.STR.nextLine();

                    }
                    else if (choice == 2) {
                        MenuService.showMenu();
                        System.out.println();
                        System.out.println("enter id of meal");
                        int id = MyScan.INT_SC.nextInt();
                        mealService.deleteMeal(id);
                        MyScan.STR.nextLine();
                    }
                }catch (InputMismatchException e) {
                    System.out.println("Wrong input");
                }
            }
        }
    }

    public void customer() {
        System.out.println("--------------------- Welcome to Customer ---------------------");
        System.out.println("enter your name");
        String name = MyScan.STR.nextLine();
        while (true) {
            System.out.println("""
                    1. Show menu
                    2. Make reservation
                    3. Show all reservations
                    4. Edit reservation
                    5. Delete reservation
                    6. exit
                    """);
            try {
                int choice = MyScan.INT_SC.nextInt();
                if(choice == 6) {
                    return;
                }
                customerManagement(choice, name);
            } catch (InputMismatchException e) {
                System.out.println("Wrong input");
            }
        }
    }

    private void customerManagement(int choice, String name) {
        switch (choice) {
            case 1 -> {
                //show menu
                MenuService.showMenu();
            }case 2 -> {
                //make reservation

                Reservation reservation = makeReservation(name);
                if(reservation != null) {
                    reservationService.addReservation(reservation);
                    System.out.println("reservation made");
                }
                MyScan.STR.nextLine();

            }case 3 -> {
                //show all reservations
                reservationService.showUserReservations(name);
            }case 4 -> {
                //edit reservation
                List<Reservation> userReservations = reservationService.getUserReservations(name);
                if(userReservations.isEmpty()) {
                    System.out.println("You have no reservations");
                    break;
                }
                reservationService.showUserReservations(name);
                System.out.println();
                System.out.println("Enter reservation number");
                int id = MyScan.INT_SC.nextInt();
                Reservation reservation = makeReservation(name);
                if(reservation == null) {
                   reservationService.editReservation(id, reservation);
                }
            }case 5 -> {
                //delete reservation
                reservationService.showUserReservations(name);
                System.out.println();
                System.out.println("Enter reservation number");
                int id = MyScan.INT_SC.nextInt();
                reservationService.deleteReservation(id, name);
            }
        }
    }

    private Reservation makeReservation(String name) {
        System.out.println("which date would you like to make reservation? Enter the date in dd/mm/yyyy format");
        String date = MyScan.STR.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(date, formatter);
        System.out.println("which time would you like to make reservation? Enter the time in hh:mm format");
        String time = MyScan.STR.nextLine();
        LocalTime localTime = LocalTime.parse(time);

        System.out.println("how many people?");
        int people = MyScan.INT_SC.nextInt();
        System.out.println("enter number of meals");
        int numOfMeals = MyScan.INT_SC.nextInt();
        List<Meal> mealList = new ArrayList<>(numOfMeals);
        MenuService.showMenu();
        System.out.println();
        double totalPrice = 0;
        for (int i = 0; i < numOfMeals; i++) {
            System.out.println("enter id of meal");
            int id = MyScan.INT_SC.nextInt();
            if (id > 0 && id <= meals.size()) {
                Meal e = meals.get(id - 1);
                totalPrice += e.getPrice();
                mealList.add(e);
            }
        }

        List<String> list = mealList
                .stream()
                .map(Meal::getName)
                .toList();

        System.out.printf("""
                Reservation date: %s
                Reservation time: %s
                Number of people: %d
                Meals: %s
                Total price: %.2f
                %n""", localDate, localTime, people, list, totalPrice);
        System.out.println("Do you want to make reservation? (y/n)");
        String answer = MyScan.STR.nextLine();
        if (answer.equalsIgnoreCase("y")) {
            return new Reservation(name, localDate, localTime, people, mealList);
        }
        return null;
    }
}