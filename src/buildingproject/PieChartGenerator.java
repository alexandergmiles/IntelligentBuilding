package buildingproject;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javafx.scene.Group;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class PieChartGenerator extends Application {
    private ObservableList<PieChart.Data> data;
    /*
    *Building the pie chart and showing it
     */
    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(new Group());
        primaryStage.setTitle("Rooms visited");
        primaryStage.setWidth(500);
        primaryStage.setHeight(500);
        final PieChart chart = new PieChart(data);
        chart.setTitle("Rooms visited");
        Group root = new Group();
        root.getChildren().add(chart);
        scene.setRoot(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setPieChartData(ArrayList<PieChart.Data> passedData) {
        data = FXCollections.observableArrayList(passedData);
    }
}
