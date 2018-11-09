package com.example;

import java.util.Scanner;

public class Market {

    private static final int travelTimeHours = 1;
    private Equipment[] availableEquipment;
    private Food[] availableFood;
    private Recipe[] availableRecipes;

    //each runMarket call simulates one trip to the market
    public static void runMarket(Market market, Restaurant restaurant){

        System.out.println("Welcome to the market. " +
                "Here you can 'buy' and 'sell' food and equipment." +
                        "Say 'leave' when you are ready to go back to the restaurant");


        //user can continue to buy and sell until they choose to "leave"
        while(true){
            System.out.println("What would you like to do?");
            Scanner stdin = new Scanner(System.in);
            String userInput = stdin.nextLine();
            if(userInput.toLowerCase().equals("leave")){
                break;
            }

            String[] words = userInput.split(" ");
            if(words.length == 2){
                if(words[0].toLowerCase().equals("list")){
                    switch (words[1].toLowerCase()) {
                        case "food":
                            System.out.println(listFood(market));
                            break;
                        case "equipment":
                            System.out.println(listEquipment(market));
                            break;
                        case "recipes":
                            System.out.println(listRecipe(market));
                            break;
                        default:
                            System.out.println("You cannot do that");
                            break;
                    }
                }
            }

            //if the input is not "leave", doesn't begin with "list", and the input is not 3 words long,
            //then the input cannot be valid
            //so notifies the user and reprompts the user for input
            if(words.length == 3){
                switch (words[0].toLowerCase()) {
                    case "buy":
                        System.out.println(buyFromMarket(words[1], words[2], market, restaurant));
                        break;
                    case "sell":
                        System.out.println(sellToMarket(words[1], words[2], market, restaurant));
                        break;
                    default:
                        System.out.println("You cannot do that");
                        break;
                }
            }

            if(words.length != 2 && words.length != 3){
                System.out.println("You cannot do that");
            }
        }

        //each trip to the market, no matter how much the user buys or sells, takes one hour
        //adds an hour to the day, if 24 hours are over it begins a new day
        UserInterface.time = (UserInterface.time + travelTimeHours) % 24;

    }

    public static String listFood(Market market){
        StringBuilder returnString = new StringBuilder();
        for(int i = 0; i < market.availableFood.length; i++){
            returnString.append(market.availableFood[i].name).append(": ").append(market.availableFood[i].baseValue);
            returnString.append("\n");
        }
        return returnString.toString();
    }

    public static String listEquipment(Market market){
        StringBuilder returnString = new StringBuilder();
        for(int i = 0; i < market.availableEquipment.length; i++){
            returnString.append(market.availableEquipment[i].name).append(": ").append(market.availableEquipment[i].baseValue);
            returnString.append("\n");
        }
        return returnString.toString();
    }

    public static String listRecipe(Market market){
        StringBuilder returnString = new StringBuilder();
        for(int i = 0; i < market.availableRecipes.length; i++){
            returnString.append(market.availableRecipes[i].name).append(": ").append(market.availableRecipes[i].buyValue);
            returnString.append("\n");
        }
        return returnString.toString();
    }

    //called when the user wants to buy something
    //amount is how many units the user wants to buy and item is the object the user is trying to buy
    public static String buyFromMarket(String amount, String item, Market market, Restaurant restaurant){

        StringBuilder returnString = new StringBuilder();
        double quantity = Double.parseDouble(amount);

        Food possibleFood = null;
        Equipment possibleEquipment = null;
        Recipe possibleRecipe = null;

        //tests to see if the user is looking to buy a food, equipment, or recipe
        //the correct item is then initialized so long as the user is buying a valid item
        for (int i = 0; i < market.availableFood.length; i++){
            if(market.availableFood[i].name.toLowerCase().equals(item.toLowerCase())){
                possibleFood = market.availableFood[i];
            }
        }

        for (int i = 0; i < market.availableEquipment.length; i++){
            if(market.availableEquipment[i].name.toLowerCase().equals(item.toLowerCase())){
                possibleEquipment = market.availableEquipment[i];
            }
        }

        for (int i = 0; i < market.availableRecipes.length; i++){
            if(market.availableRecipes[i].name.toLowerCase().equals(item.toLowerCase())){
                possibleRecipe = market.availableRecipes[i];
            }
        }

        //if the user is buying a real item, it calculates the money needed for the purchase
        //if the user has enough money it completes the purchase, otherwise it does not
        if(possibleFood != null){
            double requiredMoney = quantity * possibleFood.baseValue;
            if(requiredMoney > UserInterface.wealth){
                return "You do not have enough money";
            } else {
                UserInterface.wealth -= requiredMoney;
                for(int i = 0; i < restaurant.inventory.length; i++){
                    if(possibleFood.name.toLowerCase().equals(restaurant.inventory[i].name.toLowerCase())){
                        restaurant.inventory[i].unitsLeft += quantity;
                        returnString.append("You have successfully bought ").append(quantity).append(" ").append(item);
                    }
                }
            }
        } else if(possibleEquipment != null){
            double requiredMoney = quantity * possibleEquipment.baseValue;
            if(requiredMoney > UserInterface.wealth){
                return "You do not have enough money";
            } else {
                UserInterface.wealth -= requiredMoney;
                for(int i = 0; i < restaurant.equipment.length; i++){
                    if(possibleEquipment.name.toLowerCase().equals(restaurant.equipment[i].name.toLowerCase())){
                        restaurant.equipment[i].units += quantity;
                        returnString.append("You have successfully bought ").append(quantity).append(" ").append(item);
                    }
                }
            }
        } else if(possibleRecipe != null){
            double requiredMoney = quantity * possibleRecipe.buyValue;
            if(requiredMoney > UserInterface.wealth){
                return "You do not have enough money";
            } else {

                //if the user already has the recipe there is no point in buying it again
                for(int i = 0; i < restaurant.recipes.length; i++){
                    if(possibleRecipe.name.toLowerCase().equals(restaurant.recipes[i].name.toLowerCase())){
                        if(restaurant.recipes[i].has){
                            return "You already have this recipe";
                        } else {
                            restaurant.recipes[i].has = true;
                            returnString.append("You have successfully bought a ").append(item);
                        }
                    }
                }
                UserInterface.wealth -= requiredMoney;
            }
        } else{
            //occurs when the object the user is looking to buy is not contained in the market
            return "That does not exist";
        }
        return returnString.toString();
    }

