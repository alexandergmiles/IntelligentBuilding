package buildingproject;


import java.io.File;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


/**
 * @author Alexander Miles
 */
public class BuildingGUI extends Application {

    private Stage stagePrimary;
    private Building theBuilding;
    //Vertical box where building info will be stored
    private VBox rtPane;
    private VBox lightPane;
    //Graphics Context allows us to draw
    private GraphicsContext gc;
    //Timer for the animation
    private AnimationTimer timer;
    private int whichBuild;
    private static Config config;
    /**
     * @param args
     */
    public static void main(String[] args) {
        config = Config.getInstance();
        // TODO Auto-generated method stub
        Application.launch(args);
    }

    /**
     * Alert message box
     * @param TStr title of message block
     * @param CStr content of message
     */
    private void showMessage(String TStr, String CStr) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(TStr);
        alert.setHeaderText(null);
        alert.setContentText(CStr);
        alert.showAndWait();
    }
    /**
     * Show welcome message
     */
    private void showWelcome() {
        showMessage("Welcome", "Welcome to Alexander Miles' Intelligent Building!");
    }

    /**
     * set up the menu of commands for the GUI
     *
     * @return the menu bar
     */
    MenuBar setMenu() {
        // initially set up the file chooser to look for cfg files in current directory
        MenuBar menuBar = new MenuBar();                    // create main menu

        //Adding file dropdown
        Menu mFile = new Menu("File");
        //Exit menu item
        MenuItem mExit = new MenuItem("Exit");
        //Action for exiting
        mExit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                timer.stop();
                System.exit(0);
            }
        });
        //Add menu items
        mFile.getItems().addAll(mExit);

        Menu mHelp = new Menu("Help");
        MenuItem mWelcome = new MenuItem("Welcome");
        mWelcome.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showWelcome();
            }
        });
        mHelp.getItems().addAll(mWelcome);

        menuBar.getMenus().addAll(mFile, mHelp);            // set main menu with File, Config, Run, Help
        return menuBar;                                                    // return the menu
    }

    /**
     * Container to store buttons
     * @return HBox
     */
    private HBox setButtons() {

        Button btnNewBuild = new Button("New Building");
        btnNewBuild.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                theBuilding = new Building(buildingString());
                drawBuilding();                                // then redraw arena
            }
        });

        Button btnStart = new Button("Start");
        btnStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timer.start();                                // whose action is to start the timer
            }
        });

        Button btnStop = new Button("Pause");
        btnStop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timer.stop();                                // and its action to stop the timer
            }
        });

        // now add these buttons + labels to a HBox
        HBox hbox = new HBox(new Label("Config: "), btnNewBuild,
                new Label("Run: "), btnStart, btnStop);
        return hbox;
    }

    /**
     * function to convert char c to actual colour used
     *
     * @param c
     * @return Color
     */
    Color colFromChar(char c) {
        Color ans = Color.BLACK;
        switch (c) {
            case 'y':
                ans = Color.YELLOW;
                break;
            case 'r':
                ans = Color.RED;
                break;
            case 'g':
                ans = Color.GREEN;
                break;
            case 'b':
                ans = Color.BLUE;
                break;
            case 'k':
                ans = Color.BLACK;
                break;
            case 'o':
                ans = Color.ORANGE;
                break;
            case 'p':
                ans = Color.PINK;
                break;
            case 'c':
                ans = Color.CYAN;
                break;
        }
        return ans;
    }

    /**
     * show a Line from first xy point to second xy point, with given width and colour
     *
     * @param xy1   is xy1[0] is x, xy1[1] is y
     * @param xy2
     * @param width
     * @param col
     */
    void showLine(int[] xy1, int[] xy2, int width, char col) {
        gc.setStroke(colFromChar(col));                    // set the stroke colour
        gc.setLineWidth(width);
        gc.strokeLine(xy1[0], xy1[1], xy2[0], xy2[1]);        // draw line
    }

    void showWall(int x1, int y1, int x2, int y2) {
        gc.setStroke(colFromChar('k'));                    // set the stroke colour
        gc.setLineWidth(3);
        gc.strokeLine(x1, y1, x2, y2);        // draw line
    }

    /**
     * Drawing character
     * @param x
     * @param y
     * @param size
     * @param col
     */
    void showItem(int x, int y, int size, char col) {
        //Setting file colour from character passed
        gc.setFill(colFromChar(col));
        //Creating the circle that represents a person
        gc.fillArc(x - size, y - size, size * 2, size * 2, 0, 360, ArcType.ROUND);
    }                                                        // fill 360 degree arc

    /**
     * Draw the building and details
     */
    void drawBuilding() {
        gc.setFill(Color.BEIGE);
        //Clearing canvas
        gc.fillRect(0, 0, theBuilding.getXSize(), theBuilding.getYSize());
        //Drawing the building and rooms
        theBuilding.showBuilding(this);
        //Clear for update
        rtPane.getChildren().clear();
        for (int i = 0; i <  theBuilding.getAllRooms().size(); i++) {
            //Label object with text
            Label RoomStatus = new Label(String.valueOf("Room status of lights: " + theBuilding.getAllRooms().get(i).getLights()));
            //If light is on, label is yellow, otherwise background is black
            if(theBuilding.getAllRooms().get(i).getLights())
            {
                RoomStatus.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
            }
            else
            {
                RoomStatus.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
            }
            rtPane.getChildren().add(RoomStatus);
        }

        for (int i = 0; i <  theBuilding.getAllRooms().size(); i++)
        {
            if(theBuilding.getAllRooms().get(i) instanceof AdvancedRoom)
            {
                Label temp = new Label(String.valueOf("Temperature reading: " + ((AdvancedRoom) theBuilding.getAllRooms().get(i)).getTemperature()));
                rtPane.getChildren().add(temp);
            }
            else {
                Label temp = new Label(String.valueOf("Temperature reading unavailable for this room"));
                rtPane.getChildren().add(temp);
            }
        }

        for (int i = 0; i <  theBuilding.getAllRooms().size(); i++)
        {
            //Count of people in each room
            Label CountOfPeople = new Label(String.valueOf("Currently there are: " + theBuilding.getAllRooms().get(i).CountOfPeople));
            rtPane.getChildren().add(CountOfPeople);
        }
    }

    /**
     * Return a string used to construct the buildings
     */
    public String buildingString() {
        whichBuild = 1 - whichBuild;
        if (whichBuild == 1)
            return "420 400;1 1 140 60 60 60 10;140 1 240 60 180 60 10;240 1 360 60 300 60 15;1 90 120 180 40 90 15;120 90 360 180 220 90 15";
        else
            return "400 400;90 10 320 70 220 70 10;10 180 100 380 60 180 15;100 180 320 380 200 180 20;";
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stagePrimary = primaryStage;
        stagePrimary.setTitle("Alexander Miles' Intelligent Building");
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(10, 20, 10, 20));
        //Placing menu at top
        bp.setTop(setMenu());

        //Creating the root group for items to be stored in
        Group root = new Group();
        //Canvas to store building
        Canvas canvas = new Canvas(500, 500);
        //Adding canvas to the parent object
        root.getChildren().add(canvas);
        //Set canvas location
        bp.setCenter(root);

        //Setting drawing context
        gc = canvas.getGraphicsContext2D();

        //Action on timer tick
        timer = new AnimationTimer() {                                            // set up timer
            public void handle(long currentNanoTime) {
                theBuilding.update();
                drawBuilding();
            }
        };

        //Initalising rtPane
        rtPane = new VBox();
        rtPane.setAlignment(Pos.TOP_LEFT);
        rtPane.setPadding(new Insets(5, 75, 75, 5));

        bp.setRight(rtPane);
        bp.setBottom(setButtons());
        Scene scene = new Scene(bp, 800, 600);
        bp.prefHeightProperty().bind(scene.heightProperty());
        bp.prefWidthProperty().bind(scene.widthProperty());

        //Setting up application
        primaryStage.setScene(scene);
        primaryStage.show();
        whichBuild = 0;
        theBuilding = new Building(buildingString());
        showWelcome();                                                        // set welcome message
        drawBuilding();
    }
}
