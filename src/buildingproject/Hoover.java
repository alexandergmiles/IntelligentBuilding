package buildingproject;

import java.awt.*;

public class Hoover extends SimObject {

    public Hoover(Point position) {
        super(position);
    }

    public String toString() {
        return "Hoover is at x: " + getLocation().x + " y: " + getLocation().y;

    }

    @Override
    public void update() {
        setStopped(false);
    }
}
