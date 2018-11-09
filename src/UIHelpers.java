package com.example;

public class UIHelpers {

    //called when the user gives the program invalid text
    //userinput is the part of the users text that is not valid(is not always the entire input)
    public static String printBadInputMessage(String userInput){
        return "Sorry, I do not understand " + userInput;
    }

    //called when the input is null
    public static String printNullInputMessage(){
        return "Please input command";
    }

    public static double printWealth(){
        return UserInterface.wealth;
    }

    public static double printTime(){
        return UserInterface.time;
    }

    //called by the user to advance the day
    //userInput is the number of hours the user wishes to advance
    public static String passTime(String userInput, Restaurant restaurant, Market market) {
        StringBuilder returnString = new StringBuilder();
        int additionalTime = Integer.parseInt(userInput);
        if (additionalTime < 0){
            return "You cannot go back in time";
        }

        //there are only 24 hours in a day so if the user advances past the 24th hour
        // a new day is started
        UserInterface.time = (UserInterface.time + additionalTime) % 24;
        if(UserInterface.time > 18 || UserInterface.time < 10){
            double multiplier = Math.random();
            returnString.append("\nYou have sold: \n");
            for(int i = 0; i < restaurant.inventory.length; i++){
                int numberToSell = (int)(restaurant.inventory[i].unitsLeft * multiplier);
                for(int j = 0; j < numberToSell; j++){
                    Market.sellToMarket("1", restaurant.inventory[i].name, market, restaurant);
                }
                returnString.append(numberToSell).append(" ").append(restaurant.inventory[i].name).append("\n");
            }
        }
        returnString.append("The time is now ").append(UserInterface.time).append(":00");
        return returnString.toString();
    }

    //lists any recipes that have been added to the menu
    public static String listMenu(Restaurant restaurant){
        StringBuilder returnString = new StringBuilder();
        if(restaurant.menu.foodItems.length == 0){
            return "There is no food";
        }

        //checks every item in the inventory to see if it is on the menu
        //if it is it kinds the item in the recipes list and prints the name and price
        for(int i = 0; i < restaurant.inventory.length; i++){
            if(restaurant.inventory[i].onMenu){
                for(int j = 0; j < restaurant.recipes.length; j++){
                    if(restaurant.inventory[i].name.equals(restaurant.recipes[j].output.name)){
                        returnString.append("\n").append(restaurant.recipes[j].output.name);
                        returnString.append(": ").append(restaurant.recipes[j].buyValue);
                    }
                }

            }

        }
        return returnString.toString();
    }

    //this method prints all the items of a certain type(food, equipment, recipe)
    //userinput is the name of the item
    public static String printInventory(String userInput, Restaurant restaurant){
        StringBuilder returnString = new StringBuilder();

        switch (userInput.toLowerCase()) {
            case "food":
                for (int i = 0; i < restaurant.inventory.length; i++) {

                    //only prints the food items that the user has at least one of
                    if (restaurant.inventory[i].unitsLeft > 0) {
                        returnString.append("\n").append(restaurant.inventory[i].name);
                        returnString.append("\n").append("Units Left: ").append(restaurant.inventory[i].unitsLeft);
                    }

                }
                if (returnString.length() == 0) {
                    return "You have no food";
                }
                break;
            case "equipment":
                for (int i = 0; i < restaurant.equipment.length; i++) {

                    //only prits the equipment that the user has at least one of
                    if (restaurant.equipment[i].units > 0) {
                        returnString.append("\n").append(restaurant.equipment[i].name);
                        returnString.append("\nUnits: ").append(restaurant.equipment[i].units);
                    }

                }
                if (returnString.length() == 0) {
                    return "You have no equipment";
                }
                break;
            case "recipe":
            case "recipes":
                for (int i = 0; i < restaurant.recipes.length; i++) {

                    //only prints out the recipes the user has bought
                    if (restaurant.recipes[i].has) {
                        returnString.append("\n").append(restaurant.recipes[i].name);
                    }
                }
                if (returnString.length() == 0) {
                    return "You have no recipes";
                }
                break;
            default:
                return printBadInputMessage(userInput);
        }

        return returnString.toString();
    }

