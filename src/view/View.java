package view;

import algoname.AlgorithmName;
import canvasWrapper.CanvasWrapper;
import city.City;
import javafx.scene.layout.HBox;
import model.Model;
import controller.Controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import path.Path;

import java.util.List;


public class View extends Application {
    private final Controller control = new Controller(new Model(10));
    private final CanvasWrapper canvas = new CanvasWrapper(new Canvas(500, 500));
    private final int rectWidth = 10;
    private final int rectHeight = 10;

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane main = new BorderPane();
        control.setUpperBounds(500, 500);
        drawPath();
        drawCities();


        main.setCenter(canvas.getCanvas());
        Scene scene = new Scene(main, 700, 700);
        stage.setScene(scene);
        stage.show();

    }

    public void drawPath() {
        control.solve(AlgorithmName.NEAREST_NEIGHBOR);

        List<City> cityPath = control.getCompletedPath();
        canvas.setLineColor(Color.RED);
        drawLineBetweenCities(cityPath.get(0), cityPath.get(1));
        canvas.setLineColor(Color.BLACK);

        for (int i = 1; i < cityPath.size() - 1; i++) {
            drawLineBetweenCities(cityPath.get(i), cityPath.get(i + 1));

        }
    }

    public void drawLineBetweenCities(City from, City to) {
        canvas.drawLine(from.getX() + (rectWidth/2), from.getY() + (rectWidth/2), to.getX() + (rectWidth/2), to.getY() + (rectHeight/2));
    }

    public void drawCities() {
        List<City> cities = control.getCities();

        canvas.setFillColor(Color.RED);
        canvas.drawRect(cities.get(0).getX(), cities.get(0).getY(), rectWidth, rectHeight);
        canvas.setFillColor(Color.BLACK);
        for (int i = 1; i < cities.size(); i++) {
            City city = cities.get(i);
            canvas.drawRect(city.getX(), city.getY(), rectWidth, rectHeight);
        }
    }

    public static void main(String [] args) {
        launch(args);
    }
}
