package buildingproject;

public class Config {
    private static Config instance;
    private int sleepTime;
    private int currentPeople;
    private String saveLocation;

    /**
     * Default constructor made private - Singleton pattern
     * We dont want people initalising objects of this class
     */
    private Config() {
    }

    /**
     * Use this to get the single instance of the class.
     * @return returns the instance of config
     */
    public static Config getInstance()
    {
        if(instance == null)
        {
            instance = new Config();
        }
        return instance;
    }

    /**
     * Getter and setter
     * @return int sleepTime
     */
    public int getSleepTime()
    {
        return sleepTime;
    }

    public void setSleepTime(int value)
    {
        sleepTime = value;
    }
    /**
     * Getter and setter
     * @return int currentPeople
     */
    public int getCurrentPeople()
    {
        return currentPeople;
    }

    public void setCurrentPeople(int value)
    {
        currentPeople = value;
    }
    /**
     * Getter and setter
     * @return int SaveLocation
     */
    public String getSaveLocation()
    {
        return saveLocation;
    }

    public void setSaveLocation(String value)
    {
        saveLocation = value;
    }
}
