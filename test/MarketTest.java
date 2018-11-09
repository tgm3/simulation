import com.example.*;
import com.google.gson.Gson;

import static org.junit.Assert.*;

public class MarketTest {

    private static Market marketOne;
    private static Restaurant restaurantOne;

    @org.junit.Before
    public void setUp(){
        Gson gson = new Gson();
        marketOne = gson.fromJson(Simulation.getFileContentsAsString("testMarketOne.json"), Market.class);
        restaurantOne = gson.fromJson(Simulation.getFileContentsAsString("testRestaurantOne.json"), Restaurant.class);
    }

    @org.junit.Test
    public void printAvailableFoods() {
        assertEquals("bread: 10.0\n", Market.listFood(marketOne));
    }

    @org.junit.Test
    public void printAvailableEquipment() {
        assertEquals("toaster: 30.0\n", Market.listEquipment(marketOne));
    }

    @org.junit.Test
    public void printAvailableRecipes() {
        assertEquals("sandwich: 10.0\n", Market.listRecipe(marketOne));
    }

    @org.junit.Test
    public void attemptToBuyFood() {
        assertEquals("You have successfully bought 5.0 bread",
                Market.buyFromMarket("5", "bread", marketOne, restaurantOne));
    }

    @org.junit.Test
    public void attemptToBuyRecipe() {
        assertEquals("You have successfully bought a sandwich",
                Market.buyFromMarket("1", "sandwich", marketOne, restaurantOne));
    }

    @org.junit.Test
    public void attemptToBuyEquipment() {
        assertEquals("You have successfully bought 5.0 toaster",
                Market.buyFromMarket("5", "toaster", marketOne, restaurantOne));
    }

    @org.junit.Test
    public void notEnoughMoney() {
        assertEquals("You do not have enough money",
                Market.buyFromMarket("500", "toaster", marketOne, restaurantOne));
    }

    @org.junit.Test
    public void attemptToSellFood() {
        assertEquals("You have successfully sold 5.0 bread",
                Market.sellToMarket("5", "bread", marketOne, restaurantOne));
    }

    @org.junit.Test
    public void notEnoughFood() {
        assertEquals("You do not have that many bread",
                Market.sellToMarket("20", "bread", marketOne, restaurantOne));
    }

    @org.junit.Test
    public void attemptToSellFakeFood() {
        assertEquals("dirt does not exist",
                Market.sellToMarket("1", "dirt", marketOne, restaurantOne));
    }

    @org.junit.Test
    public void attemptToSellEquipment() {
        assertEquals("You have successfully sold 1.0 toaster",
                Market.sellToMarket("1", "toaster", marketOne, restaurantOne));
    }

    @org.junit.Test
    public void notEnoughEquipment() {
        assertEquals("You do not have that many toaster",
                Market.sellToMarket("20", "toaster", marketOne, restaurantOne));
    }

    @org.junit.Test
    public void attemptToSellFakeEquipment() {
        assertEquals("laser does not exist",
                Market.sellToMarket("1", "laser", marketOne, restaurantOne));
    }

    @org.junit.Test
    public void attemptToSellRecipe() {
        assertEquals("You do not have sandwich",
                Market.sellToMarket("1", "sandwich", marketOne, restaurantOne));
    }

    @org.junit.Test
    public void notEnoughRecipe() {
        assertEquals("You do not have sandwich",
                Market.sellToMarket("20", "sandwich", marketOne, restaurantOne));
    }

    @org.junit.Test
    public void attemptToSellFakeRecipe() {
        assertEquals("pizza does not exist",
                Market.sellToMarket("1", "pizza", marketOne, restaurantOne));
    }
}
