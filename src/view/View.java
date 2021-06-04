package view;

import algoname.AlgorithmName;
import canvasWrapper.CanvasWrapper;
import city.City;

import model.Model;
import controller.Controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;

public class View extends Application {
    private final int rectHeight = 10;
    private final int rectWidth = 10;
    private final int canvasHeight = 500;
    private final int canvasWidth = 500;
    private final Controller control = new Controller(new Model(15));
    private final CanvasWrapper canvas = new CanvasWrapper(new Canvas(canvasWidth, canvasHeight));

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane main = new BorderPane();
        HBox canvasHolder = new HBox(canvas.getCanvas());
        canvasHolder.setMaxWidth(canvasWidth);
        canvasHolder.setMaxHeight(canvasHeight);
        canvasHolder.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        canvasHolder.setAlignment(Pos.BASELINE_LEFT);

        main.setPadding(new Insets(20, 0, 0, 20));
        main.setTop(canvasHolder);

        control.setUpperBounds(canvasWidth, canvasHeight);
        control.setMinimumCityDistance(rectWidth, rectHeight);
        drawPath();
        drawCities();



        Scene scene = new Scene(main, 700, 700);
        stage.setScene(scene);
        stage.show();

    }

    private void drawPath() {
        control.solve(AlgorithmName.NEAREST_NEIGHBOR);

        List<City> cityPath = control.getCompletedPath();
        canvas.setLineColor(Color.RED);
        drawLineBetweenCities(cityPath.get(0), cityPath.get(1));
        canvas.setLineColor(Color.BLACK);

        for (int i = 1; i < cityPath.size() - 1; i++) {
            drawLineBetweenCities(cityPath.get(i), cityPath.get(i + 1));

        }
    }

    private void drawLineBetweenCities(City from, City to) {
        canvas.drawLine(from.getX() + (rectWidth/2), from.getY() + (rectWidth/2), to.getX() + (rectWidth/2), to.getY() + (rectHeight/2));
    }

    private void drawCities() {
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
