package buildingproject;

import sun.security.ssl.Debug;

import javax.xml.stream.Location;
import java.awt.Point;
import java.util.ArrayList;

/**
 * @author Alexander Miles
 */
public class Person extends SimObject {
	//Used to check if they are at the door
	//Not in SimObject because not all SimObject can leave room
	public boolean hasReachedDoor = false;

	Person(Point position) {
		super(position);
	}
	/**
	 * Defining a custom toString method
	 * @return A string defining persons location
	 */
	public String toString() {
		return "Person at " + getLocation().x + ", " + getLocation().y;
	}
}