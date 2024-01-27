package uz.mahkambek.service;

import uz.mahkambek.model.Meal;

import static uz.mahkambek.db.Database.meals;

public class MealService {
    public String addMeal(Meal meal){
        String result = "";
        if(meals.size() < 10){
            for (Meal value : meals) {
                if (value.getName().equalsIgnoreCase(meal.getName())) {
                    result = "meal Already exists";
                    return result;
                }
            }
            meals.add(meal);
            result = "Added";
        }else {
            result = "List is full";
        }
        return result;
    }

    public void updateMeal(int id, Meal meal){
        if(id > 0 && id <= meals.size()){
            meals.set(id-1, meal);
        }
    }

    public void deleteMeal(int id){
        if (id > 0 && id <= meals.size()){
            meals.remove(id-1);
            System.out.println("Deleted");
        }
    }
}
