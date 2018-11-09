package com.example;

public class UserInterface {

    public static double wealth = 10000.0;
    public static int time = 0;

    //called by the simulation to execute the user command
    //userInput is the string given by the user
    public static void useUserInput(String userInput, Restaurant restaurant, Market market){
        if(userInput == null){
            System.out.println(UIHelpers.printNullInputMessage());
            return;
        }

        String[] words = userInput.split(" ");

        //if the command was one word, these are the possibilities
        if(words.length == 1){
            switch (words[0].toLowerCase()) {
                case "wealth":
                    System.out.println(UIHelpers.printWealth());
                    break;
                case "time":
                    System.out.println(UIHelpers.printTime());
                    break;
                case "market":
                    Market.runMarket(market, restaurant);
                    break;
                default:
                    System.out.println(UIHelpers.printBadInputMessage(userInput));
                    break;
            }
        }

        //if the command was two words, these are the possibilities
        if(words.length == 2){
            switch (words[0].toLowerCase()) {
                case "inventory":
                    System.out.println(UIHelpers.printInventory(words[1], restaurant));
                    break;
                case "info":
                    System.out.println(UIHelpers.printInfo(words[1], restaurant));
                    break;
                case "menu":
                    if (words[1].toLowerCase().equals("list")) {
                        System.out.println(UIHelpers.listMenu(restaurant));
                    } else {
                        System.out.println(UIHelpers.printBadInputMessage(userInput));
                    }
                    break;
                default:
                    System.out.println(UIHelpers.printBadInputMessage(userInput));
                    break;
            }
        }

        //if the command was three words, these are the possibilities
        if(words.length == 3){
            switch (words[0].toLowerCase()) {
                case "pass":
                    if (words[1].toLowerCase().equals("time")) {
                        System.out.println(UIHelpers.passTime(words[2], restaurant, market));
                    } else {
                        System.out.println(UIHelpers.printBadInputMessage(userInput));
                    }
                    break;
                case "cook":
                    System.out.println(UIHelpers.cook(words[1], words[2], restaurant));
                    break;
                case "menu":
                    switch (words[1].toLowerCase()) {
                        case "add":
                            System.out.println(UIHelpers.addToMenu(words[2], restaurant));
                            break;
                        case "remove":
                            System.out.println(UIHelpers.removeFromMenu(words[2], restaurant));
                            break;
                        default:
                            System.out.println(UIHelpers.printBadInputMessage(userInput));
                            break;
                    }
                    break;
            }
        }
    }
}