    //prints all the properties of the item(food, equipment, recipe) the user is looking for
    //userinput is the name of the item the user wants info about
    public static String printInfo(String userInput, Restaurant restaurant){
        StringBuilder returnString = new StringBuilder();

        Food possibleFood = null;
        Equipment possibleEquipment = null;
        Recipe possibleRecipe = null;

        //determines whether the user is looking for info about a food, equipment, or recipe
        for (int i = 0; i < restaurant.inventory.length; i++){
            if(restaurant.inventory[i].name.toLowerCase().equals(userInput.toLowerCase())){
                possibleFood = restaurant.inventory[i];
            }
        }

        for (int i = 0; i < restaurant.equipment.length; i++){
            if(restaurant.equipment[i].name.toLowerCase().equals(userInput.toLowerCase())){
                possibleEquipment = restaurant.equipment[i];
            }
        }

        for (int i = 0; i < restaurant.recipes.length; i++){
            if(restaurant.recipes[i].name.toLowerCase().equals(userInput.toLowerCase())){
                possibleRecipe = restaurant.recipes[i];
            }
        }


        //if the user put in a valid name, then that item(food, equipment, recipe) will have been initialized
        //all of that items info gets added to the returnstring
        if(possibleRecipe != null) {

            returnString.append("\n").append(possibleRecipe.name);
            returnString.append("\n").append(possibleRecipe.timeToMake);
            returnString.append("\n").append(possibleRecipe.buyValue);
            returnString.append("\n").append("Necessary Equipment");
            for (int i = 0; i < possibleRecipe.necessaryEquipment.length; i++) {
                returnString.append("\n").append(possibleRecipe.necessaryEquipment[i].name);
            }
            returnString.append("\n").append("Necessary Ingredients");
            for (int i = 0; i < possibleRecipe.ingredients.length; i++) {
                returnString.append("\n").append(possibleRecipe.ingredients[i].name);
            }

        } else if(possibleEquipment != null){

            returnString.append("\n").append(possibleEquipment.name);
            returnString.append("\n").append("Base Value: ").append(possibleEquipment.baseValue);
            returnString.append("\n").append("Upkeep Cost: ").append(possibleEquipment.upKeepCost);
            returnString.append("\n").append("Units: ").append(possibleEquipment.units);

        } else if(possibleFood != null){

                returnString.append("\n").append(possibleFood.name);
                returnString.append("\n").append("Base Value: ").append(possibleFood.baseValue);
                returnString.append("\n").append("Units Left: ").append(possibleFood.unitsLeft);
                returnString.append("\n").append("Necessary Ingredients");
                if(possibleFood.ingredients == null){
                    returnString.append("\n").append("none");
                } else{
                    for (int i = 0; i < possibleFood.ingredients.length; i++){
                        returnString.append("\n").append(possibleFood.ingredients[i].name);
                    }
                }

        } else{
            return printBadInputMessage(userInput);
        }

        return returnString.toString();
    }

    //adds a food item to the restaurants menu
    //userimput is the name of the food item that the user wants to add
    public static String addToMenu(String userInput, Restaurant restaurant){

        //checks to see if the food item is already of the menu
        //if it is then it does not get added to avoid duplicates
        boolean alreadyOnMenu = false;
        for (int i =0; i < restaurant.menu.foodItems.length; i++){
            if(userInput.toLowerCase().equals(restaurant.menu.foodItems[i].name.toLowerCase())){
                if(restaurant.menu.foodItems[i].onMenu){
                    alreadyOnMenu = true;
                }

            }
        }
        if(alreadyOnMenu){
            return userInput + "is already on menu";
        }

        //if the user has a recipe to make the food item, foodToAdd should get initialized and then added to the menu
        //if foodToAdd does not get initialized then the user does not have it or it does not exist
        //and therefore does not get added to the restaurants menu
        Food foodToAdd = null;
        for(int i = 0; i < restaurant.recipes.length; i++){
            if(restaurant.recipes[i].has){
                if(restaurant.recipes[i].name.toLowerCase().equals(userInput.toLowerCase())){
                    for(int j = 0; j < restaurant.inventory.length; j++){
                        if(restaurant.recipes[i].name.toLowerCase().equals(restaurant.inventory[j].name)){
                            restaurant.inventory[j].onMenu = true;
                            return userInput + " added to menu";
                        }
                    }
                }
            }
        }
        if(foodToAdd == null){
            return "you do not have " + userInput;
        }

        return null;
    }

