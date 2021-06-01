package path;

import city.City;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class PathTest {

    private List<City> createCitiesHelper() {
        List<City> cities = new ArrayList<City>();
        for (int i = 0; i < 5; i++) {
            cities.add(new City(i, 0));
        }
        return cities;
    }

    @Test
    public void testAddCityToEndOfPath() {
        List<City> cities = createCitiesHelper();
        Path path = new Path(cities);

        for (City city : cities) {
            assertTrue(path.addCityToEndOfPath(city));
            assertFalse(path.addCityToEndOfPath(city));
        }

        // what about if we send it a city not in availabelCities?
        City notInCitiesArray = new City(-10, -10);
        assertFalse(path.addCityToEndOfPath(notInCitiesArray));
    }

    @Test
    public void testIsSolved() {
        List<City> cities = createCitiesHelper();
        Path path = new Path(cities);

        // should return true the availableCities instance variable
        // is empty, false if otherwise
        assertFalse(path.isSolved());
        path.addCityToEndOfPath(cities.get(0));
        assertFalse(path.isSolved());

        for (int i = 1; i < cities.size(); i++) {
            path.addCityToEndOfPath(cities.get(i));
        }
        assertTrue(path.isSolved());
    }

    @Test
    public void testGetSetStartingCity() {
        List<City> cities = createCitiesHelper();
        Path path = new Path(cities);

        // should return null if path is empty
        assertNull(path.getStartingCity());


        path.addCityToEndOfPath(cities.get(0));
        path.addCityToEndOfPath(cities.get(1));
        assertEquals(path.getStartingCity(), cities.get(0));
        // returns false if starting city isn't in the availableCities
        // array
        assertFalse(path.setStartingCity(cities.get(0)));


        assertTrue(path.setStartingCity(cities.get(2)));
        assertEquals(path.getStartingCity(), cities.get(2));
    }

    @Test
    public void testGetPathDistance() {
        List<City> cities = createCitiesHelper();
        Path path = new Path(cities);

        // since path is empty, getPathDistance() should return 0
        assertEquals(0, path.getPathDistance(), 0);
        // it should also return 0 if only one element in path arr
        path.setStartingCity(cities.get(0));
        assertEquals(0, path.getPathDistance(), 0);

        // I know that the total distance of our made up city coords
        // are going to be equal to the length of cities arr - 1
        for (int i = 1; i < cities.size(); i++) {
            path.addCityToEndOfPath(cities.get(i));
        }

        assertEquals((cities.size() - 1.0), path.getPathDistance(), 0);
    }
}
