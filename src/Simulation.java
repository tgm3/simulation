package com.example;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import com.google.gson.Gson;

public class Simulation {

    //runs the simulation of a restaurant owner
    //takes input from the user and reacts accordingly
    public static void main(String[] args) {

        Scanner stdin = new Scanner(System.in);
        System.out.println("Would you like to run the default Simulation?");
        String response = stdin.nextLine();

        //if the user inputs anything other than "yes" he will be prompted for the files
        //if the user inputs "yes" he gets the default
        if(response.toLowerCase().equals("yes")){
            String restaurantString = getFileContentsAsString(args[0]);
            String marketString = getFileContentsAsString(args[1]);
            startGame(restaurantString, marketString);

        } else {
            System.out.println("Type the file name containing the json for your restaurant");
            String restaurantFileName = stdin.nextLine();
            String restaurantString = getFileContentsAsString(restaurantFileName);
            System.out.println("Type the file name containing the json for your market");
            String marketFileName = stdin.nextLine();
            String marketString = getFileContentsAsString(marketFileName);

            startGame(restaurantString, marketString);
        }

    }

    private static void startGame(String restaurantString, String marketString){

        Scanner stdin = new Scanner(System.in);

        Restaurant restaurant = loadRestaurant(restaurantString);
        Market market = loadMarket(marketString);

        System.out.println(printWelcomeMessage());
        boolean isPlaying = true;

        //the simulation is never ending
        //can only be stopped with the word "exit"
        while(isPlaying){

            System.out.println("What would you like to do next?");


            String userInput = stdin.nextLine();

            if(userInput.toLowerCase().equals("exit")){
                isPlaying = false;
            } else {
                UserInterface.useUserInput(userInput, restaurant, market);
            }

        }
    }

    public static String printWelcomeMessage(){
        return "Welcome to your Restaurant Simulation. " +
                "You have $10,000. Have fun, and type 'exit' when you wish to end the simulation.";
    }

    public static String getFileContentsAsString(String filename) {

        // Java uses Paths as an operating system-independent specification of the location of files.
        // In this case, we're looking for files that are in a directory called 'data' located in the
        // root directory of the project, which is the 'current working directory'.
        final Path path = FileSystems.getDefault().getPath("data", filename);

        try {
            // Read all of the bytes out of the file specified by 'path' and then convert those bytes
            // into a Java String.  Because this operation can fail if the file doesn't exist, we
            // include this in a try/catch block
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            // Since we couldn't find the file, there is no point in trying to continue.  Let the
            // user know what happened and exit the run of the program.  Note: we're only exiting
            // in this way because we haven't talked about exceptions and throwing them in CS 126 yet.
            System.out.println("Couldn't find file: " + filename);
            System.exit(-1);
            return null;  // note that this return will never execute, but Java wants it there.
        }
    }

    private static Restaurant loadRestaurant(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Restaurant.class);
    }

    private static Market loadMarket(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Market.class);
    }
}