    //removes a food item, if on the menu, off the menu
    public static String removeFromMenu(String userInput, Restaurant restaurant){
        StringBuilder returnString = new StringBuilder();

        //checks to make sure the food item is on the menu
        //if the food item does not exist or is not on the menu it does not get added
        //otherwise it does
        boolean notOnMenu = true;
        for(int i = 0; i < restaurant.recipes.length; i++){
            if(restaurant.recipes[i].name.toLowerCase().equals(userInput.toLowerCase())){
                for(int j = 0; j < restaurant.inventory.length; j++){
                    if(restaurant.recipes[i].name.toLowerCase().equals(restaurant.inventory[j].name)){
                        if(restaurant.inventory[j].onMenu){
                            notOnMenu = false;
                            restaurant.inventory[j].onMenu = false;
                            returnString.append("\n").append(userInput).append(" has been removed from menu");
                        }
                    }
                }
            }
        }
        if (notOnMenu){
            return userInput + " is not on the menu";
        }

        return returnString.toString();
    }

    //called when the user wishes to use a recipe to make a food item
    //food is the name of the food item the user is trying to make and the amount is how many the user is trying to make
    public static String cook(String food, String amount, Restaurant restaurant){
        double quantity = Double.parseDouble(amount);

        //checks to make sure the user has the recipe to make the food item
        //if the user does not have the recipe or it does not exist, nothing is cooked
        boolean notARecipe = true;
        Recipe recipe = null;
        for(int i = 0; i < restaurant.recipes.length; i++){
            if(restaurant.recipes[i].has){
                if(restaurant.recipes[i].name.toLowerCase().equals(food.toLowerCase())){
                    notARecipe = false;
                    recipe = restaurant.recipes[i];
                }
            }
        }
        if(notARecipe){
            return "There is not a recipe for that";
        }

        //if the user has the recipe, this checks to make sure the user has all the necessary equipment to make the food
        //if the user does not have the equipment, nothing is cooked
        boolean missingEquipment = false;
        for(int i = 0; i < recipe.necessaryEquipment.length; i++){
            for(int j = 0; j < restaurant.equipment.length; j++){
                if (restaurant.equipment[j].name.toLowerCase().equals(recipe.necessaryEquipment[i].name.toLowerCase())) {
                    if(restaurant.equipment[j].units == 0){
                        missingEquipment = true;
                    }
                }
            }
        }
        if(missingEquipment){
            return "You do not have the necessary equipment";
        }

        //checks to make sure the user has all the necessary ingredients to make the food
        //if the user does not, nothing is made
        //if the user does, the method makeFood in the Food class is called
        //which attempts make as many of the specified quantity as it can
        boolean missingFood = false;
        for(int i = 0; i < recipe.ingredients.length; i++){
            for(int j = 0; j < restaurant.inventory.length; j++){
                if(restaurant.inventory[j].name.toLowerCase().equals(recipe.ingredients[i].name.toLowerCase())){
                    if (restaurant.inventory[j].unitsLeft == 0) {
                        missingFood = true;
                    }
                }
            }
        }
        if(missingFood){
            return "You do not have the necessary ingredients";
        }
        return Food.makeFood(quantity, recipe, restaurant);
    }
}
