package city;

import org.junit.Test;

import static org.junit.Assert.*;

public class CityTest {

    @Test
    public void testEqualsMethod() {
        City one = new City(0, 0);
        City two = new City(0, 0);
        assertEquals(one, two);

        one = new City(0, 1);
        assertNotEquals(one, two);

        two = new City(10, 10);
        assertNotEquals(one, two);
    }

    @Test
    public void testCalculateDistanceMethod() {
        City one = new City(0, 0);
        City two = new City(1, 0);
        assertEquals(1, one.calculateDistance(two), 0.0);

        one = new City(1, 0);
        assertEquals(0, one.calculateDistance(two), 0.0);

        one = new City(10, 0);
        assertEquals(9, one.calculateDistance(two), 0.0);
    }


}
