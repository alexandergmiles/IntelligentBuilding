package buildingproject;

import java.util.*;
import java.util.concurrent.*;

public class AdvancedRoom extends Room {
    //Poor attempt at making temp work
    private double temperature = 20;
    private int PreviousSecond = 0;

    /**
     * Calling base class constructor
     * @param rStr building constructing string
     */
    AdvancedRoom(String rStr)
    {
        super(rStr);
    }

    /**
     * Overriding the abstract base class method. As it was defined abstract we have to define it
     */
    @Override
    public void update() {
        calcTemp();
    }

    /**
     * getter of temperature
     * @return returning temperature
     */
    public double getTemperature()
    {
        return temperature;
    }

    //Calculating the temperature. Badly.
    private double calcTemp()
    {
        return (temperature + 0.05 * temperature) / 10.0;
    }
}
