package algorithms;

import algorithms.Algorithm;
import city.City;
import path.Path;
import java.util.List;

public class NearestNeighbor extends Algorithm {
    public NearestNeighbor(Path path) {
        super(path);
    }

    public void solve() {
        Path temp = this.getPath();

        City currCity = temp.getStartingCity();
        City closestCity = null;

        while (!temp.getAvailableCities().isEmpty()) {
            double smallestDistance = Integer.MAX_VALUE;
            List<City> availableCities = temp.getAvailableCities();

            for (City city : availableCities) {
                double tempDistance = currCity.calculateDistance(city);
                if (tempDistance < smallestDistance) {
                    smallestDistance = tempDistance;
                    closestCity = city;
                }
            }
            currCity = closestCity;
            temp.addCityToEndOfPath(currCity);
        }
        this.getPath().endPath();
        // set current city to starting city
        // loop through available cities, find city closest to current city
        // add closest city to path
        // set current city to closest city
        // keep looping until no available cities are left
    }


}
