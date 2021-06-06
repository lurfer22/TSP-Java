package view;

import algoname.AlgorithmName;
import canvasWrapper.CanvasWrapper;
import city.City;

import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;
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

import java.awt.*;
import java.util.List;

public class View extends Application {
    private final int rectHeight = 10;
    private final int rectWidth = 10;
    private final int canvasHeight = 500;
    private final int canvasWidth = 500;
    private final CanvasWrapper canvas = new CanvasWrapper(new Canvas(canvasWidth, canvasHeight));

    private Controller control;
    private int numberOfCities = 5;


    @Override
    public void start(Stage stage) throws Exception {
        initializeController();
        BorderPane main = new BorderPane();

        Label title = new Label("Travelling Salesperson");
        title.setStyle("-fx-font-size: 30px; -fx-font-weight: bold");

        VBox canvasHolder = new VBox(canvas.getCanvas());
        canvasHolder.setMaxWidth(canvasWidth);
        canvasHolder.setMaxHeight(canvasHeight);
        canvasHolder.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        canvasHolder.setAlignment(Pos.BASELINE_LEFT);
        canvasHolder.setStyle("-fx-background-color: white;");

        HBox canvasAndComboBox = new HBox(canvasHolder, createAlgoOptionsAndSolveButton());
        canvasAndComboBox.setSpacing(10);

        HBox drawAndClearCities = new HBox(createClearButton(), createNumberOfCitiesSlider(), createDrawCitiesButton());

        VBox topBox = new VBox(title, canvasAndComboBox, drawAndClearCities);
        main.setTop(topBox);

        main.setStyle("-fx-background-color: gray");
        main.setPadding(new Insets(20, 0, 0, 20));

        Scene scene = new Scene(main, 750, 650);
        stage.setScene(scene);
        stage.show();
    }

    private void initializeController() {
        control = new Controller(new Model(numberOfCities));
        // change the bounds for city x and y coords
        control.setUpperBounds(canvasWidth, canvasHeight);
        // set the minimum distance between cities being generated
        control.setMinimumCityDistance(rectWidth, rectHeight);
    }

    private void drawCitiesAndPath() {
        this.canvas.clear();
        drawCities();
        drawPath();
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

    private void drawPath() {
        int animationTimeMillis = 250;
        List<City> cityPath = control.getCompletedPath();

        final int[] count = new int[]{1};
        PauseTransition newPause = new PauseTransition(Duration.millis(animationTimeMillis));
        newPause.setOnFinished(e -> {
            if (count[0] != cityPath.size() - 1) {
                drawLineBetweenCities(cityPath.get(count[0]), cityPath.get(count[0] + 1));
                count[0]++;
                newPause.play();
            }
        });

        PauseTransition pause = new PauseTransition(Duration.millis(animationTimeMillis));
        pause.setOnFinished(e -> {
            canvas.setLineColor(Color.RED);
            drawLineBetweenCities(cityPath.get(0), cityPath.get(1));
            canvas.setLineColor(Color.BLACK);
            newPause.play();
        });
        pause.play();

        // since we can only use final variables inside a lambda function,
        // we can pass in a final array and only change the indices of the arr


    }

    private void drawLineBetweenCities(City from, City to) {
        canvas.drawLine(from.getX() + (rectWidth/2), from.getY() + (rectWidth/2), to.getX() + (rectWidth/2), to.getY() + (rectHeight/2));
    }

    private Button createClearButton() {
        Button clear = new Button("Clear Screen");
        clear.setOnMouseClicked(e -> {
            this.canvas.clear();
        });
        return clear;
    }

    private Button createDrawCitiesButton() {
        Button draw = new Button("Update City Count");
        draw.setOnMouseClicked(e -> {
            initializeController();
            this.canvas.clear();
            drawCities();
        });
        return draw;
    }

    private VBox createNumberOfCitiesSlider() {
        Label numCities = new Label("Number of Cities: 5");
        numCities.setAlignment(Pos.CENTER);
        numCities.setStyle("-fx-font-size: 12");

        Slider val = new Slider(5, 50, 5);
        val.setShowTickMarks(true);
        val.setShowTickLabels(true);
        val.setMinWidth(306);

        val.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                numCities.setText("Number of Cities: " + t1.intValue());
                numberOfCities = t1.intValue();
            }
        });

        VBox labelSlider = new VBox(numCities, val);
        labelSlider.setAlignment(Pos.CENTER);
        return labelSlider;
    }

    private HBox createAlgoOptionsAndSolveButton() {
        ChoiceBox<String> algorithmOptions = new ChoiceBox<String>();
        algorithmOptions.getItems().addAll("Nearest Neighbor", "Greedy");

        Button solve = new Button("Draw Path");
        solve.setOnMouseClicked(e -> {
            String selection = algorithmOptions.getValue();
            if (selection == null) {
                algorithmOptions.setValue("Nearest Neighbor");
                control.solve(AlgorithmName.NEAREST_NEIGHBOR);
            } else {
                switch (selection) {
                    case "Nearest Neighbor" -> control.solve(AlgorithmName.NEAREST_NEIGHBOR);
                    case "Greedy" -> control.solve(AlgorithmName.GREEDY);
                }
            }
            drawCitiesAndPath();
        });


        HBox ret = new HBox(algorithmOptions, solve);
        ret.setSpacing(5);

        return ret;
    }

    public static void main(String [] args) {
        launch(args);
    }
}
