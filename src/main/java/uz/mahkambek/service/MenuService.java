package uz.mahkambek.service;

import static uz.mahkambek.db.Database.meals;

public class MenuService {
    public static void showMenu() {
        if(meals.isEmpty()) {
            System.out.println("Menu is empty");
            return;
        }
        for (int i = 0; i < meals.size(); i++) {
            String s = """
                    %d) Meal: %s
                        Price: %s
                        Ingredients: %s
                    """.formatted(i+1, meals.get(i).getName(),
                    meals.get(i).getPrice(), meals.get(i).getIngredients());
            System.out.println(s);
        }
    }
}
