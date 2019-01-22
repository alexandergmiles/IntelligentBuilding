package buildingproject;

import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * @author shsmchlr
 * Class for a building ... which will have rooms ...
 */
public class Building {
        /**
     * The building in which there are various rooms and objects
     * Its size is defined by xSize,ySize
     * Variables are used for actual rooms
     */
    private int xSize = 10;                        // size of building
    private int ySize = 10;
    private ArrayList<Room> allRooms;            // array of rooms
    private ArrayList<Room> occupiedRooms;
    private ArrayList<Person> occupants;
    private Random ranGen;                        // for generating random numbers
    private boolean firstRun = true;
    private int timeInRoom = 0;
    private Config config;
    private int iterations;
    private ArrayList<PieChart.Data> roomsVisited;
    private ArrayList<Hoover> hoovers;
    private int[] rooms;
    /**
     * Construct a building
     */
    public Building(String bs) {
        allRooms = new ArrayList<Room>();        // create space for rooms
        occupants = new ArrayList<Person>();
        occupiedRooms = new ArrayList<Room>();
        roomsVisited = new ArrayList<PieChart.Data>();
        hoovers = new ArrayList<Hoover>();
        ranGen = new Random();                  // create object for generating random numbers
        config = Config.getInstance();          //Singleton pattern: we need to get the only instance
        config.setCurrentPeople(20);
        setBuilding(bs);                        // now set building using string bs

        rooms = new int[allRooms.size()];
        for(int i = 0; i < allRooms.size(); i++)
        {
            rooms[i] = 0;
        }
    }

    private Room getRoom(int id) {
        return allRooms.get(id);
    }

    /**
     * set up the building, as defined in string
     *
     * @param bS of form xS,yS;x1 y1 x2 y2 xd yd ds;  etc
     *           xS,yS define size, and for each room have locations of opposite corners, door and door size
     */
    public void setBuilding(String bS) {
        allRooms.clear();
        StringSplitter bSME = new StringSplitter(bS, ";");        // split strings by ;
        StringSplitter bSz = new StringSplitter(bSME.getNth(0, "5 5"), " ");    // split first by space
        xSize = bSz.getNthInt(0, 5);                        // get first of the first string, being xsize
        ySize = bSz.getNthInt(1, 5);
        for (int ct = 1; ct < bSME.numElement(); ct++)        // remaining strings define rooms
            allRooms.add(new AdvancedRoom(bSME.getNth(ct, "")));    // add each in turn
        addPerson();                                        // now add a person
    }

    public ArrayList<Room> getAllRooms()
    {
        return allRooms;
    }

    /**
     * On arena size
     *
     * @return size in x direction of robot arena
     */
    public int getXSize() {
        return xSize;
    }

    /**
     * On arena size
     *
     * @return size in y direction of robot arena
     */
    public int getYSize() {
        return ySize;
    }

