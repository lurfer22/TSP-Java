package path;

import city.City;

import java.sql.Array;
import java.util.List;
import java.util.ArrayList;

public class Path {
    private final List<City> path;
    private final List<City> availableCities;
    private List<List<City>> stateArray;

    // initialize availableCities field with List passed, deep copy
    // initialize path
    public Path(List<City> availableCities) {
        this.availableCities = new ArrayList<City>(availableCities);
        this.path = new ArrayList<City>(availableCities.size());
        this.stateArray = new ArrayList<List<City>>();

    }

    // return true if city was able to be added, false if otherwise
    public boolean addCityToEndOfPath(City city) {
        if (!this.availableCities.contains(city)) return false;

        this.path.add(city);
        this.availableCities.remove(city);
        // copy the current path into state array, used for drawing purposes
        this.stateArray.add(new ArrayList<City>(this.path));
        return true;
    }

    // just check if availableCities is empty
    public boolean isSolved() {
        return this.availableCities.size() == 0;
    }

    // returns null if there is no starting city
    public City getStartingCity() {
        return this.path.size() == 0 ? null : this.path.get(0);
    }

    // loop through path arr, calculate distance between i and i + 1
    public double getPathDistance() {
        double distance = 0;
        for (int i = 0; i < this.path.size() - 1; i++) {
            distance += this.path.get(i).calculateDistance(this.path.get(i + 1));
        }
        return distance;
    }

    public boolean setStartingCity(City city) {
        if (!this.availableCities.contains(city)) return false;

        this.path.add(0, city);
        return true;
    }
}
