package model;

import path.Path;
import city.City;
import algoname.AlgorithmName;

import java.sql.Array;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class Model {
    private Path path;
    private List<City> cities;
    private int upperBoundX;
    private int upperBoundY;

    public Model(int numOfCities) {
        this.cities = new ArrayList<City>(numOfCities);
        this.generateCities(numOfCities);

        this.path = new Path(this.cities);

        this.upperBoundX = 200;
        this.upperBoundY = 200;
    }

    public void setNumberOfCities(int newCityAmount) {
        // since we are updating number of cities, we need to create a new cities array, with fresh city objects,
        // and create a new path array.

        // a better way to do this, or quicker, would be to either remove city objects in our city array to match the new number
        // or add new ones to do the same
        if (newCityAmount < this.cities.size()) {
            this.cities.subList(newCityAmount, this.cities.size()).clear();
        } else if (newCityAmount > this.cities.size()) {
            this.generateCities(newCityAmount - this.cities.size());
        }

        this.path = new Path(this.cities);
    }

    // since I update the upper bounds, we need to also erase our cities array and push new city objects into it.
    public void setUpperBounds(int x, int y) {
        this.upperBoundX = x;
        this.upperBoundY = y;

        int tempSize = this.cities.size();
        this.cities = new ArrayList<City>();

        this.generateCities(tempSize);
    }

    public void setStartingCity(City city) {
        this.path.setStartingCity(city);
    }

    public void solvePath(AlgorithmName type) {

    }

    public void generateCities(int numOfCities) {
        Random rand = new Random();
        for (int i = 0; i < numOfCities; i++) {
            City newCity = new City(rand.nextInt(this.upperBoundX), rand.nextInt(this.upperBoundY));

            while (this.cities.contains(newCity)) {
                newCity = new City(rand.nextInt(this.upperBoundX), rand.nextInt(this.upperBoundY));
            }

            this.cities.add(newCity);
        }
    }

    public Path getSolvedPath() {
        return this.path.isSolved() ? this.path : null;
    }

    public List<City> getCities() {
        return this.cities;
    }
}
