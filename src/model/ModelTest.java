package model;

import org.junit.Test;
import city.City;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class ModelTest {

    @Test
    public void testSetNumberOfCities() {
        Model model = new Model(5);
        List<City> deepCopy = new ArrayList<City>(model.getCities());

        // test for negative numbers
        assertThrows(IllegalArgumentException.class, () -> {
            model.setNumberOfCities(-1);
        });

        // test for numbers less than original size
        model.setNumberOfCities(4);
        assertEquals(model.getCities().size(), 4);
        for (int i = 0; i < model.getCities().size(); i++) {
            assertEquals(model.getCities().get(i), deepCopy.get(i));
        }

        // test for numbers greater than original size
        model.setNumberOfCities(10);
        // since we deleted the 5 city when we decresed the number of
        // cities, after we increase the new number of cities
        // the 5 city should be different.
        // This will work most of the time, but there is a slight
        // chance that when generating new cities we come up with random x
        // and y coords that are the same as the city that was previously
        // deleted, and is inserted into the 4 index. unlikely but
        // could happen. So maybe is a bad test?
        assertNotEquals(model.getCities().get(4), deepCopy.get(4));
    }

    @Test
    public void testSetUpperBounds() {
        Model model = new Model(10);
        List<City> tempCities = new ArrayList<City>(model.getCities());

        // make sure new cities were generated when changing
        // bounds
        model.setUpperBounds(10, 10);
        assertNotEquals(tempCities, model.getCities());

        // test negative values
        assertThrows(IllegalArgumentException.class, () -> {
           model.setUpperBounds(0, -1);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            model.setUpperBounds(-1, 0);
        });
    }
}
