package buildingproject;

import java.util.*;
import java.util.concurrent.*;

public class AdvancedRoom extends Room {
    private double temperature = 20;
    private int PreviousSecond = 0;

    AdvancedRoom(String rStr)
    {
        super(rStr);
    }

    @Override
    public void update() {
        calcTemp();
    }

    public double getTemperature()
    {
        return temperature;
    }

    private double calcTemp()
    {
        return (temperature + 0.05 * temperature) / 10.0;
    }
}
