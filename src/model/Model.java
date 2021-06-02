package model;

import algorithms.Algorithm;
import algorithms.NearestNeighbor;
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
        int DEFAULT_UPPER_BOUNDS = 200;
        this.upperBoundX = DEFAULT_UPPER_BOUNDS;
        this.upperBoundY = DEFAULT_UPPER_BOUNDS;

        this.generateCities(numOfCities);

        this.path = new Path(this.cities);
    }

    public void setNumberOfCities(int newCityAmount) {
        if (newCityAmount <= 1)
            throw new IllegalArgumentException("Setting number of cities to less than or equal to 1");
        // since we are updating number of cities, we need to create a new cities array, with fresh city objects,
        // and create a new path array.

        // a better way to do this, or quicker, would be to either remove city objects in our city array to match the new number
        // or add new ones to do the same
        if (newCityAmount < this.cities.size()) {
            this.cities.subList(newCityAmount, this.cities.size()).clear();
        } else if (newCityAmount > this.cities.size()) {
            this.generateCities(newCityAmount - this.cities.size());
        }

        // create a new path object
        this.path = new Path(this.cities);
    }

    // since I update the upper bounds, we need to also erase our cities array and push new city objects into it.
    public void setUpperBounds(int x, int y) {
        if (x < 0 || y < 0)
            throw new IllegalArgumentException("Upper bounds coords are being set to less than zero.");

        this.upperBoundX = x;
        this.upperBoundY = y;

        // since no number of cities instance var,
        // save the size of the old cities list to generate
        // new cities

        this.generateCities(this.cities.size());
    }

    public boolean setStartingCity(City city) {
        return this.path.setStartingCity(city);
    }

    public void solvePath(AlgorithmName type) {
        if (this.path.getStartingCity() == null)
            this.path.setStartingCity(this.cities.get(0));

        Algorithm currAlgo = null;
        if (type == AlgorithmName.NEAREST_NEIGHBOR) {
            currAlgo = new NearestNeighbor(this.path);
        }
    }

    public void generateCities(int numOfCities) {
        this.cities = new ArrayList<City>(numOfCities);

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
