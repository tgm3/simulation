
import com.example.*;
import com.google.gson.Gson;

import static org.junit.Assert.*;

public class UIHelpersTest {

    private static Market marketOne;
    private static Restaurant restaurantOne;

    @org.junit.Before
    public void setUp(){
        Gson gson = new Gson();
        marketOne = gson.fromJson(Simulation.getFileContentsAsString("testMarketOne.json"), Market.class);
        restaurantOne = gson.fromJson(Simulation.getFileContentsAsString("testRestaurantOne.json"), Restaurant.class);
    }

    @org.junit.Test
    public void printBadInput() {
        assertEquals("Sorry, I do not understand this is wrong",
                UIHelpers.printBadInputMessage("this is wrong"));
    }

    @org.junit.Test
    public void printNullInput() {
        assertEquals("Please input command", UIHelpers.printNullInputMessage());
    }

    @org.junit.Test
    public void printWealth() {
        assertEquals("9845.0", Double.toString(UIHelpers.printWealth()));
    }

    @org.junit.Test
    public void printTime() {
        assertEquals("0.0", Double.toString(UIHelpers.printTime()));
    }

    @org.junit.Test
    public void passInvalidTime() {
        assertEquals("You cannot go back in time",
                UIHelpers.passTime("-1", restaurantOne, marketOne));
    }

    @org.junit.Test
    public void passValidTime() {
        assertEquals(true,
                UIHelpers.passTime("3", restaurantOne, marketOne).contains("The time is now 3:00"));
    }

    @org.junit.Test
    public void passIntoNewDayTime() {
        assertEquals(false,
                UIHelpers.passTime("27", restaurantOne, marketOne).contains("The time is now 27:00"));
    }

    @org.junit.Test
    public void listMenu() {
        assertEquals("", UIHelpers.listMenu(restaurantOne));
    }

    @org.junit.Test
    public void printFoodInventory() {
        assertEquals("\nbread\nUnits Left: 10", UIHelpers.printInventory("food", restaurantOne));
    }

    @org.junit.Test
    public void printEquipmentInventory() {
        assertEquals("\ntoaster\nUnits: 2", UIHelpers.printInventory("equipment", restaurantOne));
    }

    @org.junit.Test
    public void printRecipeInventory() {
        assertEquals("You have no recipes", UIHelpers.printInventory("recipe", restaurantOne));
    }

    @org.junit.Test
    public void printInvalidInventory() {
        assertEquals("Sorry, I do not understand money", UIHelpers.printInventory("money", restaurantOne));
    }

    @org.junit.Test
    public void printFoodInfo() {
        assertEquals("\nbread\nBase Value: 10.0\nUnits Left: 10\nNecessary Ingredients\nnone",
                UIHelpers.printInfo("bread", restaurantOne));
    }

    @org.junit.Test
    public void printEquipmentInfo() {
        assertEquals("\ntoaster\nBase Value: 30.0\nUpkeep Cost: 5.0\nUnits: 2",
                UIHelpers.printInfo("toaster", restaurantOne));
    }

    @org.junit.Test
    public void printRecipeInfo() {
        assertEquals("\nsandwich\n0.25\n10.0\nNecessary Equipment\ntoaster\nNecessary Ingredients\nbread",
                UIHelpers.printInfo("sandwich", restaurantOne));
    }

    @org.junit.Test
    public void printInvalidInfo() {
        assertEquals("Sorry, I do not understand money", UIHelpers.printInfo("money", restaurantOne));
    }

    @org.junit.Test
    public void addtoMenu() {
        assertEquals("you do not have sandwich", UIHelpers.addToMenu("sandwich", restaurantOne));
    }

    @org.junit.Test
    public void removeFromMenu() {
        assertEquals("sandwich is not on the menu", UIHelpers.removeFromMenu("sandwich", restaurantOne));
    }

    @org.junit.Test
    public void cook() {
        assertEquals("There is not a recipe for that", UIHelpers.cook("sandwich", "1", restaurantOne));
    }

}