    /**
     * set new destination for person and path to it
     * In this version puts person in random room and sets path from there to room's door
     *
     * @param occupant
     */
    void setNewRoom(Person occupant) {
        int cRoom = whichRoom(occupant.getLocation());
        int dRoom = 0;
        while (dRoom == cRoom) dRoom = ranGen.nextInt(allRooms.size());    // get another room randomlt
        occupant.clearPath();
        for (int i = 0; i < allRooms.size(); i++) {
            if (occupant.getLocation().x == allRooms.get(i).getByDoor(-1).x) {
                occupant.hasReachedDoor = true;
                allRooms.get(i).CountOfPeople++;
                break;
            }
        }

        if (occupant.hasReachedDoor && firstRun) {
            occupant.setPath(allRooms.get(cRoom).getRandom(ranGen));
            timeInRoom++;
            firstRun = false;
        }else if(occupant.hasReachedDoor && timeInRoom == 1) {
            int room = whichRoom(occupant.getLocation());
            rooms[room]++;
            occupant.setPath(allRooms.get(dRoom).getByDoor(-1));
            timeInRoom = 0;
        }
        else if (occupant.hasReachedDoor) {
            if (timeInRoom == 1) {
                occupant.setPath(allRooms.get(dRoom).getByDoor(-1));
                int room = whichRoom(occupant.getLocation());
                rooms[room]++;
            } else {
                occupant.setPath(allRooms.get(cRoom).getRandom(ranGen));
                timeInRoom++;
                occupant.hasReachedDoor = false;
            }
        }
        else
        {
            occupant.setPath(allRooms.get(cRoom).getByDoor(-1));
            timeInRoom = 0;
        }

        if (firstRun) {
            occupant.setLocation(allRooms.get(cRoom).getRandom(ranGen));
            occupant.setPath(allRooms.get(cRoom).getByDoor(-1));        // position by door
            firstRun = false;
            timeInRoom++;
        }
        if(!occupant.hasReachedDoor)
        {
        }
        occupant.setStopped(false);                                // say person can move

        for (int i = 0; i < allRooms.size(); i++) {
            int countOfPeopleInRoom = 0;
            for (Person pr : occupants) {
                int roomLocation = whichRoom(pr.getLocation());
                if(roomLocation == i)
                {
                    countOfPeopleInRoom++;
                }
            }
            if(countOfPeopleInRoom > 0)
            {
                allRooms.get(i).update();
                allRooms.get(i).setLights(true);

            }
            else
            {
                allRooms.get(i).setLights(false);
            }
            allRooms.get(i).CountOfPeople = countOfPeopleInRoom;
        }
    }
    /**
     * calculate a random room number
     *
     * @return number in range 0.. number of rooms
     */
    public int randRoom() {
        return ranGen.nextInt(allRooms.size()-1);
    }

    /**
     * create new person and set path for it to follow
     */
    public void addPerson() {
        for(int i = 0; i < config.getCurrentPeople(); i++) {
                occupants.add(new Person(allRooms.get(0).getRandom(ranGen)));    // create person in first room
                setNewRoom(occupants.get(i));

            allRooms.get(0).CountOfPeople = 1;
        }
        for(int i = 0; i < 3; i++)
        {
            hoovers.add(new Hoover(getRoom(i).getRandom(ranGen)));
            hoovers.get(i).setColour('b');
        }
    }

    /**
     * show all the building's rooms and person in the interface
     *
     * @param bi the interface
     */
    public void showBuilding(BuildingGUI bi) {
        for (Room r : allRooms) r.showRoom(bi);
        // loop through array of all rooms, displaying each
        for(Person occupant : occupants) {
            occupant.draw(bi);
        }
        for(Hoover hoover : hoovers)
        {
            hoover.draw(bi);
        }
    }

    public void update() {
        for (Room rm : allRooms)
        {
            rm.update();
        }
        for (Person occupant : occupants) {
            if (occupant.getStopped()) setNewRoom(occupant);
            else occupant.update();
        }
        for(Hoover hv : hoovers)
        {
            hv.setPath(allRooms.get(whichRoom(hv.getLocation())).getRandom(ranGen));
            hv.update();
        }
        iterations++;
    }

    /**
     * method to determine which room position x,y is in
     *
     * @param xy
     * @return n, the number of the room or -1 if in corridor
     */
    public int whichRoom(Point xy) {
        int ans = allRooms.size();
        for (int ct = 0; ct < allRooms.size() ; ct++)
            if (allRooms.get(ct).isInRoom(xy)) ans = ct;
        return ans;
    }

    /**
     * method to return information bout the building as a string
     */
    public String toString() {
        String s = "Building size " + getXSize() + "," + getYSize() + "\n";
        for (Room r : allRooms) s = s + r.toString() + "\n";
        //s = s + occupant.toString();
        return s;
    }

    public void showGraph()
    {
        if(iterations > 1000)
        {
            PieChartGenerator gen = new PieChartGenerator();
            for(int i = 0; i < rooms.length; i++)
            {
                roomsVisited.add(new PieChart.Data(Integer.toString(i), rooms[i]));
            }
            gen.setPieChartData(roomsVisited);
            Stage newStage = new Stage();
            gen.start(newStage);
            roomsVisited.clear();
            iterations = 0;
        }
    }
}