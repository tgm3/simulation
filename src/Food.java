package com.example;

public class Food {

    public String name;
    public double baseValue;
    public int unitsLeft;
    public Food[] ingredients;
    public boolean onMenu;

    //called to make the food and add it to the users inventory
    //quantity is how many units of food the user wants to make
    //recipe is the recipe the user is using to make the food item
    public static String makeFood(double quantity, Recipe recipe, Restaurant restaurant){
        StringBuilder returnString = new StringBuilder();

        //keeps track of how many units of the food succesfully made
        int count = 0;

        outerloop:
        for(int i = 0; i < quantity; i++){

            //goes through all the necessary ingredients and checks to make sure the user has it available
            for(int j = 0; j < recipe.ingredients.length; j++){

                Food currentIngredient;
                for(int k = 0; k < restaurant.inventory.length; k++){
                    if(recipe.ingredients[j].name.toLowerCase().equals(restaurant.inventory[k].name.toLowerCase())){
                        currentIngredient = restaurant.inventory[k];

                        //subtracting a unit of each food item used as an ingredient
                        currentIngredient.unitsLeft--;

                        //if the user is missing any of the necessary ingredients no more food items are made
                        if(currentIngredient.unitsLeft < 0){
                            returnString.append("You are out of ").append(currentIngredient.name);
                            break outerloop;
                        }
                    }
                }

            }
            count++;
        }

        //adds the successfully created units of the food to the inventory
        for(int i = 0; i < restaurant.inventory.length; i++){
            if(restaurant.inventory[i].name.equals(recipe.output.name)){
                restaurant.inventory[i].unitsLeft += count;
            }
        }

        returnString.append("\n").append("You made ").append(count).append(" ").append(recipe.name);
        return returnString.toString();
    }

}
