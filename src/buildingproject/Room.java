package buildingproject;


import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Alexander Miles
 * Room class
 */
public abstract class Room {
	//The coordinates of the room
	private int pointX1;
	private int pointX2;
	private int pointY1;
	private int pointY2;
	private int pointXd;
	private int pointYd;
	private int ds;
	//Boolean value of the light state
	private boolean lightsOn = false;
	public int CountOfPeople = 0;

	/**
	 * Default constructor
	 */
	public Room() {

	}
	/**
	 * construct room
	 * @param rStr	has xcoords of opposite corners and door as numbers separated by spaces
	 */
	public Room (String rStr) {
		StringSplitter m = new StringSplitter(rStr, " ");
		pointX1 = m.getNthInt(0, 0);
		pointY1 = m.getNthInt(1, 0);
		pointX2 = m.getNthInt(2, 5);
		pointY2 = m.getNthInt(3, 5);
		pointXd = m.getNthInt(4, 0);
		pointYd = m.getNthInt(5, 2);
		ds = m.getNthInt(6, 1);
	}

	/**
	 * return size of door
	 * @return int size of door
	 */
	public int getDoorSize() {
		return ds;
	}

	/**
	 * doWall			draw wall, but check if door there
	 * @param bi		interface into which it is shown
	 * @param x1		start x coord of wall
	 * @param y1		and y 
	 * @param x2		end x coord of wall
	 * @param y2		and y
	 */
	private void doWall (BuildingGUI bi, int x1, int y1, int x2, int y2) {
		if ( (x1 == x2) && (pointXd == x1) ) {			// if vert wall, check if door in it
			bi.showWall(x1, y1, x2, pointYd-1);
			bi.showWall(x1, pointYd+ds, x2, y2);
		}
		else if ( (y1 == y2) && (pointYd == y1) ) {		// similar for horiz wall
			bi.showWall(x1, y1, pointXd-1, pointYd);
			bi.showWall(pointXd+ds, pointYd, x2, y2);
		}
		else {
			bi.showWall(x1, y1, x2, y2);
		}
	}

	/**
	 * show the room into the interface bi
	 * @param bi
	 */
	public void showRoom (BuildingGUI bi) {
		doWall(bi, pointX1, pointY1, pointX1, pointY2);			// show each of its walls
		doWall(bi, pointX2, pointY1, pointX2, pointY2);
		doWall(bi, pointX1, pointY2, pointX2, pointY2);
		doWall(bi, pointX1, pointY1, pointX2, pointY1);
	}
	
	/**
	 * get random xy position within rooom, used to send person to
	 * @param r		random generator to use
	 * @return		coordinate
	 */
	public Point getRandom(Random r) {
		return new Point (pointX1 + 1 + r.nextInt(pointX2-pointX1-2),pointY1 + 1 + r.nextInt(pointY2-pointY1-2) );
	}
	/**
	 * return Point value of the door
	 * @param forinside     being 1 if inside 0 if at door and -1 if outside
	 * @return Point variable of the door
	 */
	public Point getByDoor(int forinside) {
		int x = pointXd;
		int y = pointYd;

		//Need to find which wall door is on, and act accordingly
		if (pointYd == pointY2) { 
			y = pointYd + forinside;
			x = pointXd + ds / 2;
		}
		else if (pointYd == pointY1) { 
			y = pointYd - forinside;
			x = pointXd + ds / 2;
		}
		else if (pointXd == pointX1) { 
			x = pointXd - forinside;
			y = pointYd + ds / 2;
		}
		else { 
			x = pointXd + forinside;
			y = pointYd + ds / 2;
		}
		return new Point(x,y);
	}

	/**
	 * Getter and setter for lights
	 * @return Lights value
	 */
	public boolean getLights()
	{
		return lightsOn;
	}
	/**
	 * Set lights
	 * @param value boolean value of lights
	 */
	public void setLights(boolean value)
	{
		lightsOn = value;
	}

	/**
	 * Checking to see if value is in room
	 * @param p Point value to be checked
	 * @return Boolean value based on if point is within bounds of rooms walls
	 */
	public boolean isInRoom(Point P) {
		return (P.x >= pointX1 && P.x <= pointX2 && P.y >= pointY1-1 && P.y <= pointY2-1);
	}
	
	/**
	 * Custom toString definition
	 */
	public String toString() {
		return "Room from " + pointX1 + "," + pointY1 + " to " + pointX2 + "," + pointY2 + " door at " + pointXd + "," + pointYd;
	}

	//Subclasses must override this and implement custom logic
	public abstract void update();
}