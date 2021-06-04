package controller;

import algoname.AlgorithmName;
import city.City;
import model.Model;
import path.Path;
import java.util.List;

public class Controller {
    private Model model;

    public Controller(Model model) {
        this.model = model;
    }

    public void setNumberOfCities(int newAmount) {
        try {
            this.model.setNumberOfCities(newAmount);
        } catch(IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setMinimumCityDistance(int x, int y) {
        try {
            this.model.setMinimumCityDistance(x, y);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<City> getCities() {
        return this.model.getCities();
    }

    public List<City> getCompletedPath() {
        return this.model.getSolvedPath();
    }

    public void setUpperBounds(int x, int y) {
        try {
            this.model.setUpperBounds(x, y);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean setStartingCity(City city) {
        boolean check = this.model.setStartingCity(city);
        if (!check)
            System.out.println("Trying to set a starting city that " +
                                "is not included in the available cities.");

        return check;
    }

    public void solve(AlgorithmName type) {
        this.model.solvePath(type);
    }
}
