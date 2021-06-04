package model;

import algorithms.Algorithm;
import algorithms.NearestNeighbor;
import path.Path;
import city.City;
import algoname.AlgorithmName;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class Model {
    private Path path;
    private List<City> cities;
    private int upperBoundX;
    private int upperBoundY;
    private int cityDistanceX;
    private int cityDistanceY;

    public Model(int numOfCities) {
        this.upperBoundX = 200;
        this.upperBoundY = 200;

        this.generateCities(numOfCities);
    }

    public void setMinimumCityDistance(int x, int y) {
        if (x <= 0 || y <= 0) {
            throw new IllegalArgumentException("Distance between cities must be set to a minimum of 1");
        } else if (x > 15 || y > 15) {
            throw new IllegalArgumentException("Distance between cities must be equal to or less than 15");
        } else {
            this.cityDistanceX = x;
            this.cityDistanceY = y;
            this.generateCities(this.cities.size());
        }
        // also need to update cities and change path
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
            this.path = new Path(this.cities);
        } else if (newCityAmount > this.cities.size()) {
            this.generateCities(newCityAmount - this.cities.size());
        }
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
        // if user hasn't already declared a starting city, then pick the first city in cities array
        if (this.path.getStartingCity() == null)
            this.path.setStartingCity(this.cities.get(0));

        Algorithm currAlgo = null;
        if (type == AlgorithmName.NEAREST_NEIGHBOR) {
            currAlgo = new NearestNeighbor(this.path);
        }
    }

    public List<City> getSolvedPath() {
        if (!this.path.isSolved()) {
            System.out.println("Must execute solvePath(AlgorithmName type) before requesting path.");
            return null;
        }

        return this.path.getPath();
    }

    public List<City> getCities() {
        return this.cities;
    }

    private void generateCities(int numOfCities) {
        this.cities = new ArrayList<City>(numOfCities);

        Random rand = new Random();
        for (int i = 0; i < numOfCities; i++) {
            City newCity = new City(rand.nextInt(this.upperBoundX), rand.nextInt(this.upperBoundY));

            while (this.cities.contains(newCity) || checkIfCitiesOverlap(newCity) || !checkIfWithinBounds(newCity)) {
                newCity = new City(rand.nextInt(this.upperBoundX), rand.nextInt(this.upperBoundY));
            }

            this.cities.add(newCity);
        }
        this.path = new Path(this.cities);
    }

    private boolean checkIfWithinBounds(City toCheck) {
        return toCheck.getX() + this.cityDistanceX < this.upperBoundX
                && toCheck.getY() + this.cityDistanceY < this.upperBoundY;
    }

    private boolean checkIfCitiesOverlap(City toCheck) {
        boolean overlap = false;
        for (City city: this.cities) {
            if (Math.max(toCheck.getX(), city.getX()) < Math.min(toCheck.getX() + this.cityDistanceX, city.getX() + this.cityDistanceX)
                    && Math.max(toCheck.getY(), city.getY()) < Math.min(toCheck.getY() + this.cityDistanceY, city.getY() + this.cityDistanceY)) {
                overlap = true;
                break;
            }
        }
        return overlap;
    }
    // have to compare a few things
    // newCity.x >= this.cityDistanceX
    // newCity.x <= this.upperBoundX - this.cityDistanceX

    // newCity.y >= this.cityDistanceY
    // newCity.y <= this.upperBoundY - this.CityDistanceY
    // that is just for upper and low bounds of the canvas
    // now I need to check if it intersects with any other nodes in the list
    //

}
