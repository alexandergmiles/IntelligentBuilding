package buildingproject;

/**
 * @author Alexander Miles
 */
public class Config {
    //instance used to contain the single instance
    private static Config instance;
    //Some variables that could be used in sim
    private int sleepTime;
    private int currentPeople;
    //The path the config file will be saved
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

    public void SaveConfig()
    {

    }

    public void LoadConfig()
    {
        
    }
}
