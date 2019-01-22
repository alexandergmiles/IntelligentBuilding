package buildingproject;

import javafx.scene.paint.Stop;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author Alexander Miles
 * Base SimObject that other object in the simulation can inherit
 */
public abstract class SimObject {
    private Point Location;
    private ArrayList<Point> Path;
    private boolean Stopped;
    private char colour;
    /**
     * Constructor to create a SimObject at given location
     * @param Point position
     */
    SimObject(Point position)
    {
        Location = position;
        Path = new ArrayList<>();
        colour = 'r';
    }
    /**
     * Get the current position for the SimObject object
     * @return Point representing the position
     */
    public Point getLocation()
    {
        return Location;
    }

    /**
     * set the SimObject's location
     * @param  the point of which the SimObject should be set at
     */
    public void setLocation(Point value)
    {
        Location = value;
    }

    /**
     * add new Point to path
     * @param point Next path point
     */

    public void setPath(Point point)
    {
        Path.add(point);
    }

    /**
     * clear the path the SimObject has to follow
     */
    public void clearPath()
    {
        Path.clear();
    }

    /**
     * Getter of stopped
     * @return stopped value
     */
    public boolean getStopped()
    {
        return Stopped;
    }
    /**
     * set SimObject as being stopped or not
     * @param Stop boolean value to set
     */
    public void setStopped(Boolean value)
    {
        Stopped = value;
    }

    public void setColour(char value)
    {
        colour = value;
    }
    /**
     * Draw SimObject in building
     * @param bi Building interface
     */
    public void draw(BuildingGUI gui)
    {
        gui.showItem(Location.x, Location.y, 4, colour);
    }

    /**
     * attempt to move SimObject unless it is stopped
     */
    public void update()
    {
        if(Stopped)
        {

        } else if(checkPath(Path.get(0)))
        {
            Stopped = true;
        }
        else
        {
            move(Path.get(0));
        }
    }
    //Checks to see if simobject has reached its destination
    /**
     *Check to see if we've arrived at the path
     * @param path
     * @return a boolean value based on if the SimObject is at the final location
     */
    private boolean checkPath(Point path)
    {
        return (path.x == Location.x) && (path.y == Location.y);
    }

    /**
     * move one step towards the given position
     * @param path The path
     */
    private void move(Point path)
    {
        int x = 0;
        int y = 0;

        x = Integer.signum((path.x+1) - (Location.x+1));
        y = Integer.signum((path.y+1) - (Location.y+1));

        Location.translate(x,y);
    }

    //This has to be defined by each child object
    public abstract String toString();
}