    //called when the user wants to sell something
    //amount is how many units the user wants to sell and item is the object the user is trying to sell
    public static String sellToMarket(String amount, String item, Market market, Restaurant restaurant){
        StringBuilder returnString = new StringBuilder();
        double quantity = Double.parseDouble(amount);

        Food possibleFood = null;
        Equipment possibleEquipment = null;
        Recipe possibleRecipe = null;

        //tests to see if the user is looking to buy a food, equipment, or recipe
        //the correct item is then initialized so long as the user is buying a valid item
        for (int i = 0; i < market.availableFood.length; i++){
            if(market.availableFood[i].name.toLowerCase().equals(item.toLowerCase())){
                possibleFood = market.availableFood[i];
            }
        }

        for (int i = 0; i < market.availableEquipment.length; i++){
            if(market.availableEquipment[i].name.toLowerCase().equals(item.toLowerCase())){
                possibleEquipment = market.availableEquipment[i];
            }
        }

        for (int i = 0; i < market.availableRecipes.length; i++){
            if(market.availableRecipes[i].name.toLowerCase().equals(item.toLowerCase())){
                possibleRecipe = market.availableRecipes[i];
            }
        }

        //if the user is selling a real item, it calculates the money needed for the sale
        //if the user has the item it completes the sale, otherwise it does not
        if(possibleFood != null){
            double moneyToReceive = quantity * possibleFood.baseValue * .8;
            for(int i = 0; i < restaurant.inventory.length; i++){
                if(possibleFood.name.toLowerCase().equals(restaurant.inventory[i].name.toLowerCase())){
                    if(restaurant.inventory[i].unitsLeft >= quantity){
                        restaurant.inventory[i].unitsLeft -= quantity;
                        UserInterface.wealth += moneyToReceive;
                        returnString.append("You have successfully sold ").append(quantity).append(" ").append(item);
                    } else {
                        return "You do not have that many " + item;
                    }
                }
            }
        } else if(possibleEquipment != null){
            double moneyToReceive = quantity * possibleEquipment.baseValue * .5;
            for(int i = 0; i < restaurant.equipment.length; i++){
                if(possibleEquipment.name.toLowerCase().equals(restaurant.equipment[i].name.toLowerCase())){
                    if(restaurant.equipment[i].units >= quantity){
                        restaurant.equipment[i].units -= quantity;
                        UserInterface.wealth += moneyToReceive;
                        returnString.append("You have successfully sold ").append(quantity).append(" ").append(item);
                    } else {
                        return "You do not have that many " + item;
                    }
                }
            }
        } else if(possibleRecipe != null){
            double moneyToReceive = quantity * possibleRecipe.buyValue * .5;
            for(int i = 0; i < restaurant.recipes.length; i++){
                if(possibleRecipe.name.toLowerCase().equals(restaurant.recipes[i].name.toLowerCase())){
                    if(restaurant.recipes[i].has){
                        restaurant.recipes[i].has = false;
                        UserInterface.wealth += moneyToReceive;
                        returnString.append("You have successfully sold ").append(item);
                    } else {
                        return "You do not have " + item;
                    }
                }
            }
        } else {
            //occurs when the object the user is looking to buy is not contained in the market
            return item + " does not exist";
        }

        return returnString.toString();
    }
}
