import com.example.Simulation;

import static org.junit.Assert.*;

public class SimulationTest {

    @org.junit.Test
    public void printWelcomeMessage() {
        assertEquals("Welcome to your Restaurant Simulation. You have $10,000. " +
                "Have fun, and type 'exit' when you wish to end the simulation.", Simulation.printWelcomeMessage());
    }

}
