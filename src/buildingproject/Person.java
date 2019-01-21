package buildingproject;

import sun.security.ssl.Debug;

import java.awt.Point;
import java.util.ArrayList;

/**
 * @author Alexander Miles
 */
public class Person {

	//The current location of the person
	private Point personLocation;
	//Path to be followed
	private ArrayList<Point> path;
	private boolean stopped;
	//Used to check if they are at the door
	public boolean hasReachedDoor = false;

	/**
	 * Constructor to create a person at given location
	 * @param xys position
	 */
	Person(Point position) {
		personLocation = position;
		path = new ArrayList<Point>();
	}

	/**
	 * Get the current position for the person object
	 * @return Point representing the position
	 */
	public Point getPersonLocation() {
		return personLocation;
	}

	/**
	 * set the person's location
	 * @param setPerson the point of which the person should be set at
	 */
	public void setLocation(Point setPerson) {
		personLocation = setPerson;
	}

	/**
	 * set person as being stopped or not
	 * @param Stop boolean value to set
	 */
	public void setStopped(boolean Stop) {
		stopped = Stop;
	}

	/**
	 * Getter of stopped
	 * @return stopped value
	 */
	public boolean getStopped() {
		return stopped;
	}

	/**
	 * Draw person in building
	 * @param bi Building interface
	 */
	public void showPerson(BuildingGUI bi) {
		bi.showItem(personLocation.x, personLocation.y, 4, 'r');
	}

	/**
	 * Defining a custom toString method
	 * @return A string defining persons location
	 */
	public String toString() {
		return "Person at " + personLocation.x + ", " + personLocation.y;
	}

	/**
	 * clear the path the person has to follow
	 */
	public void clearPath() {
		path.clear();
	}

	/**
	 * add new personLocation to path
	 * @param Z Next path point
	 */
	public void setPath(Point Z) {
		path.add(Z);
	}

	/**
	 *Check to see if we've arrived at the path
	 * @param pathOfPerson
	 * @return a boolean value based on if the person is at the final location
	 */
	private boolean personsPath(Point pathOfPerson) {
		return (pathOfPerson.x == personLocation.x) && (pathOfPerson.y == personLocation.y);
	}

	/**
	 * move one step towards the given position
	 * @param pathOfPerson The path
	 */
	private void moveTowards(Point pathOfPerson) {
		int x = 0;
		int y = 0;

		//Giving us the direction to move depending on the target, 1, 0, or -1 depending on result of signum
		x = Integer.signum((pathOfPerson.x + 1) - (personLocation.x + 1));
		y = Integer.signum((pathOfPerson.y + 1) - (personLocation.y + 1));

		//Move to the position specified
		personLocation.translate(x, y);
	}

	/**
	 * attempt to move person unless it is stopped
	 */
	public void update() {
		if (stopped) {

		}
		else if (personsPath(path.get(0))){
			stopped = true;
		}
		else {
			moveTowards(path.get(0));
		}
	}
}